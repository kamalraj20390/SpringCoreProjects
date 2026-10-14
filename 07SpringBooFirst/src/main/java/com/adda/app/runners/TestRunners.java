package com.adda.app.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.adda.app.bean.Student;
@Component
@Order(1)
public class TestRunners implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Student student=new Student(101, "kamal");
		System.out.println(student+" TestRunner");

	}

}
