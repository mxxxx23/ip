package sago.ui;

import java.util.Scanner;
import sago.task.Task;
import sago.task.TaskList;

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

    public void showBye(){
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showDeleted(Task removed, int taskCount) {
        System.out.println("Ok. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showFindResults(TaskList matches) {
        if (matches.size() == 0) {
            System.out.println("No matching tasks found.");
            return;
        }

        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }

    /**
     * Displays the list of tasks with their corresponding indices.
     *
     * @param tasks Task list to display.
     */
    public void showList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

}
