package com.adda.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adda.app.entity.Student;
import com.adda.app.repo.StudentRepository;
import com.adda.app.service.StudentService;
@Service
public class StudentServiceimpl implements StudentService {
	@Autowired
	private StudentRepository repo;
	@Override
	public boolean addStudent(Student student) {
		
		return repo.save(student)!=null;
	}

	@Override
	public Optional<Student> getStudent(int studentId) {
		return repo.findById(studentId);
	}

	@Override
	public List<Student> getAllStudents() {
		return repo.findAll();
	}

	@Override
	public void deleteStudent(Integer id) {
		repo.deleteById(id);

	}

}
