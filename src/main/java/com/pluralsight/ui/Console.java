package com.pluralsight.ui;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Prompts the user for a double.
     * @param prompt to display the user
     * @return the double the user selected.
     */
    public static double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a decimal number (e.g., 10.99).");
            }
        }
    }

    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }


    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @param min the lowest number it would accept
     * @param max the highest number it would accept
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt, int min, int max) {
        while (true) {
            int result = promptForInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }
    /**
     * Prompts the user for a string.
     * reprompts if string is empty
     * @param prompt to display the user
     * @return the string the user selected.
     */
    public static String promptForString(String prompt) {
        String result;

        while (true) {
            System.out.print(prompt);
            result = scanner.nextLine().trim();

            if (!result.isEmpty()) {
                return result;
            }
            System.out.println("Error: Input cannot be empty!");
        }
    }
    /**
     * Prompts the user for a string.
     * @param prompt to display the user
     * @return the string the user selected.
     */
    public static String promptForStringEmpty(String prompt) {
            System.out.print(prompt);
            return  scanner.nextLine().trim();
    }

    /**
     * Prompts the user for a float.
     * @param prompt to display the user
     * @return the float the user selected.
     */
    public static float promptForFloat(String prompt) {
        System.out.print(prompt);
        float result = scanner.nextFloat();
        scanner.nextLine();
        return result;
    }



    /**
     * Prompts the user for a Yes or No which is returned as a boolean.
     * @param prompt to display the user
     * @return the boolean
     */
    public static boolean promptForYesNo(String prompt) {
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        return userInput.equalsIgnoreCase("YES");
        //opportunity to enhance this with some error protection.
    }
    /**
     * Makes sure the date is formatted correctly or is empty
     * @param prompt asks the user to enter the date or skip the question
     * @return parsed date
     */
    public static LocalDate promptForOptionalDate(String prompt) {
        while (true) {
            String input = Console.promptForStringEmpty(prompt);
            if (input.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format! Please use YYYY-MM-DD or press Enter to skip.");
            }
        }
    }
}
