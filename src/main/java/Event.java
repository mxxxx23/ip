import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] "
                + this.description
                + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
