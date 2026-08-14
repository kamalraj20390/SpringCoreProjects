package com.placementadda.sms.repository;

import com.placementadda.sms.bean.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    boolean save(Student student);
    Optional<Student> findById(int studentId);
    List<Student> findAll();
    boolean existsById(int studentId);
}
