package com.adda.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adda.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	@Query("SELECT emp.empName,dept.deptName FROM Employee emp RIGHT JOIN emp.dob as dept")
	List<Object[]> getAllEnamesAndDeptNames();
}
