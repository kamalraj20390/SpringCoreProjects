package com.placementadda.sms.repository.impl;

import com.placementadda.sms.bean.Student;
import com.placementadda.sms.repository.StudentRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentRepositoryImpl implements StudentRepository {
    private final Map<Integer, Student> students = new LinkedHashMap<>();

    @Override
    public boolean save(Student student) {
        if (students.containsKey(student.getStudentId())) {
            return false;
        }
        students.put(student.getStudentId(), student);
        return true;
    }

    @Override
    public Optional<Student> findById(int studentId) {
        return Optional.ofNullable(students.get(studentId));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public boolean existsById(int studentId) {
        return students.containsKey(studentId);
    }
}
