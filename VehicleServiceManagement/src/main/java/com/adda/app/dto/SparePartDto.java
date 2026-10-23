package com.adda.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a spare part inventory item.
 *
 * <p>Includes an active alert boolean {@code isLowStock} evaluated against warehouse threshold levels.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparePartDto {

    /** Primary key ID of the spare part. */
    private Long id;

    /** Unique SKU or manufacturer part code (e.g. FIL-OIL-01). */
    private String partCode;

    /** Common name of the part. */
    private String name;

    /** Detailed technical description or fitment notes. */
    private String description;

    /** Sales / billing price per unit. */
    private BigDecimal unitPrice;

    /** Current physical quantity in inventory. */
    private Integer stockQuantity;

    /** Minimum quantity before a replenishment alert is triggered. */
    private Integer lowStockThreshold;

    /** Flag indicating if current stock is at or below the low stock threshold. */
    private boolean isLowStock;
}
