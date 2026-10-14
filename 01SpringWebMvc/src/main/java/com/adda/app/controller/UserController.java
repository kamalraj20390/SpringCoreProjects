package com.adda.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adda.app.bean.Student;

@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping("/home")
	public String showHomePage() {
		return "UserHome";
	}
	@GetMapping("/register")
	public String registerPage(Map model) {
		model.put("test", "Kamal");
		return "Register";
	}
	@PostMapping("/info")
	public String registerInfo(
			@ModelAttribute Student student,
			Model model
			) {
		List<Student> students=Arrays.asList(
				new Student("ABC1", "a1@gmail.com", "123456"),
				new Student("ABC2", "a2@gmail.com", "123456"),
				new Student("ABC3", "a3@gmail.com", "123456"),
				new Student("ABC4", "a4@gmail.com", "123456"),
				new Student("ABC5", "a5@gmail.com", "123456"),
				new Student("ABC6", "a6@gmail.com", "123456"),
				new Student("ABC7", "a7@gmail.com", "123456"),
				new Student("ABC8", "a8@gmail.com", "123456")
				);
		model.addAttribute("student", student);
		model.addAttribute("students", students);
		return "Info";
	}
}
