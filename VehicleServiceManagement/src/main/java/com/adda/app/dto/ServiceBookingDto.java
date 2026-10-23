package com.adda.app.dto;

import com.adda.app.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a vehicle service booking appointment.
 *
 * <p>Encapsulates all relevant booking metadata alongside flattened relational attributes
 * from both the assigned {@link com.adda.app.entity.Vehicle} and its owning
 * {@link com.adda.app.entity.Customer}. This prevents circular serialization and avoids
 * unneeded database queries for client consumers.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBookingDto {

    /** Primary identifier of the service booking. */
    private Long id;

    // ================= Relational Vehicle Details ================= //

    /** Unique identifier of the associated vehicle. */
    private Long vehicleId;

    /** Registration number / license plate of the vehicle. */
    private String vehicleRegistrationNumber;

    /** Manufacturer / make of the vehicle (e.g., Toyota, Honda). */
    private String vehicleMake;

    /** Model name of the vehicle (e.g., Corolla, Civic). */
    private String vehicleModel;

    // ================= Relational Customer Details ================= //

    /** Unique identifier of the customer who owns the vehicle. */
    private Long customerId;

    /** Full name of the vehicle owner. */
    private String customerName;

    /** Primary contact phone number of the vehicle owner. */
    private String customerPhone;

    // ================= Booking Metadata ================= //

    /** Date and time scheduled for the vehicle service appointment. */
    private LocalDateTime bookingDate;

    /** Primary complaint or issue reported by the customer. */
    private String complaint;

    /** Specific services requested by the customer (e.g., oil change, brake inspection). */
    private String requestedService;

    /** Current lifecycle status of the booking (BOOKED, CONFIRMED, CANCELLED, CONVERTED_TO_JOB_CARD). */
    private BookingStatus status;
}
