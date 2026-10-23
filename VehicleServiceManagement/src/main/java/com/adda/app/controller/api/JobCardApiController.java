package com.adda.app.controller.api;

import com.adda.app.dto.*;
import com.adda.app.entity.JobCard;
import com.adda.app.entity.ServiceBooking;
import com.adda.app.entity.Vehicle;
import com.adda.app.enums.JobCardStatus;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.BookingService;
import com.adda.app.service.JobCardService;
import com.adda.app.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/job-cards")
@RequiredArgsConstructor
public class JobCardApiController {

    private final JobCardService jobCardService;
    private final VehicleService vehicleService;
    private final BookingService bookingService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<JobCardDto>> getAll() {
        List<JobCardDto> dtos = jobCardService.findAll().stream()
                .map(mapper::toJobCardDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCardDto> getById(@PathVariable Long id) {
        JobCard jobCard = jobCardService.findById(id);
        return ResponseEntity.ok(mapper.toJobCardDto(jobCard));
    }

    @PostMapping
    public ResponseEntity<JobCardDto> create(@Valid @RequestBody JobCardRequestDto request) {
        Vehicle vehicle = vehicleService.findById(request.getVehicleId());
        ServiceBooking booking = request.getBookingId() != null ? bookingService.findById(request.getBookingId()) : null;
        JobCard jobCard = mapper.toJobCardEntity(request, vehicle, booking);

        // Generate job card number
        String jcNum = "JC-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-"
                + String.format("%04d", jobCardService.findAll().size() + 1);
        jobCard.setJobCardNumber(jcNum);

        JobCard saved = jobCardService.save(jobCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toJobCardDto(saved));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<JobCardDto> addLaborItem(
            @PathVariable Long id,
            @Valid @RequestBody JobCardItemRequestDto request) {
        JobCard updated = jobCardService.addLaborItem(
                id,
                request.getDescription(),
                request.getQuantity(),
                request.getUnitPrice(),
                request.getItemType());
        return ResponseEntity.ok(mapper.toJobCardDto(updated));
    }

    @PostMapping("/{id}/parts")
    public ResponseEntity<JobCardDto> addPart(
            @PathVariable Long id,
            @Valid @RequestBody JobCardPartRequestDto request) {
        JobCard updated = jobCardService.addPart(
                id,
                request.getSparePartId(),
                request.getQuantity());
        return ResponseEntity.ok(mapper.toJobCardDto(updated));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<JobCardDto> updateStatus(
            @PathVariable Long id,
            @RequestParam JobCardStatus status) {
        JobCard updated = jobCardService.updateStatus(id, status);
        return ResponseEntity.ok(mapper.toJobCardDto(updated));
    }
}
