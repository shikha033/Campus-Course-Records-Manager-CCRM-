
package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;

/** Simple transcript generator demonstrating toString override and polymorphism */
public class TranscriptService {
    private final DataStore ds = DataStore.getInstance();

    public String generateTranscript(String studentId) {
        Student s = ds.getStudents().get(studentId);
        if (s == null) return "Student not found";
        StringBuilder sb = new StringBuilder();
        sb.append("--- TRANSCRIPT ---\n");
        sb.append(s.profile()).append('\n');
        for (Enrollment e : s.getEnrollments()) {
            sb.append(String.format("%s | %d cr | Grade: %s\n",
                    e.getCourse().getCode(), e.getCourse().getCredits(),
                    e.getGrade() == null ? "N/A" : e.getGrade().name()));
        }
        double gpa = new EnrollmentService().computeGPA(studentId);
        sb.append(String.format("GPA: %.2f\n", gpa));
        return sb.toString();
    }
}
