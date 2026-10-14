package com.adda.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adda.app.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	
	List<Student> findByStdNameAndStdFee(String stdName, Double stdFee);
	@Query("SELECT s FROM Student s")
	List<Student> getAllStudents();
	@Query("SELECT s.stdName FROM Student s")
	List<String> getAllStudentsName();
	@Query("SELECT s.stdName,s.stdFee FROM Student s")
	List<Object[]> getAllStudentsNameAndFee();
	@Query("SELECT s FROM Student s WHERE s.stdId=:pqr")
	Optional<Student> getStudentById(@Param("pqr") Integer id);
}
