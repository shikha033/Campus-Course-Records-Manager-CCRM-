
package edu.ccrm.domain;

import java.time.LocalDate;

/** Abstract Person demonstrating abstraction and protected fields */
public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected final LocalDate createdOn;

    public Person(String id, String fullName, String email) {
        assert id != null && !id.isEmpty();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdOn = LocalDate.now();
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getCreatedOn() { return createdOn; }

    public abstract String profile();
}
