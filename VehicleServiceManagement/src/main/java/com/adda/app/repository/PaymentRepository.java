package com.adda.app.repository;

import com.adda.app.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data JPA repository for {@link Payment} persistence and balance calculations.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Retrieves all payment transactions made against a specific invoice.
     *
     * @param invoiceId The primary key of the invoice.
     * @return List of matching payment records.
     */
    List<Payment> findByInvoiceId(Long invoiceId);

    /**
     * Computes the aggregated sum of all payments credited towards an invoice.
     * Uses COALESCE to ensure 0 is returned when no payments exist instead of null.
     *
     * @param invoiceId The primary key of the invoice.
     * @return Total amount paid so far.
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.invoice.id = :invoiceId")
    BigDecimal sumAmountByInvoiceId(@Param("invoiceId") Long invoiceId);
}
