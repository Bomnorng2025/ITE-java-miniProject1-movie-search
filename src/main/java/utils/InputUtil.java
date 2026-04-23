package utils;

import java.util.Scanner;

public class InputUtil {
    static Scanner scanner = new Scanner(System.in);

    public static String getString(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    public static Integer getInteger(String label) {
        do {
            try {
                System.out.print(label);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("-> Error: " + e.getMessage());
            }
        } while (true);
    }

    public static void pressEnterToContinue() {
        System.out.println("\n > [-] Press Enter to continue...");
        scanner.nextLine();
    }
}
