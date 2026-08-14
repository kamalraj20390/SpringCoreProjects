package com.placementadda.sms.service.impl;

import com.placementadda.sms.bean.Student;
import com.placementadda.sms.repository.StudentRepository;
import com.placementadda.sms.service.StudentService;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean addStudent(Student student) {
        validate(student);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudent(int studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    private void validate(Student student) {
        if (student.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }
        if (isBlank(student.getName())) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (isBlank(student.getEmail())) {
            throw new IllegalArgumentException("Student email is required.");
        }
        if (isBlank(student.getMobile())) {
            throw new IllegalArgumentException("Student mobile is required.");
        }
        if (isBlank(student.getCity())) {
            throw new IllegalArgumentException("Student city is required.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
