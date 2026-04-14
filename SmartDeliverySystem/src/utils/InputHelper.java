package utils;

import java.util.Scanner;

/**
 * Utility class for safe console input reading.
 * Prevents crashes from invalid user input (e.g., letters where int is expected).
 */
public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    private InputHelper() { /* static utility class — no instances */ }

    /**
     * Read a line of text from the console.
     * Trims leading/trailing whitespace before returning.
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Read an integer from the console.
     * Keeps prompting until a valid integer is entered.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid whole number.");
            }
        }
    }

    /**
     * Read a double from the console.
     * Keeps prompting until a valid number is entered.
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number (e.g. 1.5).");
            }
        }
    }

    /** Close the underlying scanner (call on application exit). */
    public static void close() {
        scanner.close();
    }
}
