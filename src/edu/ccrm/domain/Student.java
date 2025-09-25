
package edu.ccrm.domain;

import java.util.*;
import java.util.stream.Collectors;

/** Student class demonstrates encapsulation, collections & polymorphism */
public class Student extends Person {
    public enum Status { ACTIVE, INACTIVE }

    private String regNo;
    private Status status = Status.ACTIVE;
    private final Map<String, Enrollment> enrollments = new LinkedHashMap<>();

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
    }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public Status getStatus() { return status; }
    public void setStatus(Status s) { this.status = s; }

    public Collection<Enrollment> getEnrollments() { return enrollments.values(); }
    public void enroll(Enrollment e) { enrollments.put(e.getCourse().getCode(), e); }
    public void unenroll(String courseCode) { enrollments.remove(courseCode); }

    @Override
    public String profile() {
        return String.format("Student[id=%s, regNo=%s, name=%s, email=%s, status=%s]",
                id, regNo, fullName, email, status);
    }

    @Override
    public String toString() {
        String cs = enrollments.values().stream().map(en -> en.getCourse().getCode()).collect(Collectors.joining(", "));
        return profile() + "\nEnrolled: [" + cs + "]";
    }
}
