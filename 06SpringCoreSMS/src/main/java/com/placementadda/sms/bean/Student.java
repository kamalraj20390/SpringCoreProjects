package com.placementadda.sms.bean;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String mobile;
    private String city;

    public Student() {
    }

    public Student(int studentId, String name, String email, String mobile, String city) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return String.format("%-8d %-20s %-28s %-15s %-15s",
                studentId, name, email, mobile, city);
    }
}
