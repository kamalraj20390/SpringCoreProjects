package com.adda.app.controller;

import com.adda.app.entity.JobCard;
import com.adda.app.enums.ItemType;
import com.adda.app.enums.JobCardStatus;
import com.adda.app.service.JobCardService;
import com.adda.app.service.SparePartService;
import com.adda.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/job-cards")
@RequiredArgsConstructor
public class JobCardController {

    private final JobCardService jobCardService;
    private final VehicleService vehicleService;
    private final SparePartService sparePartService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("jobCards", jobCardService.findAll());
        model.addAttribute("statuses", JobCardStatus.values());
        return "job-cards/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("jobCard", new JobCard());
        model.addAttribute("vehicles", vehicleService.findAll());
        return "job-cards/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("jobCard") JobCard jobCard,
            @RequestParam String mechanicName,
            @RequestParam Long vehicleId,
            @RequestParam(required = false) String complaint,
            @RequestParam(required = false) String inspectionNotes,
            @RequestParam(required = false) LocalDate targetCompletionDate,
            RedirectAttributes ra) {
        try {
            jobCard.setVehicle(vehicleService.findById(vehicleId));
            jobCard.setMechanicName(mechanicName);
            jobCard.setComplaint(complaint);
            jobCard.setInspectionNotes(inspectionNotes);
            jobCard.setTargetCompletionDate(targetCompletionDate);
            jobCard.setOpenedAt(LocalDateTime.now());
            jobCard.setStatus(JobCardStatus.RECEIVED);

            // Generate job card number
            String jcNum = "JC-" + LocalDate.now().toString().replace("-", "") + "-"
                    + String.format("%04d", jobCardService.findAll().size() + 1);
            jobCard.setJobCardNumber(jcNum);

            JobCard saved = jobCardService.save(jobCard);
            ra.addFlashAttribute("success", "Job card created: " + saved.getJobCardNumber());
            return "redirect:/job-cards/" + saved.getId();
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/job-cards/new";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        JobCard jc = jobCardService.findById(id);
        model.addAttribute("jobCard", jc);
        model.addAttribute("spareParts", sparePartService.findAll());
        model.addAttribute("statuses", JobCardStatus.values());
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("hasInvoice", jc.getInvoice() != null);
        return "job-cards/details";
    }

    @PostMapping("/{id}/add-item")
    public String addItem(@PathVariable Long id,
            @RequestParam String description,
            @RequestParam int quantity,
            @RequestParam BigDecimal unitPrice,
            @RequestParam ItemType itemType,
            RedirectAttributes ra) {
        try {
            jobCardService.addLaborItem(id, description, quantity, unitPrice, itemType);
            ra.addFlashAttribute("success", "Item added.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/job-cards/" + id;
    }

    @PostMapping("/{id}/add-part")
    public String addPart(@PathVariable Long id,
            @RequestParam Long sparePartId,
            @RequestParam int quantity,
            RedirectAttributes ra) {
        try {
            jobCardService.addPart(id, sparePartId, quantity);
            ra.addFlashAttribute("success", "Part added and stock updated.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/job-cards/" + id;
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
            @RequestParam JobCardStatus newStatus,
            RedirectAttributes ra) {
        try {
            jobCardService.updateStatus(id, newStatus);
            ra.addFlashAttribute("success", "Status updated to " + newStatus);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/job-cards/" + id;
    }
}
