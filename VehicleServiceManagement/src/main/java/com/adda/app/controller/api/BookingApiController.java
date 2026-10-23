package com.adda.app.controller.api;

import com.adda.app.dto.JobCardDto;
import com.adda.app.dto.ServiceBookingDto;
import com.adda.app.dto.ServiceBookingRequestDto;
import com.adda.app.entity.JobCard;
import com.adda.app.entity.ServiceBooking;
import com.adda.app.entity.Vehicle;
import com.adda.app.enums.BookingStatus;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.BookingService;
import com.adda.app.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingApiController {

    private final BookingService bookingService;
    private final VehicleService vehicleService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ServiceBookingDto>> getAll() {
        List<ServiceBookingDto> dtos = bookingService.findAll().stream()
                .map(mapper::toServiceBookingDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceBookingDto> getById(@PathVariable Long id) {
        ServiceBooking booking = bookingService.findById(id);
        return ResponseEntity.ok(mapper.toServiceBookingDto(booking));
    }

    @PostMapping
    public ResponseEntity<ServiceBookingDto> create(@Valid @RequestBody ServiceBookingRequestDto request) {
        Vehicle vehicle = vehicleService.findById(request.getVehicleId());
        ServiceBooking booking = mapper.toServiceBookingEntity(request, vehicle);
        ServiceBooking saved = bookingService.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toServiceBookingDto(saved));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceBookingDto> updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        ServiceBooking updated = bookingService.updateStatus(id, status);
        return ResponseEntity.ok(mapper.toServiceBookingDto(updated));
    }

    @PostMapping("/{id}/convert-to-jobcard")
    public ResponseEntity<JobCardDto> convertToJobCard(
            @PathVariable Long id,
            @RequestParam String mechanicName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        JobCard jobCard = bookingService.convertToJobCard(id, mechanicName, targetDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toJobCardDto(jobCard));
    }
}
