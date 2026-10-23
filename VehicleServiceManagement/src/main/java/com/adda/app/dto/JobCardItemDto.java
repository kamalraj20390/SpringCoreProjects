package com.adda.app.dto;

import com.adda.app.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a labor or service item logged on a job card.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCardItemDto {

    /** Primary key ID of the job card item. */
    private Long id;

    /** Foreign key ID of the parent job card. */
    private Long jobCardId;

    /** Description of the work or inspection performed. */
    private String description;

    /** Quantity of labor units/hours performed. */
    private Integer quantity;

    /** Price per unit of labor. */
    private BigDecimal unitPrice;

    /** Classification of the item (LABOUR, SERVICE). */
    private ItemType itemType;

    /** Computed total cost for this line item (quantity * unitPrice). */
    private BigDecimal totalPrice;
}
