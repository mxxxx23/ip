package sago.task;

/**
 * Represents a generic task with a description and completion status.
 * Serves as the base class for specific task types such as todos, deadlines, and events.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon for this task.
     * "X" indicates done, and a blank space indicates not done.
     *
     * @return Status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark task done with "X"
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the type icon representing the task type.
     * Subclasses should override this to return their own type icons.
     *
     * @return Type icon string.
     */
    public String getTypeIcon() {
        return "?";
    }

    /**
     * Returns the string representation of this task, including its type and status.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] " + description;
    }

}
