package sago.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import sago.task.Task;
import sago.task.Todo;
import sago.task.Deadline;
import sago.task.Event;

/**
 * Handles loading tasks from and saving tasks to the local storage.
 * Responsible for reading and writing task data in a persistent format.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        assert filePath != null && !filePath.isBlank() : "filePath should not be null/blank";
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        assert filePath != null && !filePath.isBlank() : "filePath should not be null/blank";
        File file = new File(filePath);

       // create folder if missing
       File parent = file.getParentFile();
       if (parent != null && !parent.exists()) {
           parent.mkdirs();
       }

       // if file does not exist, create it and return empty list
       if (!file.exists()) {
           file.createNewFile();
           return new ArrayList<>();
       }

       List<String> lines = Files.readAllLines(file.toPath());
       ArrayList<Task> tasks = new ArrayList<>();

       for (String line : lines) {
           if (line.trim().isEmpty()) {
               continue;
           }
           Task t = parseLineToTask(line);
           if (t != null) {
               tasks.add(t);
           }
           // else: silently skip corrupted lines
       }

       return tasks;

    }

    public void save(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "tasks should not be null";
        assert filePath != null && !filePath.isBlank() : "filePath should not be null/blank";

        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task t : tasks) {
                fw.write(taskToLine(t));
                fw.write(System.lineSeparator());
            }
        }
    }


    private String taskToLine(Task task) {
        // Format:
        // T | 0 | desc
        // D | 1 | desc | by
        // E | 0 | desc | from | to

        int done = task.isDone() ? 1 : 0;

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        }

        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy().toString();
        }

        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + e.getDescription() + " | "
                    + e.getFrom().toString() + " | " + e.getTo().toString();
        }

        // fall back
        return "? | " + done + " | " + task.getDescription();
    }

    private Task parseLineToTask(String line) {
        assert line != null : "line should not be null";

        // Split using " | " with optional spaces around |
        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task = parseTaskByType(type, parts, desc);
        if (task == null) {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    private Task parseTaskByType(String type, String[] parts, String desc) {
        if (type.equals("T")) {
            return parseTodo(desc);
        } else if (type.equals("D")) {
            return parseDeadline(parts, desc);
        } else if (type.equals("E")) {
            return parseEvent(parts, desc);
        } else {
            return null;
        }
    }

    private Task parseTodo(String desc) {
        return new Todo(desc);
    }

    private Task parseDeadline(String[] parts, String desc) {
        if (parts.length < 4) {
            return null;
        }

        LocalDate by = parseStoredDate(parts[3]);
        if (by == null) {
            return null;
        }

        return new Deadline(desc, by);
    }

    private Task parseEvent(String[] parts, String desc) {
        if (parts.length < 5) {
            return null;
        }

        LocalDate from = parseStoredDate(parts[3]);
        LocalDate to = parseStoredDate(parts[4]);
        if (from == null || to == null) {
            return null;
        }

        return new Event(desc, from, to);
    }

    private LocalDate parseStoredDate(String text) {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
