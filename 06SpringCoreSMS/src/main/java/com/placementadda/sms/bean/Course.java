package com.placementadda.sms.bean;

public class Course {
    private int courseId;
    private String courseName;
    private String duration;
    private double fee;

    public Course() {
    }

    public Course(int courseId, String courseName, String duration, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    @Override
    public String toString() {
        return String.format("%-8d %-25s %-15s %-10.2f",
                courseId, courseName, duration, fee);
    }
}
