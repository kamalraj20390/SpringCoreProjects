package com.adda.app.service.impl;

import com.adda.app.entity.JobCard;
import com.adda.app.entity.ServiceBooking;
import com.adda.app.enums.BookingStatus;
import com.adda.app.enums.JobCardStatus;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.JobCardRepository;
import com.adda.app.repository.ServiceBookingRepository;
import com.adda.app.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service implementation managing customer service bookings.
 *
 * <p>Handles booking scheduling, status tracking, and the atomic transition from
 * customer appointment bookings to workshop repair job cards.</p>
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ServiceBookingRepository bookingRepository;
    private final JobCardRepository jobCardRepository;

    @Override
    public List<ServiceBooking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public ServiceBooking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    @Transactional
    public ServiceBooking save(ServiceBooking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public ServiceBooking updateStatus(Long id, BookingStatus status) {
        ServiceBooking booking = findById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    /**
     * Converts an active service booking into an in-workshop JobCard.
     * Enforces that cancelled or previously converted bookings cannot be converted again.
     *
     * @param bookingId     ID of the booking to convert.
     * @param mechanicName  Name of the assigned mechanic.
     * @param targetDate    Estimated target completion date.
     * @return Newly initialized {@link JobCard} with generated job card number.
     */
    @Override
    @Transactional
    public JobCard convertToJobCard(Long bookingId, String mechanicName, LocalDate targetDate) {
        ServiceBooking booking = findById(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessRuleException("Cannot convert a cancelled booking to a job card.");
        }
        if (booking.getStatus() == BookingStatus.CONVERTED_TO_JOB_CARD) {
            throw new BusinessRuleException("Booking already converted to a job card.");
        }

        JobCard jobCard = new JobCard();
        jobCard.setVehicle(booking.getVehicle());
        jobCard.setBooking(booking);
        jobCard.setMechanicName(mechanicName);
        jobCard.setComplaint(booking.getComplaint());
        jobCard.setStatus(JobCardStatus.RECEIVED);
        jobCard.setOpenedAt(LocalDateTime.now());
        jobCard.setTargetCompletionDate(targetDate);
        jobCard.setJobCardNumber(generateJobCardNumber());

        booking.setStatus(BookingStatus.CONVERTED_TO_JOB_CARD);
        bookingRepository.save(booking);

        return jobCardRepository.save(jobCard);
    }

    @Override
    public List<ServiceBooking> findByVehicleId(Long vehicleId) {
        return bookingRepository.findByVehicleId(vehicleId);
    }

    @Override
    public long countTodaysBookings() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return bookingRepository.countByBookingDateBetween(startOfDay, endOfDay);
    }

    private String generateJobCardNumber() {
        String prefix = "JC-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        long count = jobCardRepository.count() + 1;
        return prefix + String.format("%04d", count);
    }
}
