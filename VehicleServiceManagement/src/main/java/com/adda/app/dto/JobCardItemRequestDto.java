package com.adda.app.dto;

import com.adda.app.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request payload DTO for adding a labor/service line item to a job card.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardItemRequestDto {

    /** Work or task description (e.g., Brake Pad Replacement). */
    @NotBlank(message = "Description is required")
    private String description;

    /** Number of hours or units (must be greater than 0). */
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    /** Unit price for labor (must be positive). */
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    /** Category classification of item (LABOUR, SERVICE). */
    @NotNull(message = "Item type is required")
    private ItemType itemType;
}
