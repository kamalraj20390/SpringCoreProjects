package com.adda.app.controller.api;

import com.adda.app.dto.CustomerDto;
import com.adda.app.dto.CustomerRequestDto;
import com.adda.app.dto.VehicleDto;
import com.adda.app.entity.Customer;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.CustomerService;
import com.adda.app.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(@RequestParam(required = false) String search) {
        List<Customer> customers = search != null ? customerService.search(search) : customerService.findAll();
        List<CustomerDto> dtos = customers.stream().map(mapper::toCustomerDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(mapper.toCustomerDto(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerRequestDto request) {
        Customer customer = mapper.toCustomerEntity(request);
        Customer saved = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCustomerDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @Valid @RequestBody CustomerRequestDto request) {
        Customer existing = customerService.findById(id);
        existing.setName(request.getName());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        Customer updated = customerService.save(existing);
        return ResponseEntity.ok(mapper.toCustomerDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/vehicles")
    public ResponseEntity<List<VehicleDto>> getCustomerVehicles(@PathVariable Long id) {
        List<VehicleDto> vehicles = vehicleService.findByCustomerId(id).stream()
                .map(mapper::toVehicleDto)
                .toList();
        return ResponseEntity.ok(vehicles);
    }
}
