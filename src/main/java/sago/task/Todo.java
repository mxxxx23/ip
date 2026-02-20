package sago.task;

/**
 * Represents a todo task that contains only a description.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for a todo task.
     *
     * @return "T".
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }
}
