
package com.adda.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "job_card_parts")
@Getter
@Setter
public class JobCardPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_card_id", nullable = false)
    private JobCard jobCard;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @Positive(message = "Quantity must be positive")
    @Column(nullable = false)
    private Integer quantity;

    /** Snapshot of part price at issue time */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /** Calculate total price for the issued spare part */
    public BigDecimal getTotalPrice() {
        if (unitPrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
