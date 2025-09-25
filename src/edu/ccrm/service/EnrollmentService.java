
package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;

import java.util.Optional;

/**
 * Enrollment service demonstrating business rules, custom exceptions, and GPA computation
 */
public class EnrollmentService {
    private final DataStore ds = DataStore.getInstance();
    private final int MAX_CREDITS = 18;

    public void enrollStudent(String studentId, String courseCode) throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        Student s = ds.getStudents().get(studentId);
        Course c = ds.getCourses().get(courseCode);
        if (s == null || c == null) throw new IllegalArgumentException("Student or Course not found");
        if (s.getEnrollments().stream().anyMatch(e -> e.getCourse().getCode().equals(courseCode)))
            throw new DuplicateEnrollmentException("Already enrolled");

        int currentCredits = s.getEnrollments().stream().mapToInt(e -> e.getCourse().getCredits()).sum();
        if (currentCredits + c.getCredits() > MAX_CREDITS) throw new MaxCreditLimitExceededException("Max credits exceeded");

        Enrollment e = new Enrollment(c);
        s.enroll(e);
    }

    public void unenrollStudent(String studentId, String courseCode) {
        Student s = ds.getStudents().get(studentId);
        if (s != null) s.unenroll(courseCode);
    }

    public void recordGrade(String studentId, String courseCode, Grade grade) {
        Student s = ds.getStudents().get(studentId);
        if (s == null) return;
        s.getEnrollments().stream()
            .filter(e -> e.getCourse().getCode().equals(courseCode))
            .findFirst().ifPresent(e -> e.setGrade(grade));
    }

    public double computeGPA(String studentId) {
        Student s = ds.getStudents().get(studentId);
        if (s == null) return 0;
        int totalCredits = 0; double totalPoints = 0;
        for (Enrollment e : s.getEnrollments()) {
            Grade g = e.getGrade();
            if (g == null) continue;
            int cr = e.getCourse().getCredits();
            totalCredits += cr; totalPoints += cr * g.getPoints();
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}
