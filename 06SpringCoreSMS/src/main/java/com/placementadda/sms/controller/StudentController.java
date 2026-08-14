package com.placementadda.sms.controller;

import com.placementadda.sms.bean.Course;
import com.placementadda.sms.bean.Student;
import com.placementadda.sms.service.CourseService;
import com.placementadda.sms.service.EnrollmentService;
import com.placementadda.sms.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Scanner scanner = new Scanner(System.in);

    public StudentController(StudentService studentService,
                             CourseService courseService,
                             EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudent();
                    case 3 -> viewAllStudents();
                    case 4 -> addCourse();
                    case 5 -> viewCourse();
                    case 6 -> viewAllCourses();
                    case 7 -> enrollStudent();
                    case 8 -> viewStudentCourses();
                    case 9 -> viewCourseStudents();
                    case 10 -> {
                        running = false;
                        System.out.println("\nApplication closed successfully.");
                    }
                    default -> System.out.println("\nInvalid choice.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("\nError: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("\nUnexpected error: " + ex.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=================================================");
        System.out.println("        STUDENT MANAGEMENT SYSTEM");
        System.out.println("=================================================");
        System.out.println("1.  Add Student");
        System.out.println("2.  View Student");
        System.out.println("3.  View All Students");
        System.out.println("4.  Add Course");
        System.out.println("5.  View Course");
        System.out.println("6.  View All Courses");
        System.out.println("7.  Enroll Student in Course");
        System.out.println("8.  View Student Courses");
        System.out.println("9.  View Course Students");
        System.out.println("10. Exit");
        System.out.println("=================================================");
    }

    private void addStudent() {
        System.out.println("\n--- Add Student ---");

        int id = readInt("Student ID: ");
        String name = readText("Name: ");
        String email = readText("Email: ");
        String mobile = readText("Mobile: ");
        String city = readText("City: ");

        boolean added = studentService.addStudent(
                new Student(id, name, email, mobile, city)
        );

        System.out.println(added
                ? "Student added successfully."
                : "Student ID already exists.");
    }

    private void viewStudent() {
        System.out.println("\n--- View Student ---");
        int id = readInt("Student ID: ");

        studentService.getStudent(id).ifPresentOrElse(
                student -> {
                    System.out.println("\nStudent Details");
                    printStudentHeader();
                    System.out.println(student);
                },
                () -> System.out.println("Student not found.")
        );
    }

    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        printStudentHeader();
        students.forEach(System.out::println);
    }

    private void addCourse() {
        System.out.println("\n--- Add Course ---");

        int id = readInt("Course ID: ");
        String name = readText("Course Name: ");
        String duration = readText("Duration: ");
        double fee = readDouble("Fee: ");

        boolean added = courseService.addCourse(
                new Course(id, name, duration, fee)
        );

        System.out.println(added
                ? "Course added successfully."
                : "Course ID already exists.");
    }

    private void viewCourse() {
        System.out.println("\n--- View Course ---");
        int id = readInt("Course ID: ");

        courseService.getCourse(id).ifPresentOrElse(
                course -> {
                    System.out.println("\nCourse Details");
                    printCourseHeader();
                    System.out.println(course);
                },
                () -> System.out.println("Course not found.")
        );
    }

    private void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        printCourseHeader();
        courses.forEach(System.out::println);
    }

    private void enrollStudent() {
        System.out.println("\n--- Enroll Student ---");

        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");

        System.out.println(enrollmentService.enrollStudent(studentId, courseId));
    }

    private void viewStudentCourses() {
        System.out.println("\n--- Student Courses ---");

        int studentId = readInt("Student ID: ");

        var student = studentService.getStudent(studentId);
        if (student.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }

        List<Course> courses = enrollmentService.getStudentCourses(studentId);

        System.out.println("\nStudent: " + student.get().getName());

        if (courses.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        printCourseHeader();
        courses.forEach(System.out::println);
    }

    private void viewCourseStudents() {
        System.out.println("\n--- Course Students ---");

        int courseId = readInt("Course ID: ");

        var course = courseService.getCourse(courseId);
        if (course.isEmpty()) {
            System.out.println("Course not found.");
            return;
        }

        List<Student> students = enrollmentService.getCourseStudents(courseId);

        System.out.println("\nCourse: " + course.get().getCourseName());

        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        printStudentHeader();
        students.forEach(System.out::println);
    }

    private void printStudentHeader() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-8s %-20s %-28s %-15s %-15s%n",
                "ID", "Name", "Email", "Mobile", "City");
        System.out.println("--------------------------------------------------------------------------------");
    }

    private void printCourseHeader() {
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-8s %-25s %-15s %-10s%n",
                "ID", "Course Name", "Duration", "Fee");
        System.out.println("----------------------------------------------------------------");
    }

    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
