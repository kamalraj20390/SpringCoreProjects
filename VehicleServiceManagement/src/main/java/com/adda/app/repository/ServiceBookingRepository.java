package com.adda.app.repository;

import com.adda.app.entity.ServiceBooking;
import com.adda.app.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for vehicle {@link ServiceBooking} appointments.
 */
@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {

    /**
     * Retrieves all service appointments scheduled for a specific vehicle.
     *
     * @param vehicleId Primary key ID of the vehicle.
     * @return List of booking records.
     */
    List<ServiceBooking> findByVehicleId(Long vehicleId);

    /**
     * Finds bookings filtered by status (BOOKED, CONFIRMED, CANCELLED, CONVERTED_TO_JOB_CARD).
     *
     * @param status Current status enum.
     * @return List of matching bookings.
     */
    List<ServiceBooking> findByStatus(BookingStatus status);

    /**
     * Finds bookings scheduled within a specific date/time range.
     *
     * @param start Range start boundary.
     * @param end Range end boundary.
     * @return List of bookings occurring between start and end.
     */
    List<ServiceBooking> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Counts how many appointments are booked within a date/time window (e.g. today's bookings).
     *
     * @param start Window start boundary.
     * @param end Window end boundary.
     * @return Count of bookings scheduled in range.
     */
    long countByBookingDateBetween(LocalDateTime start, LocalDateTime end);
}
