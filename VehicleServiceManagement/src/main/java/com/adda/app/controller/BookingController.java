package com.adda.app.controller;

import com.adda.app.entity.ServiceBooking;
import com.adda.app.enums.BookingStatus;
import com.adda.app.service.BookingService;
import com.adda.app.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final VehicleService vehicleService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        model.addAttribute("statuses", BookingStatus.values());
        return "bookings/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("booking", new ServiceBooking());
        model.addAttribute("vehicles", vehicleService.findAll());
        return "bookings/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("booking") ServiceBooking booking,
            BindingResult result,
            RedirectAttributes ra, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("vehicles", vehicleService.findAll());
            return "bookings/form";
        }
        try {
            if (booking.getVehicle() != null && booking.getVehicle().getId() != null) {
                booking.setVehicle(vehicleService.findById(booking.getVehicle().getId()));
            }
            bookingService.save(booking);
            ra.addFlashAttribute("success", "Booking saved.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("vehicles", vehicleService.findAll());
            return "bookings/form";
        }
        return "redirect:/bookings";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id, RedirectAttributes ra) {
        try {
            bookingService.updateStatus(id, BookingStatus.CANCELLED);
            ra.addFlashAttribute("success", "Booking cancelled.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings";
    }

    @PostMapping("/{id}/confirm")
    public String confirm(@PathVariable Long id, RedirectAttributes ra) {
        try {
            bookingService.updateStatus(id, BookingStatus.CONFIRMED);
            ra.addFlashAttribute("success", "Booking confirmed.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings";
    }

    @GetMapping("/{id}/convert")
    public String convertForm(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.findById(id));
        return "bookings/convert";
    }

    @PostMapping("/{id}/convert")
    public String convert(@PathVariable Long id,
            @RequestParam String mechanicName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate,
            RedirectAttributes ra) {
        try {
            var jobCard = bookingService.convertToJobCard(id, mechanicName, targetDate);
            ra.addFlashAttribute("success", "Job card created: " + jobCard.getJobCardNumber());
            return "redirect:/job-cards/" + jobCard.getId();
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookings";
        }
    }
}
