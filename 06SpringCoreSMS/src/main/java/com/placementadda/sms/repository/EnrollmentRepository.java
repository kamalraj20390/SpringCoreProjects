package com.placementadda.sms.repository;

import com.placementadda.sms.bean.Enrollment;
import java.util.List;

public interface EnrollmentRepository {
    boolean save(Enrollment enrollment);
    boolean exists(int studentId, int courseId);
    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);
}
