package com.adda.app.dto;

import com.adda.app.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a vehicle registered in the service system.
 *
 * <p>Contains vehicle mechanical specifications alongside flattened owner (Customer) information
 * and aggregate service history counts to avoid circular entity graphs.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDto {

    /** Unique primary key of the vehicle record. */
    private Long id;

    // ================= Relational Customer Information ================= //

    /** Primary key of the owning customer. */
    private Long customerId;

    /** Name of the registered owner. */
    private String customerName;

    /** Contact phone number of the registered owner. */
    private String customerPhone;

    /** Embedded concise summary of the owning customer. */
    private CustomerSummaryDto customer;

    // ================= Vehicle Specifications ================= //

    /** Unique vehicle registration / license plate number. */
    private String registrationNumber;

    /** Vehicle manufacturer (e.g., Maruti, Hyundai, Toyota). */
    private String make;

    /** Vehicle model name (e.g., Swift, Creta, Innova). */
    private String model;

    /** Engine fuel configuration (PETROL, DIESEL, ELECTRIC, HYBRID, CNG). */
    private FuelType fuelType;

    /** Year the vehicle was manufactured. */
    private Integer manufacturingYear;

    /** Current odometer mileage reading in kilometers. */
    private Integer odometer;

    // ================= Historical Aggregates ================= //

    /** Total number of service bookings logged for this vehicle. */
    private int totalBookings;

    /** Total number of workshop repair job cards created for this vehicle. */
    private int totalJobCards;
}
