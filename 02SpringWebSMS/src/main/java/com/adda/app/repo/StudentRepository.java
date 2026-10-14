package com.adda.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adda.app.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
