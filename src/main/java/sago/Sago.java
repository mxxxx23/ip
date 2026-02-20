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

        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            try {
                if (userInput.trim().isEmpty()) {
                    continue;
                }

                String command = Parser.getCommandWord(userInput);
                String argsText = Parser.getArguments(userInput);
                
                if (command.equals("list")) {
                    ui.showList(tasks);
                    continue;
                }

                if (command.equals("delete")) {
                    int index = Parser.parseTaskNumber(argsText, tasks.size(), "delete");
                    Task removed = tasks.remove(index);

                    saveTasks(storage, tasks, ui);
                    ui.showDeleted(removed, tasks.size());
                    continue;

                }


                if (command.equals("mark")) {
                    int index = Parser.parseTaskNumber(argsText, tasks.size(), "mark");
                    tasks.get(index).markAsDone();

                    saveTasks(storage, tasks, ui);
                    ui.showMarked(tasks.get(index));
                    continue;
                }

                if (command.equals("unmark")) {
                    int index = Parser.parseTaskNumber(argsText, tasks.size(), "unmark");
                    tasks.get(index).unmark();

                    saveTasks(storage, tasks, ui);
                    ui.showUnmarked(tasks.get(index));
                    continue;
                }


                if (command.equals("bye")) {
                    ui.showBye();
                    break;
                }

                if (command.equals("todo")) {
                    if (argsText.isEmpty()) {
                        throw new SagoException("Oh no! The description of a todo cannot be empty T-T");
                    }

                    Task t = new Todo(argsText);
                    tasks.add(t);

                    saveTasks(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());
                    continue;

                }

                if (command.equals("deadline")) {
                    if (argsText.isEmpty()) {
                        throw new SagoException("Oh no! The description of a deadline cannot be empty T-T");
                    }

                    String[] dParts = argsText.split(" /by ", 2);
                    if (dParts.length < 2 || dParts[0].trim().isEmpty() || dParts[1].trim().isEmpty()) {
                        throw new SagoException("Please use: deadline <desc> /by <time>");
                    }

                    LocalDate by = Parser.parseDate(dParts[1]);

                    Task t = new Deadline(dParts[0].trim(), by);
                    tasks.add(t);

                    saveTasks(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());
                    continue;

                }

                if (command.equals("event")) {
                    if (argsText.isEmpty()) {
                        throw new SagoException("Oh no! The description of an event cannot be empty T-T");
                    }

                    String[] p1 = argsText.split(" /from ", 2);
                    if (p1.length < 2 || p1[0].trim().isEmpty()) {
                        throw new SagoException("Please use: event <desc> /from <start> /to <end>");
                    }

                    String[] p2 = p1[1].split(" /to ", 2);
                    if (p2.length < 2
                            || p2[0].trim().isEmpty()
                            || p2[1].trim().isEmpty()) {
                        throw new SagoException("Please use: event <desc> /from <start> /to <end>");
                    }

                    LocalDate from = Parser.parseDate(p2[0].trim());
                    LocalDate to = Parser.parseDate(p2[1].trim());

                    Task t = new Event(p1[0].trim(), from, to);
                    tasks.add(t);

                    saveTasks(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());
                    continue;

                }

                throw new SagoException("Oh no! I don't understand what that means T-T");

            } catch (SagoException e) {
                ui.showError(e.getMessage());
            }

        }
    }

    private static void saveTasks(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getTasks());
        } catch (Exception e) {
            ui.showError("Oops: I couldn't save your tasks...");
        }
    }
}
