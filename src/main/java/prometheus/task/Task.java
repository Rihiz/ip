package prometheus.task;

import prometheus.PrometheusException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toFileString();

    // Factory method for creating tasks from file strings
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