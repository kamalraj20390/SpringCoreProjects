package com.placementadda.sms.service.impl;

import com.placementadda.sms.bean.Course;
import com.placementadda.sms.bean.Enrollment;
import com.placementadda.sms.bean.Student;
import com.placementadda.sms.repository.CourseRepository;
import com.placementadda.sms.repository.EnrollmentRepository;
import com.placementadda.sms.repository.StudentRepository;
import com.placementadda.sms.service.EnrollmentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AtomicInteger enrollmentSequence = new AtomicInteger(1001);

    public EnrollmentServiceImpl(StudentRepository studentRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public String enrollStudent(int studentId, int courseId) {
        if (!studentRepository.existsById(studentId)) {
            return "Student not found.";
        }

        if (!courseRepository.existsById(courseId)) {
            return "Course not found.";
        }

        if (enrollmentRepository.exists(studentId, courseId)) {
            return "Student is already enrolled in this course.";
        }

        Enrollment enrollment = new Enrollment(
                enrollmentSequence.getAndIncrement(),
                studentId,
                courseId,
                LocalDate.now()
        );

        enrollmentRepository.save(enrollment);
        return "Enrollment successful.";
    }

    @Override
    public List<Course> getStudentCourses(int studentId) {
        List<Course> result = new ArrayList<>();

        for (Enrollment enrollment : enrollmentRepository.findByStudentId(studentId)) {
            courseRepository.findById(enrollment.getCourseId()).ifPresent(result::add);
        }

        return result;
    }

    @Override
    public List<Student> getCourseStudents(int courseId) {
        List<Student> result = new ArrayList<>();

        for (Enrollment enrollment : enrollmentRepository.findByCourseId(courseId)) {
            studentRepository.findById(enrollment.getStudentId()).ifPresent(result::add);
        }

        return result;
    }
}
