package com.adda.app.entity;

import com.adda.app.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_bookings")
@Getter
@Setter
public class ServiceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "Booking date is required")
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @NotBlank(message = "Complaint is required")
    @Column(columnDefinition = "TEXT")
    private String complaint;

    @Column(name = "requested_service", columnDefinition = "TEXT")
    private String requestedService;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.BOOKED;
}
