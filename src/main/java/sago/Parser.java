package sago;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Provides utility methods for parsing raw user input.
 * Extracts command words and arguments, and converts user-supplied strings into typed values
 * such as task indices and dates.
 *
 * <p>Parsing methods throw {@link SagoException} when the input is invalid so that the caller
 * can display a user-friendly error message.</p>
 */

public class Parser {

    /**
     * Extracts the first word of the user input as the command word.
     *
     * @param userInput Raw user input.
     * @return The command word (first token), or an empty string if the input is blank.
     */
    public static String getCommandWord(String userInput) {
        return userInput.trim().split("\\s+",2)[0];
    }

    /**
     * Extracts the remaining part of the user input after the command word.
     *
     * @param userInput Raw user input.
     * @return Argument text after the command word, trimmed. Returns an empty string if no arguments exist.
     */
    public static String getArguments(String userInput) {
        String[] parts = userInput.trim().split("\\s+",2);
        return (parts.length == 2) ? parts[1].trim() : "";
    }

    /**
     * Parses a one-based task number from the given argument text and converts it to a zero-based index.
     * Validates that the index is within the current task list size.
     *
     * @param args Argument text that should contain a task number.
     * @param size Current size of the task list.
     * @param action Action being performed (used to produce a clearer error message).
     * @return Zero-based index of the task in the list.
     * @throws SagoException If the task number is missing, not a number, or out of range.
     */
    public static int parseTaskNumber(String args, int size, String action) throws SagoException {
        if (args.isEmpty()) {
            throw new SagoException("Please specify a task number to " + action);
        }

        int index;
        try {
            index = Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new SagoException("Task number must be a number!");
        }

        if (index < 0 || index >= size) {
            throw new SagoException("Task number is out of range!");
        }

        return index;
    }

    /**
     * Parses a date string in ISO-8601 format (yyyy-MM-dd) into a {@link LocalDate}.
     *
     * @param text Date text provided by the user.
     * @return Parsed {@code LocalDate}.
     * @throws SagoException If the date format is invalid.
     */
    public static LocalDate parseDate(String text) throws SagoException {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new SagoException("Please use date format yyyy-MM-dd");
        }
    }

}
