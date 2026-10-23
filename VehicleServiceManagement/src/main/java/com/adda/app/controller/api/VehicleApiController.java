package com.adda.app.controller.api;

import com.adda.app.dto.JobCardDto;
import com.adda.app.dto.ServiceBookingDto;
import com.adda.app.dto.VehicleDto;
import com.adda.app.dto.VehicleRequestDto;
import com.adda.app.entity.Customer;
import com.adda.app.entity.Vehicle;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.BookingService;
import com.adda.app.service.CustomerService;
import com.adda.app.service.JobCardService;
import com.adda.app.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleApiController {

    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final JobCardService jobCardService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAll(@RequestParam(required = false) String search) {
        List<Vehicle> vehicles = search != null ? vehicleService.search(search) : vehicleService.findAll();
        List<VehicleDto> dtos = vehicles.stream().map(mapper::toVehicleDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(mapper.toVehicleDto(vehicle));
    }

    @PostMapping
    public ResponseEntity<VehicleDto> create(@Valid @RequestBody VehicleRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());
        Vehicle vehicle = mapper.toVehicleEntity(request, customer);
        Vehicle saved = vehicleService.save(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toVehicleDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> update(@PathVariable Long id, @Valid @RequestBody VehicleRequestDto request) {
        Vehicle vehicle = vehicleService.findById(id);
        Customer customer = customerService.findById(request.getCustomerId());
        vehicle.setCustomer(customer);
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setManufacturingYear(request.getManufacturingYear());
        vehicle.setOdometer(request.getOdometer());
        Vehicle updated = vehicleService.save(vehicle);
        return ResponseEntity.ok(mapper.toVehicleDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<ServiceBookingDto>> getVehicleBookings(@PathVariable Long id) {
        List<ServiceBookingDto> dtos = bookingService.findByVehicleId(id).stream()
                .map(mapper::toServiceBookingDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/job-cards")
    public ResponseEntity<List<JobCardDto>> getVehicleJobCards(@PathVariable Long id) {
        List<JobCardDto> dtos = jobCardService.findByVehicleId(id).stream()
                .map(mapper::toJobCardDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
