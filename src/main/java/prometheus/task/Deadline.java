package prometheus.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import prometheus.PrometheusException;

/**
 * Represents a task with a specific deadline.
 * This class extends Task to include a completion deadline specified as a LocalDateTime.
 * It supports creation from both string and LocalDateTime deadline specifications.
 */
public class Deadline extends Task {
    /**
     * Formatter for parsing date-time input strings in the format "yyyy-MM-dd HHmm".
     */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Formatter for displaying date-time in the format "MMM dd yyyy, h:mma".
     */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * The deadline by which the task must be completed.
     */
    protected LocalDateTime by;

    /**
     * Constructs a Deadline task with description and deadline as LocalDateTime.
     *
     * @param description The description of the task
     * @param by The deadline as a LocalDateTime object
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeString The date-time string in the format "yyyy-MM-dd HHmm"
     * @return The parsed LocalDateTime object
     * @throws PrometheusException If the date-time string format is invalid
     */
    public LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new PrometheusException("Invalid date format! Please use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Gets the deadline of the task.
     *
     * @return The deadline as a LocalDateTime object
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Converts the task to a string format suitable for file storage.
     * Format: "D | isDone | description | deadline"
     *
     * @return The string representation for file storage
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | "
                + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a string representation of the task.
     * Format: "[D][✓] description (by: MMM dd yyyy, h:mma)"
     *
     * @return The string representation of the task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }
}
