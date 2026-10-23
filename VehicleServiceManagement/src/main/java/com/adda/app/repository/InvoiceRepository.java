package com.adda.app.repository;

import com.adda.app.entity.Invoice;
import com.adda.app.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for billing {@link Invoice} persistence and retrieval.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Finds an invoice generated for a specific job card.
     *
     * @param jobCardId Unique ID of the job card.
     * @return Optional holding the invoice if generated.
     */
    Optional<Invoice> findByJobCardId(Long jobCardId);

    /**
     * Checks if an invoice has already been generated for a job card.
     *
     * @param jobCardId Unique ID of the job card.
     * @return {@code true} if billed, {@code false} otherwise.
     */
    boolean existsByJobCardId(Long jobCardId);

    /**
     * Retrieves all invoices categorized under a particular payment status.
     *
     * @param paymentStatus UNPAID, PARTIALLY_PAID, or PAID.
     * @return List of matching invoices.
     */
    List<Invoice> findByPaymentStatus(PaymentStatus paymentStatus);

    /**
     * Counts how many invoices currently have the specified payment status.
     *
     * @param paymentStatus The payment status filter.
     * @return Total matching invoices.
     */
    long countByPaymentStatus(PaymentStatus paymentStatus);
}
