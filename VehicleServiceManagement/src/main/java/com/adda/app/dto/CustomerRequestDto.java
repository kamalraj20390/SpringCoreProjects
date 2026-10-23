package com.adda.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload DTO for creating or updating a customer profile.
 *
 * <p>Enforces input validation constraints such as non-blank name/phone and valid email formatting.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {

    /** Full name of the customer (required). */
    @NotBlank(message = "Name is required")
    private String name;

    /** Contact phone number (required). */
    @NotBlank(message = "Phone is required")
    private String phone;

    /** Email address (must match standard email regex). */
    @Email(message = "Invalid email format")
    private String email;

    /** Residential or business address. */
    private String address;
}
