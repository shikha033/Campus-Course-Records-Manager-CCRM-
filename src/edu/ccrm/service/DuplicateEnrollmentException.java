
package edu.ccrm.service;

/** Checked exception when duplicate enrollment attempted */
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String msg) { super(msg); }
}
