package com.adda.app.dto;

import com.adda.app.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object representing a payment transaction credited against an invoice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    /** Primary key ID of the payment transaction. */
    private Long id;

    /** ID of the invoice paid. */
    private Long invoiceId;

    /** Serial number of the invoice paid. */
    private String invoiceNumber;

    /** Amount paid in this transaction. */
    private BigDecimal amount;

    /** Payment channel (CASH, CARD, UPI, NET_BANKING). */
    private PaymentMethod paymentMethod;

    /** Date the payment was settled. */
    private LocalDate paymentDate;

    /** External transaction reference, check, or receipt number. */
    private String referenceNumber;
}
