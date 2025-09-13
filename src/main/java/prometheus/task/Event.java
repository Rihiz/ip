package prometheus.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import prometheus.PrometheusException;

/**
 * Represents a task with a specific start and end time.
 * This class extends Task to include event timing information specified as LocalDateTime objects.
 * It ensures that the end time is always after the start time and supports creation from
 * both string and LocalDateTime time specifications.
 */
public class Event extends Task {
    /**
     * Formatter for parsing date-time input strings in the format "yyyy-MM-dd HHmm".
     */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Formatter for displaying date-time in the format "MMM dd yyyy, h:mma".
     */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event task with description and time specifications as strings.
     *
     * @param description The description of the event
     * @param fromString The start time in the format "yyyy-MM-dd HHmm"
     * @param toString The end time in the format "yyyy-MM-dd HHmm"
     * @throws PrometheusException If the time strings cannot be parsed or if end time is before start time
     */
    public Event(String description, String fromString, String toString) throws PrometheusException {
        super(description);
        this.from = parseDateTime(fromString);
        this.to = parseDateTime(toString);

        if (this.from.isAfter(this.to)) {
            throw new PrometheusException("End time must be after start time!");
        }
    }

    /**
     * Constructs an Event task with description and time specifications as LocalDateTime objects.
     *
     * @param description The description of the event
     * @param from The start time as a LocalDateTime object
     * @param to The end time as a LocalDateTime object
     * @throws PrometheusException If the end time is before the start time
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) throws PrometheusException {
        super(description);
        this.from = from;
        this.to = to;

        if (this.from.isAfter(this.to)) {
            throw new PrometheusException("End time must be after start time!");
        }
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeString The date-time string to parse
     * @return The parsed LocalDateTime object
     * @throws PrometheusException If the date-time string is in an invalid format
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new PrometheusException("Invalid date format! Please use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Returns the start time of the event.
     *
     * @return The start time as a LocalDateTime object
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time as a LocalDateTime object
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Converts the event to a string format suitable for file storage.
     * Format: "E | isDone | description | from | to"
     * where from and to are in the format "yyyy-MM-dd HHmm"
     *
     * @return The string representation for file storage
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")) + " | "
                + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a string representation of the event.
     * Format: "[E][✓] description (from: MMM dd yyyy, h:mma to: MMM dd yyyy, h:mma)"
     *
     * @return The string representation of the event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMATTER)
                + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }
}
