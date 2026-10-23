package com.adda.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight summary DTO of a customer.
 *
 * <p>Used when embedding customer references inside other DTOs (e.g. {@link VehicleDto})
 * without including nested lists or sensitive details.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSummaryDto {

    /** Customer ID. */
    private Long id;

    /** Customer full name. */
    private String name;

    /** Contact phone number. */
    private String phone;

    /** Contact email address. */
    private String email;
}
