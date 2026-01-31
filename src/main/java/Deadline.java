import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    protected LocalDate by;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString(){
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] "
                + this.description
                + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
