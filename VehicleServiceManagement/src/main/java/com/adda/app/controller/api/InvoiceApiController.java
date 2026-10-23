package com.adda.app.controller.api;

import com.adda.app.dto.GenerateInvoiceRequestDto;
import com.adda.app.dto.InvoiceDto;
import com.adda.app.dto.PaymentDto;
import com.adda.app.dto.PaymentRequestDto;
import com.adda.app.entity.Invoice;
import com.adda.app.entity.Payment;
import com.adda.app.mapper.EntityDtoMapper;
import com.adda.app.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceApiController {

    private final InvoiceService invoiceService;
    private final EntityDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAll() {
        List<InvoiceDto> dtos = invoiceService.findAll().stream()
                .map(mapper::toInvoiceDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getById(@PathVariable Long id) {
        Invoice invoice = invoiceService.findById(id);
        return ResponseEntity.ok(mapper.toInvoiceDto(invoice));
    }

    @PostMapping("/generate")
    public ResponseEntity<InvoiceDto> generate(@Valid @RequestBody GenerateInvoiceRequestDto request) {
        Invoice invoice = invoiceService.generateInvoice(
                request.getJobCardId(),
                request.getDiscount(),
                request.getTaxPercent());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toInvoiceDto(invoice));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<PaymentDto> recordPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDto request) {
        Payment payment = invoiceService.recordPayment(
                id,
                request.getAmount(),
                request.getPaymentMethod(),
                request.getReferenceNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toPaymentDto(payment));
    }
}
