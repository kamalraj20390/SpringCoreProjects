package com.placementadda.sms.repository;

import com.placementadda.sms.bean.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    boolean save(Course course);
    Optional<Course> findById(int courseId);
    List<Course> findAll();
    boolean existsById(int courseId);
}
