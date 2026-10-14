package com.adda.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adda.app.entity.Student;
import com.adda.app.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService service;
	@GetMapping("/list")
	public String home(
			Model model
			) {
		List<Student> students=service.getAllStudents();
		model.addAttribute("students",students);
		return "StudentsList";
	}
	 @GetMapping("/add")
	    public String showAddForm(Model model) {
	        model.addAttribute("student", new Student());
	        model.addAttribute("formAction", "/student/save");
	        return "StudentForm";
	    }
	 @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable("id") int id,
	                               Model model) {

	        Student student = service.getStudent(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Student not found"));

	        model.addAttribute("student", student);
	        model.addAttribute("formAction", "/student/save");

	        return "StudentForm";
	    }
	@PostMapping("/save")
	public String registerStudent(
			@ModelAttribute Student student,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		
		if(service.addStudent(student)) {
			redirectAttributes.addFlashAttribute(
				    "success", "Student registered successfully!");
		}
		return "redirect:/student/list";
	}
	
	@PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id,
    		RedirectAttributes redirectAttributes) {
        service.deleteStudent(id);
        redirectAttributes.addFlashAttribute(
                "deleteMsg", "Student deleted successfully!");
        return "redirect:/student/list";
    }
	
	@GetMapping("/view/{id}")
    public String viewStudent(@PathVariable("id") int id,
                              Model model) {

        Student student = service.getStudent(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        return "StudentView";
    }

}
