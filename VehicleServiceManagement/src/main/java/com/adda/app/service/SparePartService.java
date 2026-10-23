package com.adda.app.service;

import com.adda.app.entity.SparePart;
import java.util.List;

/**
 * Service interface for spare part inventory tracking and threshold alerts.
 */
public interface SparePartService {

    /**
     * Lists all spare parts cataloged in inventory.
     *
     * @return List of all spare parts.
     */
    List<SparePart> findAll();

    /**
     * Finds a spare part by its database ID.
     *
     * @param id Primary key ID of the spare part.
     * @return The found {@link SparePart}.
     */
    SparePart findById(Long id);

    /**
     * Persists or updates a spare part record, checking unique part code.
     *
     * @param part Part entity to save.
     * @return Saved SparePart instance.
     */
    SparePart save(SparePart part);

    /**
     * Deletes a spare part by its ID.
     *
     * @param id Primary key ID.
     */
    void deleteById(Long id);

    /**
     * Finds all parts whose stock count is at or below their low stock threshold.
     *
     * @return List of parts needing replenishment.
     */
    List<SparePart> findLowStockParts();

    /**
     * Counts how many parts are currently in a low stock condition.
     *
     * @return Number of low stock parts.
     */
    long countLowStockParts();
}
