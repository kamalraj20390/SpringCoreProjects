package com.adda.app.repository;

import com.adda.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Customer} entity management.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Case-insensitive search for customers by partial or full name.
     *
     * @param name The customer name keyword.
     * @return List of matching customers.
     */
    List<Customer> findByNameContainingIgnoreCase(String name);

    /**
     * Looks up a customer by their unique email address.
     *
     * @param email The customer email string.
     * @return Optional containing the Customer if found.
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Checks if a customer with the given email address is already registered.
     *
     * @param email The email address to check.
     * @return {@code true} if email already exists, {@code false} otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if another customer record already uses this email, excluding the given ID (for edit flows).
     *
     * @param email The email address to test.
     * @param id The customer ID to exclude.
     * @return {@code true} if conflict exists, {@code false} otherwise.
     */
    boolean existsByEmailAndIdNot(String email, Long id);
}
