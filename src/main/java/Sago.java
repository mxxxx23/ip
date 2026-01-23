import java.util.Scanner;

public class Sago {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] items = new String[100];
        int itemCount = 0;

        System.out.println("Hello! I'm Sago");
        System.out.println("What can I do for you?");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.trim().isEmpty()) {
                continue;
            }

            if (userInput.equals("list")) {
                for (int i = 0; i < itemCount; i++) {
                    System.out.println((i + 1) + ". " + items[i]);
                }
                continue;
            }

            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            items[itemCount] = userInput;
            itemCount++;

            System.out.println("add: " + userInput);
        }

        scanner.close();
    }
}
