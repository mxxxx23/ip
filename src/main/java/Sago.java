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
                    System.out.println((i + 1) + ".["
                            + tasks[i].getStatusIcon()
                            + "] " + tasks[i].getDescription());
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

            tasks[taskCount] = new Task(userInput);
            taskCount++;

            System.out.println("add: " + userInput);
        }

        scanner.close();
    }
}
