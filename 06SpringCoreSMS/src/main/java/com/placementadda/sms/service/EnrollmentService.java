package com.placementadda.sms.service;

import com.placementadda.sms.bean.Course;
import com.placementadda.sms.bean.Student;
import java.util.List;

public interface EnrollmentService {
    String enrollStudent(int studentId, int courseId);
    List<Course> getStudentCourses(int studentId);
    List<Student> getCourseStudents(int courseId);
}
