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
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[X] " + tasks[index].getDescription());

                continue;
            }

            if (userInput.startsWith("unmark")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].unmark();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[ ] " + tasks[index].getDescription());

                continue;
            }


            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (userInput.startsWith("todo ")) {
                Task t = new Todo(userInput.substring(5).trim());
                tasks[taskCount++] = t;
                printAdded(t, taskCount);
                continue;

            } else if (userInput.startsWith("deadline ")){
                String[] parts = userInput.substring(9).trim().split(" /by", 2);
                if (parts.length < 2) {
                    System.out.println("Please use: deadline <desc> /by <time>");
                    continue;
                }

                Task t = new Deadline(parts[0].trim(), parts[1].trim());
                tasks[taskCount++] = t;
                printAdded(t, taskCount);
                continue;

            } else if (userInput.startsWith("event ")) {
                String rest = userInput.substring(6).trim();
                String[] p1 = rest.split(" /from ", 2);
                String[] p2 = (p1.length < 2) ? new String[0] : p1[1].split(" /to ", 2);

                if (p1.length < 2 || p2.length < 2) {
                    System.out.println("Please use: event <desc> /from <start> /to <end>");
                    continue;
                }

                Task t = new Event(p1[0].trim(), p2[0].trim(), p2[1].trim());
                tasks[taskCount++] = t;
                printAdded(t, taskCount);
                continue;
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
