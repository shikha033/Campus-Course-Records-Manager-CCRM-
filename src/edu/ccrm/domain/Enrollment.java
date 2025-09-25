
package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Enrollment class linking Student and Course with enrollment date and optional grade
 */
public class Enrollment {
    private final Course course;
    private final LocalDate enrolledOn;
    private Grade grade;

    public Enrollment(Course course) {
        this.course = course;
        this.enrolledOn = LocalDate.now();
    }

    public Course getCourse() { return course; }
    public LocalDate getEnrolledOn() { return enrolledOn; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }
}
