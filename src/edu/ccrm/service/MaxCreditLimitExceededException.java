
package edu.ccrm.service;

/** Checked exception when max credit limit exceeded */
public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String msg) { super(msg); }
}
