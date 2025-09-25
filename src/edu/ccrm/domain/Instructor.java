
package edu.ccrm.domain;

/** Simple Instructor extending Person */
public class Instructor extends Person {
    private String department;

    public Instructor(String id, String fullName, String email, String department) {
        super(id, fullName, email);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }

    @Override
    public String profile() {
        return String.format("Instructor[id=%s, name=%s, dept=%s]", id, fullName, department);
    }
}
