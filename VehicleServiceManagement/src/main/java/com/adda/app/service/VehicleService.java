package com.adda.app.service;

import com.adda.app.entity.Vehicle;
import java.util.List;

/**
 * Service interface for vehicle registration, searching, and customer-vehicle relations.
 */
public interface VehicleService {

    /**
     * Lists all registered vehicles.
     *
     * @return List of all vehicles.
     */
    List<Vehicle> findAll();

    /**
     * Searches vehicles matching registration number, make, or model.
     *
     * @param query Search keyword.
     * @return List of matching vehicles.
     */
    List<Vehicle> search(String query);

    /**
     * Retrieves a vehicle by ID.
     *
     * @param id Primary key ID of the vehicle.
     * @return The found {@link Vehicle}.
     */
    Vehicle findById(Long id);

    /**
     * Persists or updates a vehicle, ensuring valid customer association and registration uniqueness.
     *
     * @param vehicle Vehicle entity to save.
     * @return Saved vehicle instance.
     */
    Vehicle save(Vehicle vehicle);

    /**
     * Deletes a vehicle by ID.
     *
     * @param id Vehicle primary key ID.
     */
    void deleteById(Long id);

    /**
     * Finds all vehicles registered under a specific customer.
     *
     * @param customerId Foreign key ID of the customer.
     * @return List of vehicles belonging to the customer.
     */
    List<Vehicle> findByCustomerId(Long customerId);
}
