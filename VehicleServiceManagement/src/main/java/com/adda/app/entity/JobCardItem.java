
package com.adda.app.entity;

import com.adda.app.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "job_card_items")
@Getter
@Setter
public class JobCardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_card_id", nullable = false)
    private JobCard jobCard;

    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String description;

    @Positive(message = "Quantity must be positive")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType = ItemType.LABOUR;

    // Calculate total price for this item
    public BigDecimal getTotalPrice() {
        if (unitPrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
