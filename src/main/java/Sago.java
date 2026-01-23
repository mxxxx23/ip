import java.util.Scanner;

public class Sago {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Sago");
        System.out.println("What can I do for you?");

        while (true) {
            String userInput = scanner.nextLine();

            try {
                if (userInput.trim().isEmpty()) {
                    continue;
                }

                if (userInput.equals("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    continue;
                }

                if (userInput.startsWith("mark")) {
                    String[] parts = userInput.trim().split("\\s+", 2);
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new SagoException("Please specify a task number to mark");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(parts[1].trim()) - 1;
                    } catch (NumberFormatException e) {
                        throw new SagoException("Task number must be a number!");
                    }

                    if (index < 0 || index >= taskCount) {
                        throw new SagoException("Task number is out of range!");
                    }

                    tasks[index].markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("[X] " + tasks[index].getDescription());

                    continue;
                }

                if (userInput.startsWith("unmark")) {
                    String[] parts = userInput.trim().split("\\s+", 2);
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new SagoException("Please specify a task number to unmark");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(parts[1].trim()) - 1;
                    } catch (NumberFormatException e) {
                        throw new SagoException("Task number must be a number!");
                    }

                    if (index < 0 || index >= taskCount) {
                        throw new SagoException("Task number is out of range!");
                    }


                    tasks[index].unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("[ ] " + tasks[index].getDescription());

                    continue;
                }


                if (userInput.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (userInput.startsWith("todo")) {
                    String desc = userInput.length() > 4
                            ? userInput.substring(4).trim()
                            : "";

                    if (desc.isEmpty()) {
                        throw new SagoException("Oh no! The description of a todo cannot be empty T-T");
                    }

                    Task t = new Todo(userInput.substring(5).trim());
                    tasks[taskCount++] = t;
                    printAdded(t, taskCount);
                    continue;

                } else if (userInput.startsWith("deadline")) {
                    String rest = userInput.length() > 8
                            ? userInput.substring(8).trim()
                            : "";

                    if (rest.isEmpty()) {
                        throw new SagoException("Oh no! The description of a deadline cannot be empty T-T");
                    }

                    String[] parts = rest.split(" /by ", 2);
                    //String part0 = parts[0].trim();
                    //String part1 = parts[1].trim();
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new SagoException("Please use: deadline <desc> /by <time>");
                    }

                    Task t = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks[taskCount++] = t;
                    printAdded(t, taskCount);
                    continue;

                } else if (userInput.startsWith("event")) {
                    String rest = userInput.length() > 5
                            ? userInput.substring(5).trim()
                            : "";

                    if (rest.isEmpty()) {
                        throw new SagoException("Oh no! The description of an event cannot be empty T-T");
                    }

                    String[] p1 = rest.split(" /from ", 2);
                    if (p1.length < 2 || p1[0].trim().isEmpty()) {
                        throw new SagoException("Please use: event <desc> /from <start> /to <end>");
                    }

                    String[] p2 = p1[1].split(" /to ", 2);
                    if (p2.length < 2
                            || p2[0].trim().isEmpty()
                            || p2[1].trim().isEmpty()) {
                        throw new SagoException("Please use: event <desc> /from <start> /to <end>");
                    }

                    Task t = new Event(p1[0].trim(), p2[0].trim(), p2[1].trim());
                    tasks[taskCount++] = t;
                    printAdded(t, taskCount);
                    continue;
                } else {
                    throw new SagoException("Oh no! I don't understand what that means T-T");
                }
            } catch (SagoException e) {
                System.out.println(e.getMessage());
            }

        }

        scanner.close();
    }

    private static void printAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}
