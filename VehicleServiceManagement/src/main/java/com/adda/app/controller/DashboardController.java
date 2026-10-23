package com.adda.app.controller;

import com.adda.app.enums.JobCardStatus;
import com.adda.app.enums.PaymentStatus;
import com.adda.app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DashboardController {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingService bookingService;
    private final JobCardService jobCardService;
    private final SparePartService sparePartService;
    private final InvoiceService invoiceService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalCustomers", customerService.findAll().size());
        model.addAttribute("totalVehicles", vehicleService.findAll().size());
        model.addAttribute("todaysBookings", bookingService.countTodaysBookings());
        model.addAttribute("vehiclesUnderRepair", jobCardService.countByStatus(JobCardStatus.IN_PROGRESS));
        model.addAttribute("readyForDelivery", jobCardService.countByStatus(JobCardStatus.READY_FOR_DELIVERY));
        model.addAttribute("unpaidInvoices", invoiceService.countUnpaidInvoices());
        model.addAttribute("lowStockParts", sparePartService.countLowStockParts());
        model.addAttribute("recentJobCards", jobCardService.findAll().stream().limit(5).toList());
        return "dashboard";
    }
}
