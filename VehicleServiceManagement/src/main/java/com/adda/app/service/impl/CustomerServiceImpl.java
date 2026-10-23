package com.adda.app.service.impl;

import com.adda.app.entity.Customer;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.CustomerRepository;
import com.adda.app.repository.VehicleRepository;
import com.adda.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Service implementation for {@link CustomerService}.
 *
 * <p>Enforces business constraints such as email uniqueness and foreign-key integrity
 * (prevents deleting customers that have associated registered vehicles).</p>
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> search(String query) {
        if (!StringUtils.hasText(query))
            return findAll();
        return customerRepository.findByNameContainingIgnoreCase(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Persists a customer after verifying email uniqueness across both new registrations and updates.
     */
    @Override
    @Transactional
    public Customer save(Customer customer) {
        // Validate unique email if provided
        if (StringUtils.hasText(customer.getEmail())) {
            if (customer.getId() == null) {
                if (customerRepository.existsByEmail(customer.getEmail())) {
                    throw new BusinessRuleException("Email already in use: " + customer.getEmail());
                }
            } else {
                if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customer.getId())) {
                    throw new BusinessRuleException("Email already in use: " + customer.getEmail());
                }
            }
        }
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer. Rejects deletion if vehicles are registered under this customer.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Customer customer = findById(id);
        if (!vehicleRepository.findByCustomerId(id).isEmpty()) {
            throw new BusinessRuleException("Cannot delete customer with registered vehicles.");
        }
        customerRepository.delete(customer);
    }
}
