package com.adda.app.repository;

import com.adda.app.entity.JobCard;
import com.adda.app.enums.JobCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for workshop {@link JobCard} records.
 */
@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    /**
     * Retrieves all repair job cards ever opened for a particular vehicle.
     *
     * @param vehicleId The primary key ID of the vehicle.
     * @return List of historical job cards.
     */
    List<JobCard> findByVehicleId(Long vehicleId);

    /**
     * Finds job cards filtered by a specific repair status.
     *
     * @param status Current status (e.g. IN_PROGRESS, READY_FOR_DELIVERY).
     * @return List of matching job cards.
     */
    List<JobCard> findByStatus(JobCardStatus status);

    /**
     * Counts how many job cards are currently in a given status.
     *
     * @param status Status filter.
     * @return Number of job cards matching status.
     */
    long countByStatus(JobCardStatus status);

    /**
     * Checks if a job card with the specified number already exists.
     *
     * @param jobCardNumber Job card identifier string.
     * @return {@code true} if present, {@code false} otherwise.
     */
    boolean existsByJobCardNumber(String jobCardNumber);

    /**
     * Case-insensitive substring search by job card number.
     *
     * @param jobCardNumber Partial or full job card number.
     * @return List of matching job cards.
     */
    List<JobCard> findByJobCardNumberContainingIgnoreCase(String jobCardNumber);
}
