package com.placementadda.sms.service;

import com.placementadda.sms.bean.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    boolean addStudent(Student student);
    Optional<Student> getStudent(int studentId);
    List<Student> getAllStudents();
}
