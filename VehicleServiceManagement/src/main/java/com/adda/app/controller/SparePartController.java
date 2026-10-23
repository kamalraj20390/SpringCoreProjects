package com.adda.app.controller;

import com.adda.app.entity.SparePart;
import com.adda.app.service.SparePartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/parts")
@RequiredArgsConstructor
public class SparePartController {

    private final SparePartService sparePartService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("parts", sparePartService.findAll());
        model.addAttribute("lowStockParts", sparePartService.findLowStockParts());
        return "parts/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("part", new SparePart());
        return "parts/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("part", sparePartService.findById(id));
        return "parts/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("part") SparePart part,
            BindingResult result,
            RedirectAttributes ra, Model model) {
        if (result.hasErrors())
            return "parts/form";
        try {
            sparePartService.save(part);
            ra.addFlashAttribute("success", "Spare part saved.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "parts/form";
        }
        return "redirect:/parts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            sparePartService.deleteById(id);
            ra.addFlashAttribute("success", "Part deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/parts";
    }
}
