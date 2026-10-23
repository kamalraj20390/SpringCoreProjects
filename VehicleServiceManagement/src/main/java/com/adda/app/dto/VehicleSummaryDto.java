package com.adda.app.dto;

import com.adda.app.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight summary DTO of a vehicle.
 *
 * <p>Used when nesting vehicle lists inside {@link CustomerDto} to break cyclic relationships.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleSummaryDto {

    /** Vehicle ID. */
    private Long id;

    /** Registration number / license plate. */
    private String registrationNumber;

    /** Manufacturer / make (e.g. Maruti, Honda). */
    private String make;

    /** Model name (e.g. Swift, City). */
    private String model;

    /** Fuel configuration type. */
    private FuelType fuelType;

    /** Manufacturing year. */
    private Integer manufacturingYear;

    /** Current odometer mileage in km. */
    private Integer odometer;
}
