package com.adda.app.service.impl;

import com.adda.app.entity.JobCard;
import com.adda.app.entity.JobCardItem;
import com.adda.app.entity.JobCardPart;
import com.adda.app.entity.SparePart;
import com.adda.app.enums.ItemType;
import com.adda.app.enums.JobCardStatus;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.JobCardRepository;
import com.adda.app.repository.SparePartRepository;
import com.adda.app.service.JobCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link JobCardService} governing vehicle repair workflows.
 *
 * <p>Core responsibilities include:
 * <ul>
 *   <li>Enforcing the strict state transition graph for workshop repairs.</li>
 *   <li>Atomic deduction of spare parts inventory upon assignment.</li>
 *   <li>Managing labor items and parts attached to repair job cards.</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class JobCardServiceImpl implements JobCardService {

    private final JobCardRepository jobCardRepository;
    private final SparePartRepository sparePartRepository;

    /**
     * Finite State Machine (FSM) defining valid status transitions for a job card.
     * Prevents invalid state jumps (e.g. RECEIVED cannot jump directly to DELIVERED).
     */
    private static final java.util.Map<JobCardStatus, Set<JobCardStatus>> ALLOWED_TRANSITIONS = java.util.Map.of(
            JobCardStatus.RECEIVED, Set.of(JobCardStatus.DIAGNOSIS, JobCardStatus.CANCELLED),
            JobCardStatus.DIAGNOSIS,
            Set.of(JobCardStatus.IN_PROGRESS, JobCardStatus.WAITING_FOR_PARTS, JobCardStatus.CANCELLED),
            JobCardStatus.WAITING_FOR_PARTS, Set.of(JobCardStatus.IN_PROGRESS, JobCardStatus.CANCELLED),
            JobCardStatus.IN_PROGRESS,
            Set.of(JobCardStatus.READY_FOR_DELIVERY, JobCardStatus.WAITING_FOR_PARTS, JobCardStatus.CANCELLED),
            JobCardStatus.READY_FOR_DELIVERY, Set.of(JobCardStatus.DELIVERED),
            JobCardStatus.DELIVERED, Set.of(),
            JobCardStatus.CANCELLED, Set.of());

    @Override
    public List<JobCard> findAll() {
        return jobCardRepository.findAll();
    }

    @Override
    public JobCard findById(Long id) {
        return jobCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job card not found with id: " + id));
    }

    @Override
    @Transactional
    public JobCard save(JobCard jobCard) {
        if (jobCard.getOpenedAt() == null) {
            jobCard.setOpenedAt(LocalDateTime.now());
        }
        return jobCardRepository.save(jobCard);
    }

    @Override
    @Transactional
    public JobCard addLaborItem(Long jobCardId, String description, int quantity, BigDecimal unitPrice,
            ItemType itemType) {
        JobCard jobCard = findById(jobCardId);
        JobCardItem item = new JobCardItem();
        item.setJobCard(jobCard);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setItemType(itemType);
        jobCard.getItems().add(item);
        return jobCardRepository.save(jobCard);
    }

    @Override
    @Transactional
    public JobCard addPart(Long jobCardId, Long sparePartId, int quantity) {
        if (quantity <= 0) {
            throw new BusinessRuleException("Quantity must be positive.");
        }
        JobCard jobCard = findById(jobCardId);
        SparePart part = sparePartRepository.findById(sparePartId)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + sparePartId));

        if (part.getStockQuantity() < quantity) {
            throw new BusinessRuleException(
                    "Insufficient stock for '" + part.getName() + "'. Available: " + part.getStockQuantity());
        }

        // Deduct stock atomically in same transaction
        part.setStockQuantity(part.getStockQuantity() - quantity);
        sparePartRepository.save(part);

        JobCardPart jcp = new JobCardPart();
        jcp.setJobCard(jobCard);
        jcp.setSparePart(part);
        jcp.setQuantity(quantity);
        jcp.setUnitPrice(part.getUnitPrice()); // price snapshot
        jobCard.getParts().add(jcp);

        return jobCardRepository.save(jobCard);
    }

    @Override
    @Transactional
    public JobCard updateStatus(Long jobCardId, JobCardStatus newStatus) {
        JobCard jobCard = findById(jobCardId);
        JobCardStatus current = jobCard.getStatus();
        Set<JobCardStatus> allowed = ALLOWED_TRANSITIONS.getOrDefault(current, Set.of());
        if (!allowed.contains(newStatus)) {
            throw new BusinessRuleException(
                    "Cannot transition from " + current + " to " + newStatus);
        }
        jobCard.setStatus(newStatus);
        if (newStatus == JobCardStatus.DELIVERED) {
            jobCard.setCompletedAt(LocalDateTime.now());
        }
        return jobCardRepository.save(jobCard);
    }

    @Override
    public List<JobCard> findByVehicleId(Long vehicleId) {
        return jobCardRepository.findByVehicleId(vehicleId);
    }

    @Override
    public long countByStatus(JobCardStatus status) {
        return jobCardRepository.countByStatus(status);
    }
}
