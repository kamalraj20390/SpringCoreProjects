package com.adda.app.controller;

import com.adda.app.enums.PaymentMethod;
import com.adda.app.service.InvoiceService;
import com.adda.app.service.JobCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final JobCardService jobCardService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());
        return "invoices/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        var invoice = invoiceService.findById(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("outstanding", invoiceService.getOutstandingBalance(id));
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "invoices/details";
    }

    @PostMapping("/generate/{jobCardId}")
    public String generate(@PathVariable Long jobCardId,
            @RequestParam(defaultValue = "0") BigDecimal discount,
            @RequestParam(defaultValue = "0") BigDecimal taxPercent,
            RedirectAttributes ra) {
        try {
            var invoice = invoiceService.generateInvoice(jobCardId, discount, taxPercent);
            ra.addFlashAttribute("success", "Invoice generated: " + invoice.getInvoiceNumber());
            return "redirect:/invoices/" + invoice.getId();
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/job-cards/" + jobCardId;
        }
    }

    @PostMapping("/{id}/pay")
    public String recordPayment(@PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam PaymentMethod paymentMethod,
            @RequestParam(required = false) String referenceNumber,
            RedirectAttributes ra) {
        try {
            invoiceService.recordPayment(id, amount, paymentMethod, referenceNumber);
            ra.addFlashAttribute("success", "Payment recorded successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices/" + id;
    }
}
