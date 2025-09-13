package prometheus.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import prometheus.PrometheusException;

/**
 * Abstract base class for all tasks in the Prometheus application.
 * Provides common functionality for task management including description,
 * completion status, and string representation. All specific task types
 * must extend this class.
 */
public abstract class Task {
    /**
     * The description of the task.
     */
    protected String description;

    /**
     * The completion status of the task.
     */
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for the task.
     * Returns "X" if the task is done, or a space if not done.
     *
     * @return The status icon as a string
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
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
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns whether the task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the task.
     * Format: "[✓] description" or "[ ] description"
     *
     * @return The string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts the task to a string format suitable for file storage.
     * Each task type must implement its own storage format.
     *
     * @return The string representation for file storage
     */
    public abstract String toFileString();

    /**
     * Creates a task from its string representation in storage.
     * Factory method that creates the appropriate task type based on the stored format.
     *
     * @param fileString The string representation from storage
     * @return The created Task object
     * @throws PrometheusException If the string format is invalid or cannot be parsed
     */
    public static Task fromFileString(String fileString) throws PrometheusException {
        String[] parts = fileString.split(" \\| ");
        if (parts.length < 3) {
            throw new PrometheusException("Invalid task format in file: " + fileString);
        }

        String typePrefix = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task = createTaskFromPrefix(typePrefix, description, parts);

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static Task createTaskFromPrefix(String typePrefix, String description, String[] parts)
            throws PrometheusException {
        switch (typePrefix) {
        case "T":
            return new Todo(description);
        case "D":
            if (parts.length >= 4) {
                LocalDateTime by = parseDateTime(parts[3].trim());
                return new Deadline(description, by);
            } else {
                throw new PrometheusException("Invalid deadline format");
            }
        case "E":
            if (parts.length >= 5) {
                LocalDateTime from = parseDateTime(parts[3].trim());
                LocalDateTime to = parseDateTime(parts[4].trim());
                return new Event(description, from, to);
            } else {
                throw new PrometheusException("Invalid event format");
            }
        default:
            throw new PrometheusException("Unknown task type: " + typePrefix);
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            throw new PrometheusException("Invalid date format: " + dateTimeString);
        }
    }
}
