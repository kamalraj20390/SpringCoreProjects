package com.adda.app.controller.api;

import com.adda.app.dto.SparePartDto;
import com.adda.app.dto.SparePartRequestDto;
import com.adda.app.entity.SparePart;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.SparePartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spare-parts")
@RequiredArgsConstructor
public class SparePartApiController {

    private final SparePartService sparePartService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<SparePartDto>> getAll(@RequestParam(required = false) String search) {
        List<SparePart> parts = sparePartService.findAll();
        if (search != null && !search.isBlank()) {
            String q = search.toLowerCase();
            parts = parts.stream()
                    .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(q))
                            || (p.getPartCode() != null && p.getPartCode().toLowerCase().contains(q)))
                    .toList();
        }
        List<SparePartDto> dtos = parts.stream().map(mapper::toSparePartDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartDto> getById(@PathVariable Long id) {
        SparePart part = sparePartService.findById(id);
        return ResponseEntity.ok(mapper.toSparePartDto(part));
    }

    @PostMapping
    public ResponseEntity<SparePartDto> create(@Valid @RequestBody SparePartRequestDto request) {
        SparePart part = mapper.toSparePartEntity(request);
        SparePart saved = sparePartService.save(part);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toSparePartDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartDto> update(@PathVariable Long id, @Valid @RequestBody SparePartRequestDto request) {
        SparePart part = sparePartService.findById(id);
        part.setPartCode(request.getPartCode());
        part.setName(request.getName());
        part.setDescription(request.getDescription());
        part.setUnitPrice(request.getUnitPrice());
        part.setStockQuantity(request.getStockQuantity());
        part.setLowStockThreshold(request.getLowStockThreshold());
        SparePart updated = sparePartService.save(part);
        return ResponseEntity.ok(mapper.toSparePartDto(updated));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<SparePartDto>> getLowStockParts() {
        List<SparePartDto> dtos = sparePartService.findLowStockParts().stream()
                .map(mapper::toSparePartDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
