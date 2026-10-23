package com.adda.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload DTO for issuing a spare part from warehouse stock to a workshop job card.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardPartRequestDto {

    /** Primary key ID of the spare part being issued. */
    @NotNull(message = "Spare part ID is required")
    private Long sparePartId;

    /** Number of units requested (must be positive). */
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
