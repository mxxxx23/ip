package sago.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the application.
 * Provides operations to add, remove, and get tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public TaskList find(String keyword) {
        TaskList matchedTasks = new TaskList();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) { // adjust if your internal list name differs
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchedTasks.add(task);
            }
        }
        return matchedTasks;
    }

}
