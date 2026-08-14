package com.placementadda.sms.service;

import com.placementadda.sms.bean.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    boolean addCourse(Course course);
    Optional<Course> getCourse(int courseId);
    List<Course> getAllCourses();
}
