package com.adda.app.repository;

import com.adda.app.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Vehicle} management and vehicle lookup operations.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Looks up a vehicle by exact registration number.
     *
     * @param registrationNumber The vehicle's license plate string.
     * @return Optional containing the Vehicle if found.
     */
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    /**
     * Checks if a vehicle with the given registration number is already registered.
     *
     * @param registrationNumber The license plate to check.
     * @return {@code true} if present, {@code false} otherwise.
     */
    boolean existsByRegistrationNumber(String registrationNumber);

    /**
     * Checks uniqueness of a registration number excluding a specific vehicle ID (for edit flows).
     *
     * @param registrationNumber Registration number to test.
     * @param id ID to exclude.
     * @return {@code true} if another record conflicts, {@code false} otherwise.
     */
    boolean existsByRegistrationNumberAndIdNot(String registrationNumber, Long id);

    /**
     * Retrieves all vehicles owned by a specific customer.
     *
     * @param customerId Foreign key ID of the customer.
     * @return List of vehicles associated with the customer.
     */
    List<Vehicle> findByCustomerId(Long customerId);

    /**
     * Full-text case-insensitive search across vehicle registration number, make, or model.
     *
     * @param query The search keyword.
     * @return List of vehicles matching the query.
     */
    @Query("SELECT v FROM Vehicle v WHERE " +
            "LOWER(v.registrationNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(v.make) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(v.model) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Vehicle> search(@Param("q") String query);
}
