package com.adda.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request payload DTO for creating or updating a spare part catalog entry.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparePartRequestDto {

    /** Unique SKU or part code (required). */
    @NotBlank(message = "Part code is required")
    private String partCode;

    /** Name of the spare part (required). */
    @NotBlank(message = "Name is required")
    private String name;

    /** Description of part application and technical details. */
    private String description;

    /** Sales unit price (must be positive). */
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    /** Available warehouse stock quantity (cannot be negative). */
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    /** Minimum stock warning boundary (cannot be negative). */
    @NotNull(message = "Low stock threshold is required")
    @Min(value = 0, message = "Low stock threshold cannot be negative")
    private Integer lowStockThreshold;
}
