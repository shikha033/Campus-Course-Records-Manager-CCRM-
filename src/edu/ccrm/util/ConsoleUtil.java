
package edu.ccrm.util;

import java.util.Scanner;

/** Console helper using a single Scanner */
public final class ConsoleUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }
}
