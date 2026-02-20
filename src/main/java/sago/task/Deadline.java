package sago.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task that must be completed by a specific date.
 */
public class Deadline extends Task {
    protected LocalDate by;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a deadline task with the given description and due date.
     *
     * @param description Description of the task.
     * @param by Due date of the deadline.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the date this deadline task is due.
     *
     * @return Due date.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns the type icon for a deadline task.
     *
     * @return "D".
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the string representation of this deadline task, including its due date.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString(){
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] "
                + this.description
                + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
