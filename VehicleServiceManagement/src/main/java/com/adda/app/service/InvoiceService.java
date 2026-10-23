package com.adda.app.service;

import com.adda.app.entity.Invoice;
import com.adda.app.entity.Payment;
import com.adda.app.enums.PaymentMethod;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for invoice generation, payment processing, and billing balances.
 */
public interface InvoiceService {

    /**
     * Lists all generated invoices.
     *
     * @return List of all invoices.
     */
    List<Invoice> findAll();

    /**
     * Finds an invoice by its unique ID.
     *
     * @param id Primary key ID of the invoice.
     * @return The found {@link Invoice}.
     */
    Invoice findById(Long id);

    /**
     * Generates a final invoice from a completed job card with optional discount and tax.
     *
     * @param jobCardId Primary key ID of the job card.
     * @param discount Discount amount to deduct from subtotal.
     * @param taxPercent Percentage of tax to apply.
     * @return Generated and persisted Invoice.
     */
    Invoice generateInvoice(Long jobCardId, BigDecimal discount, BigDecimal taxPercent);

    /**
     * Records a payment against an invoice and updates its payment status.
     *
     * @param invoiceId ID of the invoice being paid.
     * @param amount Amount being paid.
     * @param method Payment method (CASH, CARD, UPI, NET_BANKING).
     * @param referenceNumber Transaction or receipt reference number.
     * @return Persisted Payment record.
     */
    Payment recordPayment(Long invoiceId, BigDecimal amount, PaymentMethod method, String referenceNumber);

    /**
     * Computes the current outstanding unpaid balance for an invoice (grandTotal - sum of payments).
     *
     * @param invoiceId Primary key ID of the invoice.
     * @return Remaining outstanding balance.
     */
    BigDecimal getOutstandingBalance(Long invoiceId);

    /**
     * Counts how many invoices are currently UNPAID or PARTIALLY_PAID.
     *
     * @return Number of unpaid invoices.
     */
    long countUnpaidInvoices();
}
