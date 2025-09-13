package prometheus;

import prometheus.task.Task;

import java.util.ArrayList;

/**
 * Manages a collection of tasks in the Prometheus application.
 * This class provides operations for adding, removing, and accessing tasks
 * in the task list, as well as utility methods for checking the list's state.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The ArrayList of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The task to be added
     */
    public void add(Task task) {
        assert task != null : "Cannot add null task";
        assert tasks != null : "Task list cannot be null";
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The index of the task to remove
     * @return The removed task
     * @throws PrometheusException If the index is out of bounds
     */
    public Task remove(int index) throws PrometheusException {
        assert index >= 0 : "Index cannot be negative";
        assert tasks != null : "Task list cannot be null";
        assert !tasks.isEmpty() : "Cannot remove from empty list";

        if (index < 0 || index >= tasks.size()) {
            throw new PrometheusException("Invalid task index!");
        }
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task to retrieve
     * @return The task at the specified index
     * @throws PrometheusException If the index is out of bounds
     */
    public Task get(int index) throws PrometheusException {
        assert index >= 0 : "Index cannot be negative";
        assert tasks != null : "Task list cannot be null";
        assert !tasks.isEmpty() : "Cannot get from empty list";
        if (index < 0 || index >= tasks.size()) {
            throw new PrometheusException("Invalid task index!");
        }
        return tasks.get(index);
    }
    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a new ArrayList containing all tasks.
     * This method creates a defensive copy to prevent external modification of the task list.
     *
     * @return A new ArrayList containing all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}