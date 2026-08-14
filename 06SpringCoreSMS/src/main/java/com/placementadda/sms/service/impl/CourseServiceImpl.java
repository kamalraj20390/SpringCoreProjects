package com.placementadda.sms.service.impl;

import com.placementadda.sms.bean.Course;
import com.placementadda.sms.repository.CourseRepository;
import com.placementadda.sms.service.CourseService;

import java.util.List;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public boolean addCourse(Course course) {
        validate(course);
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourse(int courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    private void validate(Course course) {
        if (course.getCourseId() <= 0) {
            throw new IllegalArgumentException("Course ID must be greater than 0.");
        }
        if (isBlank(course.getCourseName())) {
            throw new IllegalArgumentException("Course name is required.");
        }
        if (isBlank(course.getDuration())) {
            throw new IllegalArgumentException("Course duration is required.");
        }
        if (course.getFee() < 0) {
            throw new IllegalArgumentException("Course fee cannot be negative.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
