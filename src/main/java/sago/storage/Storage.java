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

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
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
        // Split using " | " with optional spaces around |
        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task t;

        if (type.equals("T")) {
            t = new Todo(desc);

        } else if (type.equals("D")) {
            if (parts.length < 4) {
                return null;
            }

            LocalDate by;
            try {
                by = LocalDate.parse(parts[3]);
            } catch (DateTimeParseException e) {
                return null;
            }
            t = new Deadline(desc, by);

        } else if (type.equals("E")) {
            if (parts.length < 5) {
                return null;
            }

            LocalDate from;
            LocalDate to;
            try {
                from = LocalDate.parse(parts[3]);
                to = LocalDate.parse(parts[4]);
            } catch (DateTimeParseException e) {
                return null;
            }
            t = new Event(desc, from, to);

        } else {
            return null;
        }

        if (isDone) {
            t.markAsDone();
        }

        return t;

    }

}
