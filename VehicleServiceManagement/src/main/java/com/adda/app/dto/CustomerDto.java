package com.adda.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a customer account and their vehicle fleet.
 *
 * <p>Prevents infinite serialization loops by nesting lightweight {@link VehicleSummaryDto}
 * instances rather than full Vehicle entities.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    /** Primary key ID of the customer. */
    private Long id;

    /** Full name of the customer. */
    private String name;

    /** Primary contact phone number. */
    private String phone;

    /** Customer email address. */
    private String email;

    /** Physical postal/residential address. */
    private String address;

    /** Timestamp of account registration. */
    private LocalDateTime createdAt;

    /** List of vehicles owned by this customer. */
    @Builder.Default
    private List<VehicleSummaryDto> vehicles = new ArrayList<>();
}
