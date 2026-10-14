package com.adda.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adda.app.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
