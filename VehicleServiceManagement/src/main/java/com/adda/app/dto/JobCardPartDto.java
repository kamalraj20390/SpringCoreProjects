package com.adda.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing an issued spare part consumed during a vehicle repair.
 *
 * <p>Flattens the part code and part name from {@link com.adda.app.entity.SparePart}
 * to prevent unnecessary joins on client queries.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardPartDto {

    /** Primary key ID of the job card part record. */
    private Long id;

    /** Foreign key ID of the parent job card. */
    private Long jobCardId;

    // ================= Relational Spare Part Details ================= //

    /** ID of the spare part from inventory. */
    private Long sparePartId;

    /** SKU / code of the spare part. */
    private String sparePartCode;

    /** Display name of the spare part. */
    private String sparePartName;

    // ================= Issue Metrics ================= //

    /** Number of part units issued. */
    private Integer quantity;

    /** Price per unit frozen at the time of issuance. */
    private BigDecimal unitPrice;

    /** Total line cost (quantity * unitPrice). */
    private BigDecimal totalPrice;
}
