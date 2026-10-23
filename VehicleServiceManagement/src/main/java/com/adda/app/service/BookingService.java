package com.adda.app.service;

import com.adda.app.entity.ServiceBooking;
import com.adda.app.entity.JobCard;
import com.adda.app.enums.BookingStatus;
import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for booking appointments and converting them into repair job cards.
 */
public interface BookingService {

    /**
     * Lists all service bookings.
     *
     * @return List of bookings.
     */
    List<ServiceBooking> findAll();

    /**
     * Retrieves a booking by ID.
     *
     * @param id Primary key ID.
     * @return The found {@link ServiceBooking}.
     */
    ServiceBooking findById(Long id);

    /**
     * Creates or updates a service booking.
     *
     * @param booking Booking entity to persist.
     * @return Saved ServiceBooking instance.
     */
    ServiceBooking save(ServiceBooking booking);

    /**
     * Updates the status of an appointment (e.g. to CONFIRMED or CANCELLED).
     *
     * @param id Primary key ID.
     * @param status New status.
     * @return Updated ServiceBooking.
     */
    ServiceBooking updateStatus(Long id, BookingStatus status);

    /**
     * Converts an appointment booking into an active workshop JobCard.
     *
     * @param bookingId ID of the booking to convert.
     * @param mechanicName Name of mechanic assigned.
     * @param targetDate Target completion date.
     * @return Generated and saved JobCard.
     */
    JobCard convertToJobCard(Long bookingId, String mechanicName, LocalDate targetDate);

    /**
     * Finds all bookings for a given vehicle.
     *
     * @param vehicleId Primary key ID of the vehicle.
     * @return List of bookings.
     */
    List<ServiceBooking> findByVehicleId(Long vehicleId);

    /**
     * Counts how many service bookings are scheduled for today.
     *
     * @return Today's booking count.
     */
    long countTodaysBookings();
}
