package sago.ui;

import java.util.Scanner;

/**
 * Handles user interface interactions for the application.
 * Responsible for displaying messages and formatting output shown to the user.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("Hello! I'm Sago.");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a command entered by the user from standard input.
     *
     * @return Raw user input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
