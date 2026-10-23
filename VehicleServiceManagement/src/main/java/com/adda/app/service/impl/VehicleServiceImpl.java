package com.adda.app.service.impl;

import com.adda.app.entity.Vehicle;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.VehicleRepository;
import com.adda.app.service.CustomerService;
import com.adda.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Service implementation for {@link VehicleService}.
 *
 * <p>Enforces foreign key validation with Customer and guarantees registration number uniqueness.</p>
 */
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vehicle> search(String query) {
        if (!StringUtils.hasText(query))
            return findAll();
        return vehicleRepository.search(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    /**
     * Saves a vehicle after validating that the associated customer exists
     * and that the registration number is globally unique.
     */
    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        // Ensure customer exists
        customerService.findById(vehicle.getCustomer().getId());

        String reg = vehicle.getRegistrationNumber();
        if (vehicle.getId() == null) {
            if (vehicleRepository.existsByRegistrationNumber(reg)) {
                throw new BusinessRuleException("Registration number already exists: " + reg);
            }
        } else {
            if (vehicleRepository.existsByRegistrationNumberAndIdNot(reg, vehicle.getId())) {
                throw new BusinessRuleException("Registration number already exists: " + reg);
            }
        }
        return vehicleRepository.save(vehicle);
    }

    /**
     * Deletes a vehicle and cascades removals where configured.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Vehicle vehicle = findById(id);
        vehicleRepository.delete(vehicle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vehicle> findByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }
}
