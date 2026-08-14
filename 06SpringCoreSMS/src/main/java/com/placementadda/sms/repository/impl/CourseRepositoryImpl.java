package com.placementadda.sms.repository.impl;

import com.placementadda.sms.bean.Course;
import com.placementadda.sms.repository.CourseRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseRepositoryImpl implements CourseRepository {
    private final Map<Integer, Course> courses = new LinkedHashMap<>();

    @Override
    public boolean save(Course course) {
        if (courses.containsKey(course.getCourseId())) {
            return false;
        }
        courses.put(course.getCourseId(), course);
        return true;
    }

    @Override
    public Optional<Course> findById(int courseId) {
        return Optional.ofNullable(courses.get(courseId));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public boolean existsById(int courseId) {
        return courses.containsKey(courseId);
    }
}
