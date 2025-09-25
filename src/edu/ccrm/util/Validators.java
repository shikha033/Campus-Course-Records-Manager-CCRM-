
package edu.ccrm.util;

/** Small validators util */
public final class Validators {
    private Validators() {}
    public static boolean isEmail(String s) { return s != null && s.contains("@"); }
}
