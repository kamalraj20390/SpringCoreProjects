package com.placementadda.sms.repository.impl;

import com.placementadda.sms.bean.Enrollment;
import com.placementadda.sms.repository.EnrollmentRepository;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public boolean save(Enrollment enrollment) {
        if (exists(enrollment.getStudentId(), enrollment.getCourseId())) {
            return false;
        }
        enrollments.add(enrollment);
        return true;
    }

    @Override
    public boolean exists(int studentId, int courseId) {
        return enrollments.stream()
                .anyMatch(e -> e.getStudentId() == studentId
                        && e.getCourseId() == courseId);
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        return enrollments.stream()
                .filter(e -> e.getStudentId() == studentId)
                .toList();
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        return enrollments.stream()
                .filter(e -> e.getCourseId() == courseId)
                .toList();
    }
}
