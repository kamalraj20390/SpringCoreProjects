package com.adda.app.dto;

import com.adda.app.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request payload DTO for recording a payment against an outstanding invoice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    /** Primary key ID of the invoice to credit. */
    @NotNull(message = "Invoice ID is required")
    private Long invoiceId;

    /** Amount paid in this installment (must be greater than 0). */
    @NotNull(message = "Amount is required")
    @Positive(message = "Payment amount must be positive")
    private BigDecimal amount;

    /** Payment channel (CASH, CARD, UPI, NET_BANKING). */
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    /** Optional transaction ID, check number, or payment reference code. */
    private String referenceNumber;
}
