package sago.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task that occurs within a date range.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs an event task with the given description and date range.
     *
     * @param description Description of the event.
     * @param from Start date of the event.
     * @param to End date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date of this event.
     *
     * @return Start date.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date of this event.
     *
     * @return End date.
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Returns the type icon for an event task.
     *
     * @return "E".
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Returns the string representation of this event, including its date range.
     *
     * @return Formatted event string.
     */
    @Override
    public String toString() {
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] "
                + this.description
                + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
