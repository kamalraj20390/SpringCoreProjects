package com.adda.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Request payload DTO for scheduling a new service appointment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBookingRequestDto {

    /** Primary key ID of the vehicle being booked for service. */
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    /** Scheduled date and time for the appointment. */
    @NotNull(message = "Booking date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookingDate;

    /** Primary complaint or issue reported by the customer (required). */
    @NotBlank(message = "Complaint is required")
    private String complaint;

    /** Optional summary of requested services (e.g. 10,000 km periodic service). */
    private String requestedService;
}
