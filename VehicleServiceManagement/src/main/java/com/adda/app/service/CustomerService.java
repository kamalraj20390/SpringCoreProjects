package com.adda.app.service;

import com.adda.app.entity.Customer;
import java.util.List;

/**
 * Service interface defining business operations for customer account management.
 */
public interface CustomerService {

    /**
     * Retrieves all registered customers.
     *
     * @return List of all customers.
     */
    List<Customer> findAll();

    /**
     * Searches customers matching a name or query keyword.
     *
     * @param query Search keyword.
     * @return List of matching customers.
     */
    List<Customer> search(String query);

    /**
     * Finds a customer by their unique ID.
     *
     * @param id Primary key ID of the customer.
     * @return The found {@link Customer}.
     * @throws com.adda.app.exception.ResourceNotFoundException if no customer matches the ID.
     */
    Customer findById(Long id);

    /**
     * Creates or updates a customer account, validating email uniqueness.
     *
     * @param customer Customer entity to persist.
     * @return Saved Customer instance with assigned ID.
     */
    Customer save(Customer customer);

    /**
     * Deletes a customer account by ID and cascades removals if applicable.
     *
     * @param id Customer ID to delete.
     */
    void deleteById(Long id);
}
