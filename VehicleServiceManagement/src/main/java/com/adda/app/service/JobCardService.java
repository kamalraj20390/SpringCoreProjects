package com.adda.app.service;

import com.adda.app.entity.JobCard;
import com.adda.app.entity.JobCardItem;
import com.adda.app.enums.ItemType;
import com.adda.app.enums.JobCardStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface governing workshop job card repair workflows, labor tracking, and part issuances.
 */
public interface JobCardService {

    /**
     * Retrieves all repair job cards.
     *
     * @return List of all job cards.
     */
    List<JobCard> findAll();

    /**
     * Looks up a job card by ID.
     *
     * @param id Primary key ID of the job card.
     * @return The matching {@link JobCard}.
     */
    JobCard findById(Long id);

    /**
     * Saves or creates a job card entity.
     *
     * @param jobCard JobCard to persist.
     * @return The persisted JobCard.
     */
    JobCard save(JobCard jobCard);

    /**
     * Appends a labor or service line item to an active job card.
     *
     * @param jobCardId ID of the target job card.
     * @param description Nature of work performed.
     * @param quantity Number of labor units/hours.
     * @param unitPrice Price per unit.
     * @param itemType Category of item (LABOUR, SERVICE).
     * @return Updated JobCard.
     */
    JobCard addLaborItem(Long jobCardId, String description, int quantity, BigDecimal unitPrice, ItemType itemType);

    /**
     * Issues a spare part to a job card and atomically deducts the stock from inventory.
     *
     * @param jobCardId ID of the target job card.
     * @param sparePartId ID of the spare part from inventory.
     * @param quantity Units to issue.
     * @return Updated JobCard.
     */
    JobCard addPart(Long jobCardId, Long sparePartId, int quantity);

    /**
     * Validates and executes a status change following the finite state machine graph.
     *
     * @param jobCardId ID of the job card.
     * @param newStatus Target status to transition to.
     * @return Updated JobCard.
     */
    JobCard updateStatus(Long jobCardId, JobCardStatus newStatus);

    /**
     * Retrieves all job cards associated with a given vehicle.
     *
     * @param vehicleId Primary key ID of the vehicle.
     * @return List of job cards.
     */
    List<JobCard> findByVehicleId(Long vehicleId);

    /**
     * Counts how many job cards are in a specific status.
     *
     * @param status The status filter.
     * @return Total count.
     */
    long countByStatus(JobCardStatus status);
}
