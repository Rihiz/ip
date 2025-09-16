package prometheus.task;

/**
 * Represents a Todo task in the task management system.
 * A Todo is a basic task type that only contains a description and completion status.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Converts the Todo task to a string format suitable for file storage.
     * Format: "T | [completion status] | [description]"
     * where completion status is 1 for done and 0 for not done.
     *
     * @return A string representation of the Todo task for file storage
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + priority.ordinal() + " | " + description;
    }

    /**
     * Returns a string representation of the Todo task for display purposes.
     * Format: "[T][✓/✗] [description]"
     *
     * @return A formatted string representation of the Todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
