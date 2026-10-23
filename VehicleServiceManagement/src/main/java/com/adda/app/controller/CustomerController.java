package com.adda.app.controller;

import com.adda.app.entity.Customer;
import com.adda.app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("customers", search != null ? customerService.search(search) : customerService.findAll());
        model.addAttribute("search", search);
        return "customers/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "customers/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer,
            BindingResult result,
            RedirectAttributes ra, Model model) {
        if (result.hasErrors())
            return "customers/form";
        try {
            customerService.save(customer);
            ra.addFlashAttribute("success", "Customer saved successfully.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "customers/form";
        }
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            customerService.deleteById(id);
            ra.addFlashAttribute("success", "Customer deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customers";
    }
}
