package com.adda.app.service;

import java.util.List;
import java.util.Optional;

import com.adda.app.entity.Student;

public interface StudentService {
	 boolean addStudent(Student student);
	    Optional<Student> getStudent(int studentId);
	    List<Student> getAllStudents();
	    void deleteStudent(Integer id);
}
