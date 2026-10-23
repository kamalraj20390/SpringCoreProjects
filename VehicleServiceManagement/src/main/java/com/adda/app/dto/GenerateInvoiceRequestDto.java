package com.adda.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request payload DTO for generating an invoice from a workshop job card.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateInvoiceRequestDto {

    /** Primary key ID of the job card to bill. */
    @NotNull(message = "Job Card ID is required")
    private Long jobCardId;

    /** Fixed discount amount to deduct from total labor and parts (default 0). */
    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    /** Tax rate percentage to apply to post-discount subtotal (e.g., 18.0 for 18% GST). */
    @DecimalMin(value = "0.0", message = "Tax percentage cannot be negative")
    @Builder.Default
    private BigDecimal taxPercent = BigDecimal.ZERO;
}
