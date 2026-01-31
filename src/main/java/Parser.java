import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static String getCommandWord(String userInput) {
        return userInput.trim().split("\\s+",2)[0];
    }

    public static String getArguments(String userInput) {
        String[] parts = userInput.trim().split("\\s+",2);
        return (parts.length == 2) ? parts[1].trim() : "";
    }

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

    public static LocalDate parseDate(String text) throws SagoException {
        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new SagoException("Please use date format yyyy-MM-dd");
        }
    }

}
