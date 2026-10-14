package com.adda.app.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.adda.app.bean.DbCon;
import com.adda.app.entity.Student;
import com.adda.app.repo.StudentRepo;
@Component
public class TestRunner implements CommandLineRunner{
	@Autowired
	private DbCon con;
	@Autowired
	private StudentRepo repo;

	@Override
	public void run(String... args) throws Exception {
		List<Student> students=Arrays.asList(
		new Student("S1", 100.0),
		new Student("S2", 100.0),
		new Student("S3", 100.0),
		new Student("S4", 100.0),
		new Student("S5", 100.0),
		new Student("S6", 100.0),
		new Student("S7", 100.0),
		new Student("S8", 100.0),
		new Student("S9", 100.0)
		);
		repo.saveAll(students);
		//repo.findAll(Sort.by(Direction.DESC,"stdId","stdName"))
		/*
		 * repo.findAll(Sort.by(Order.asc("stdId"))) .forEach(System.out::println);
		 */
		/*
		 * Student sob=new Student(); //sob.setStdId(101); //sob.setStdName("S1");
		 * sob.setStdFee(100.0); Example<Student> eob=Example.of(sob); Pageable
		 * p=PageRequest.of(0, 3, Direction.DESC,"stdName"); Page<Student>
		 * page=repo.findAll(eob,p); page.getContent().forEach(System.out::println);
		 * System.out.println(page.hasNext()); System.out.println(page.hasPrevious());
		 * System.out.println(page.isFirst()); System.out.println(page.isLast());
		 * System.out.println(page.getTotalPages());
		 * System.out.println(page.getTotalElements());
		 */
		//repo.findByStdNameAndStdFee("s1",100.0).forEach(System.out::println);
		//repo.getAllStudents().forEach(System.out::println);
		//repo.getAllStudentsNameAndFee().forEach(System.out::println);
		System.out.println(repo.getStudentById(1).get());
	}


}
