package com.adda.app.repository;

import com.adda.app.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for inventory management of {@link SparePart} entities.
 */
@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Long> {

    /**
     * Finds a spare part by its unique part code.
     *
     * @param partCode The unique SKU/part identifier.
     * @return Optional holding the matching spare part if found.
     */
    Optional<SparePart> findByPartCode(String partCode);

    /**
     * Checks if a spare part with the given part code already exists.
     *
     * @param partCode The SKU to check.
     * @return {@code true} if present, {@code false} otherwise.
     */
    boolean existsByPartCode(String partCode);

    /**
     * Checks if another spare part with the same code exists, excluding a given ID (for update checks).
     *
     * @param partCode The SKU to verify uniqueness against.
     * @param id The ID of the part currently being updated.
     * @return {@code true} if conflict exists, {@code false} otherwise.
     */
    boolean existsByPartCodeAndIdNot(String partCode, Long id);

    /**
     * Queries all spare parts where stock levels have fallen to or below the low stock threshold.
     *
     * @return List of parts requiring restock.
     */
    @Query("SELECT s FROM SparePart s WHERE s.stockQuantity <= s.lowStockThreshold")
    List<SparePart> findLowStockParts();

    /**
     * Counts the total number of parts currently marked as low in stock.
     *
     * @return Count of low stock items.
     */
    @Query("SELECT COUNT(s) FROM SparePart s WHERE s.stockQuantity <= s.lowStockThreshold")
    long countLowStockParts();
}
