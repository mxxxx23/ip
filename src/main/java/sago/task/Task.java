package sago.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark task done with "X"
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getTypeIcon() {
        return "?";
    }

    @Override
    public String toString() {
        return "[" + this.getTypeIcon() + "]["
                + this.getStatusIcon() + "] " + description;
    }

}
