7
package edu.ccrm.domain;

import java.util.Objects;

/**
 * Course class with Builder pattern, immutable code, equals/hashCode
 */
public class Course {
    private final String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;

    private Course(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
    }

    public static class Builder {
        private final String code;
        private String title = "";
        private int credits = 0;
        private Instructor instructor;
        private Semester semester;
        private String department;

        public Builder(String code) { this.code = code; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder credits(int c) { this.credits = c; return this; }
        public Builder instructor(Instructor i) { this.instructor = i; return this; }
        public Builder semester(Semester s) { this.semester = s; return this; }
        public Builder department(String d) { this.department = d; return this; }
        public Course build() { return new Course(this); }
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public int getCredits() { return credits; }
    public void setCredits(int c) { this.credits = c; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor i) { this.instructor = i; }
    public Semester getSemester() { return semester; }
    public void setSemester(Semester s) { this.semester = s; }
    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }

    @Override
    public String toString() {
        return String.format("Course[%s: %s (%d cr) - %s]", code, title, credits, instructor == null ? "TBA" : instructor.getFullName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return code.equals(c.code);
    }
    @Override
    public int hashCode() { return Objects.hash(code); }
}
