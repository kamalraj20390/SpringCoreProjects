package com.adda.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "spare_parts")
@Getter
@Setter
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Part code is required")
    @Column(name = "part_code", nullable = false, unique = true)
    private String partCode;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Min(value = 0)
    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold = 5;
}
