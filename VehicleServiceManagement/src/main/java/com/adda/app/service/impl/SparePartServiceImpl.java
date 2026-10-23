package com.adda.app.service.impl;

import com.adda.app.entity.SparePart;
import com.adda.app.exception.BusinessRuleException;
import com.adda.app.exception.ResourceNotFoundException;
import com.adda.app.repository.SparePartRepository;
import com.adda.app.service.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for {@link SparePartService}.
 *
 * <p>Enforces unique part codes and monitors low stock inventory alerts.</p>
 */
@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

    private final SparePartRepository sparePartRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SparePart> findAll() {
        return sparePartRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SparePart findById(Long id) {
        return sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id));
    }

    /**
     * Saves a spare part while enforcing unique SKU / part codes.
     */
    @Override
    @Transactional
    public SparePart save(SparePart part) {
        String code = part.getPartCode();
        if (part.getId() == null) {
            if (sparePartRepository.existsByPartCode(code)) {
                throw new BusinessRuleException("Part code already exists: " + code);
            }
        } else {
            if (sparePartRepository.existsByPartCodeAndIdNot(code, part.getId())) {
                throw new BusinessRuleException("Part code already exists: " + code);
            }
        }
        return sparePartRepository.save(part);
    }

    /**
     * Removes a spare part from the catalog.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        SparePart part = findById(id);
        sparePartRepository.delete(part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SparePart> findLowStockParts() {
        return sparePartRepository.findLowStockParts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countLowStockParts() {
        return sparePartRepository.countLowStockParts();
    }
}
