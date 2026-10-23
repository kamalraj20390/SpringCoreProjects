package com.adda.app.service.impl;

import com.adda.app.entity.Invoice;
import com.adda.app.entity.JobCard;
import com.adda.app.entity.JobCardItem;
import com.adda.app.entity.JobCardPart;
import com.adda.app.entity.Payment;
import com.adda.app.enums.PaymentMethod;
import com.adda.app.enums.PaymentStatus;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.InvoiceRepository;
import com.adda.app.repository.JobCardRepository;
import com.adda.app.repository.PaymentRepository;
import com.adda.app.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service implementation managing invoice billing, tax computation, and payments.
 *
 * <p>Handles:
 * <ul>
 *   <li>Server-side price snapshot calculation from associated job card items and parts.</li>
 *   <li>Discount application and precise rounding-mode tax computations.</li>
 *   <li>Atomic payment recording and automatic status updates (UNPAID -> PARTIALLY_PAID -> PAID).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final JobCardRepository jobCardRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    /**
     * Generates a final invoice for a completed job card.
     * Computes labor subtotal, parts subtotal, applies discount, and applies tax.
     * Enforces the business rule that only one invoice may exist per job card.
     */
    @Override
    @Transactional
    public Invoice generateInvoice(Long jobCardId, BigDecimal discount, BigDecimal taxPercent) {
        if (invoiceRepository.existsByJobCardId(jobCardId)) {
            throw new BusinessRuleException("An invoice already exists for this job card.");
        }
        JobCard jobCard = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Job card not found with id: " + jobCardId));

        // Calculate labor total from job card items (server side)
        BigDecimal laborTotal = jobCard.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate parts total from job card parts
        BigDecimal partsTotal = jobCard.getParts().stream()
                .map(p -> p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal subtotal = laborTotal.add(partsTotal).subtract(discount == null ? BigDecimal.ZERO : discount);
        BigDecimal taxAmount = subtotal.multiply(
                taxPercent == null ? BigDecimal.ZERO
                        : taxPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal grandTotal = subtotal.add(taxAmount).setScale(2, RoundingMode.HALF_UP);

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setJobCard(jobCard);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setLaborTotal(laborTotal.setScale(2, RoundingMode.HALF_UP));
        invoice.setPartsTotal(partsTotal.setScale(2, RoundingMode.HALF_UP));
        invoice.setDiscount(discount == null ? BigDecimal.ZERO : discount.setScale(2, RoundingMode.HALF_UP));
        invoice.setTaxAmount(taxAmount);
        invoice.setGrandTotal(grandTotal);
        invoice.setPaymentStatus(PaymentStatus.UNPAID);

        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public Payment recordPayment(Long invoiceId, BigDecimal amount, PaymentMethod method, String referenceNumber) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Payment amount must be positive.");
        }
        Invoice invoice = findById(invoiceId);
        BigDecimal outstanding = getOutstandingBalance(invoiceId);
        if (amount.compareTo(outstanding) > 0) {
            throw new BusinessRuleException(
                    "Payment amount (" + amount + ") exceeds outstanding balance (" + outstanding + ").");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(amount.setScale(2, RoundingMode.HALF_UP));
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDate.now());
        payment.setReferenceNumber(referenceNumber);
        paymentRepository.save(payment);

        // Recalculate and update payment status
        BigDecimal totalPaid = paymentRepository.sumAmountByInvoiceId(invoiceId);
        if (totalPaid.compareTo(invoice.getGrandTotal()) >= 0) {
            invoice.setPaymentStatus(PaymentStatus.PAID);
        } else {
            invoice.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
        }
        invoiceRepository.save(invoice);
        return payment;
    }

    @Override
    public BigDecimal getOutstandingBalance(Long invoiceId) {
        Invoice invoice = findById(invoiceId);
        BigDecimal paid = paymentRepository.sumAmountByInvoiceId(invoiceId);
        return invoice.getGrandTotal().subtract(paid).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public long countUnpaidInvoices() {
        return invoiceRepository.countByPaymentStatus(PaymentStatus.UNPAID)
                + invoiceRepository.countByPaymentStatus(PaymentStatus.PARTIALLY_PAID);
    }

    private String generateInvoiceNumber() {
        String prefix = "INV-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        long count = invoiceRepository.count() + 1;
        return prefix + String.format("%04d", count);
    }
}
