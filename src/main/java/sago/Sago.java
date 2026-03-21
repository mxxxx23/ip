package sago;

import java.time.LocalDate;
import sago.ui.Ui;
import sago.storage.Storage;
import sago.task.TaskList;
import sago.task.Task;
import sago.task.Todo;
import sago.task.Deadline;
import sago.task.Event;

/**
 * Entry point of the Sago task manager application.
 * Coordinates the main program flow by reading user input, delegating parsing to {@link Parser},
 * applying changes to the {@link sago.task.TaskList}, and persisting updates via {@link sago.storage.Storage}.
 *
 * <p>All user-facing messages are handled through {@link sago.ui.Ui}.</p>
 */
public class Sago {

    private final Storage storage;
    private TaskList tasks;
    private boolean isExit;

    public Sago() {
        this.storage = new Storage("data/sago.txt");
        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
            this.tasks = new TaskList();
        }
        this.isExit = false;
    }

    private Sago(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
        this.isExit = false;
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/sago.txt");

        TaskList tasks;

        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError("Oops: I couldn't load your saved tasks. Starting fresh~~");
            tasks = new TaskList();
        }

        Sago sago = new Sago(storage, tasks);
        ui.showWelcome();

        while (!sago.isExit()) {
            String userInput = ui.readCommand();

            try {
                if (userInput.trim().isEmpty()) {
                    continue;
                }
                String response = sago.executeCommand(userInput);
                ui.showMessage(response);
            } catch (SagoException e) {
                ui.showError(e.getMessage());
            }

        }
    }

    public String getResponse(String userInput) {
        try {
            return executeCommand(userInput);
        } catch (SagoException e) {
            return "Error: " + e.getMessage();
        }
    }

    public boolean isExit() {
        return isExit;
    }

    private String executeCommand(String userInput) throws SagoException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new SagoException("Please type something ~~");
        }

        String command = Parser.getCommandWord(userInput);
        String argsText = Parser.getArguments(userInput);

        switch (command) {
        case "list":
            return handleList();

        case "delete":
            return handleDelete(argsText);

        case "mark":
            return handleMark(argsText);

        case "unmark":
            return handleUnmark(argsText);

        case "todo":
            return handleTodo(argsText);

        case "deadline":
            return handleDeadline(argsText);

        case "event":
            return handleEvent(argsText);

        case "find":
            return handleFind(argsText);

        case "help":
            return handleHelp();

        case "bye":
            return handleBye();

        default:
            throw new SagoException("Oh no! I don't understand what that means T-T");
        }
    }

    private String handleList() {
        return formatList();
    }

    private String handleDelete(String argsText) throws SagoException {
        int index = Parser.parseTaskNumber(argsText, tasks.size(), "delete");
        Task removed = tasks.remove(index);
        saveTasks(storage, tasks);
        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleMark(String argsText) throws SagoException {
        int index = Parser.parseTaskNumber(argsText, tasks.size(), "mark");
        tasks.get(index).markAsDone();
        saveTasks(storage, tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    private String handleUnmark(String argsText) throws SagoException {
        int index = Parser.parseTaskNumber(argsText, tasks.size(), "unmark");
        tasks.get(index).unmark();
        saveTasks(storage, tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    private String handleTodo(String argsText) throws SagoException {
        if (argsText.isEmpty()) {
            throw new SagoException("Oh no! The description of a todo cannot be empty T-T");
        }
        Task task = new Todo(argsText);
        tasks.add(task);
        saveTasks(storage, tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleDeadline(String argsText) throws SagoException {
        if (argsText.isEmpty()) {
            throw new SagoException("Oh no! The description of a deadline cannot be empty T-T");
        }
        String[] dParts = argsText.split(" /by ", 2);
        if (dParts.length < 2 || dParts[0].trim().isEmpty() || dParts[1].trim().isEmpty()) {
            throw new SagoException("Please use: deadline <desc> /by <time>");
        }
        LocalDate by = Parser.parseDate(dParts[1]);
        Task task = new Deadline(dParts[0].trim(), by);
        tasks.add(task);
        saveTasks(storage, tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleEvent(String argsText) throws SagoException {
        if (argsText.isEmpty()) {
            throw new SagoException("Oh no! The description of an event cannot be empty T-T");
        }
        String[] p1 = argsText.split(" /from ", 2);
        if (p1.length < 2 || p1[0].trim().isEmpty()) {
            throw new SagoException("Please use: event <desc> /from <start> /to <end>");
        }
        String[] p2 = p1[1].split(" /to ", 2);
        if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
            throw new SagoException("Please use: event <desc> /from <start> /to <end>");
        }
        LocalDate from = Parser.parseDate(p2[0].trim());
        LocalDate to = Parser.parseDate(p2[1].trim());
        Task task;
        try {
            task = new Event(p1[0].trim(), from, to);
        } catch (IllegalArgumentException e) {
            throw new SagoException("Event start date cannot be after end date.");
        }
        tasks.add(task);
        saveTasks(storage, tasks);
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleFind(String argsText) throws SagoException {
        String keyword = argsText.trim();
        if (keyword.isEmpty()) {
            throw new SagoException("Please provide a keyword to find.");
        }
        TaskList matches = tasks.find(keyword);
        return formatFind(matches, keyword);
    }

    private String handleHelp() {
        return getHelpMessage();
    }

    private String handleBye() {
        isExit = true;
        return "Bye. Hope to see you again soon!";
    }

    private String formatList() {
        if (tasks.size() == 0) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a user-facing help message listing available commands and formats.
     *
     * @return Help message string.
     */
    private static String getHelpMessage() {
        return "Here are the available commands:\n"
                + "list\n"
                + "todo <description>\n"
                + "deadline <description> /by <yyyy-MM-dd>\n"
                + "event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>\n"
                + "mark <taskNumber>\n"
                + "unmark <taskNumber>\n"
                + "delete <taskNumber>\n"
                + "find <keyword>\n"
                + "help\n"
                + "bye";
    }

    private String formatFind(TaskList matches, String keyword) {
        if (matches.size() == 0) {
            return "No matching tasks found for: " + keyword;
        }
        StringBuilder sb = new StringBuilder("Matching tasks:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    private static void saveTasks(Storage storage, TaskList tasks) {
        try {
            storage.save(tasks.getTasks());
        } catch (Exception e) {
            // GUI version: just swallow; response already returned
        }
    }


}
