package sago.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/*
 * AI-Assisted Coding (A-AiAssisted)
 *
 * Tool used: ChatGPT.
 *
 * AI assistance:
 * - Reviewed Event class for code quality improvements.
 * - Suggested making date fields immutable (final).
 * - Suggested adding validation to ensure start date is not after end date.
 * - Suggested small refactor (helper method for date formatting).
 *
 * All changes were manually reviewed and tested before committing.
 */

/**
 * Represents an event task that occurs within a date range.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs an event task with the given description and date range.
     *
     * @param description Description of the event.
     * @param from Start date of the event (inclusive).
     * @param to End date of the event (inclusive).
     * @throws NullPointerException if from or to is null
     * @throws IllegalArgumentException if from is after to
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = Objects.requireNonNull(from, "from date cannot be null");
        this.to = Objects.requireNonNull(to, "to date cannot be null");

        if (this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("from date cannot be after to date");
        }
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] "
                + description
                + " (from: " + formatDate(from)
                + " to: " + formatDate(to) + ")";
    }

    private static String formatDate(LocalDate date) {
        return date.format(OUTPUT_FORMAT);
    }
}