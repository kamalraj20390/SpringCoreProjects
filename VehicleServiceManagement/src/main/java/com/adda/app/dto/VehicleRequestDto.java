package com.adda.app.dto;

import com.adda.app.enums.FuelType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload DTO for creating or updating a vehicle record.
 *
 * <p>Carries Bean Validation constraints to enforce schema rules before
 * entity persistence.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDto {

    /** ID of the existing customer who owns this vehicle. */
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    /** Unique registration / license plate number. */
    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    /** Vehicle manufacturer or brand. */
    @NotBlank(message = "Make is required")
    private String make;

    /** Model name or vehicle edition. */
    @NotBlank(message = "Model is required")
    private String model;

    /** Engine fuel configuration. */
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    /** Year of manufacture (must be between 1886 and 2100). */
    @NotNull(message = "Manufacturing year is required")
    @Min(value = 1886, message = "Year must be 1886 or later")
    @Max(value = 2100, message = "Year seems too far in the future")
    private Integer manufacturingYear;

    /** Current odometer mileage in kilometers. */
    @NotNull(message = "Odometer reading is required")
    @Min(value = 0, message = "Odometer must be 0 or more")
    private Integer odometer;
}
