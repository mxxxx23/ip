package sago;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Provides utility methods for parsing raw user input.
 * Extracts command words and arguments, and converts user-supplied
 * strings into typed values such as task indices and dates.
 *
 * Parsing methods throw {@link SagoException} when the input is invalid
 * so that the caller can display a user-friendly error message.
 */
public class Parser {

    /**
     * Splits the given user input into at most two parts:
     * the command word and the remaining argument string.
     *
     * @param userInput Raw user input (non-null).
     * @return A String array containing 1 or 2 elements.
     */
    private static String[] splitOnce(String userInput) {
        assert userInput != null : "userInput should not be null";
        return userInput.trim().split("\\s+", 2);
    }

    /**
     * Extracts the first word of the user input as the command word.
     *
     * @param userInput Raw user input.
     * @return The command word, or an empty string if the input is blank.
     */
    public static String getCommandWord(String userInput) {
        assert userInput != null : "userInput should not be null";

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return "";
        }

        return splitOnce(userInput)[0];
    }

    /**
     * Extracts the remaining part of the user input after the command word.
     *
     * @param userInput Raw user input.
     * @return Argument text after the command word, or empty string if none exists.
     */
    public static String getArguments(String userInput) {
        assert userInput != null : "userInput should not be null";

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return "";
        }

        String[] parts = splitOnce(userInput);
        return (parts.length == 2) ? parts[1].trim() : "";
    }

    /**
     * Parses a one-based task number from the given argument text
     * and converts it to a zero-based index.
     *
     * @param args Argument text containing a task number.
     * @param size Current size of the task list.
     * @param action Action being performed (for error message clarity).
     * @return Zero-based task index.
     * @throws SagoException If the number is missing, invalid, or out of range.
     */
    public static int parseTaskNumber(String args, int size, String action)
            throws SagoException {

        assert size >= 0 : "Task list size should not be negative";
        assert action != null : "Action should not be null";

        if (args == null || args.trim().isEmpty()) {
            throw new SagoException("Please specify a task number to " + action);
        }

        int index;
        try {
            index = Integer.parseInt(args.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new SagoException("Task number must be a number!");
        }

        if (index < 0 || index >= size) {
            throw new SagoException("Task number is out of range!");
        }

        return index;
    }

    /**
     * Parses a date string in ISO-8601 format (yyyy-MM-dd).
     *
     * @param text Date text provided by the user.
     * @return Parsed LocalDate.
     * @throws SagoException If the date format is invalid.
     */
    public static LocalDate parseDate(String text) throws SagoException {
        assert text != null : "Date text should not be null";

        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new SagoException("Please use date format yyyy-MM-dd");
        }
    }
}