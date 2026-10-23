package com.adda.app.controller;

import com.adda.app.entity.Vehicle;
import com.adda.app.enums.FuelType;
import com.adda.app.service.CustomerService;
import com.adda.app.service.VehicleService;
import com.adda.app.service.BookingService;
import com.adda.app.service.JobCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final JobCardService jobCardService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("vehicles", search != null ? vehicleService.search(search) : vehicleService.findAll());
        model.addAttribute("search", search);
        return "vehicles/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("fuelTypes", FuelType.values());
        return "vehicles/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("fuelTypes", FuelType.values());
        return "vehicles/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("vehicle") Vehicle vehicle,
            BindingResult result,
            RedirectAttributes ra, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("fuelTypes", FuelType.values());
            return "vehicles/form";
        }
        try {
            // Bind customer entity by id from form
            if (vehicle.getCustomer() != null && vehicle.getCustomer().getId() != null) {
                vehicle.setCustomer(customerService.findById(vehicle.getCustomer().getId()));
            }
            vehicleService.save(vehicle);
            ra.addFlashAttribute("success", "Vehicle saved successfully.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("fuelTypes", FuelType.values());
            return "vehicles/form";
        }
        return "redirect:/vehicles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            vehicleService.deleteById(id);
            ra.addFlashAttribute("success", "Vehicle deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}/history")
    public String history(@PathVariable Long id, Model model) {
        var vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("bookings", bookingService.findByVehicleId(id));
        model.addAttribute("jobCards", jobCardService.findByVehicleId(id));
        return "vehicles/history";
    }
}
