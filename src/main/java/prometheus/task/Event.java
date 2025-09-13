package prometheus.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import prometheus.PrometheusException;

/**
 * Represents a task with a specific start and end time.
 * This class extends Task to include event timing information specified as LocalDateTime objects.
 * It ensures that the end time is always after the start time and supports creation from
 * both string and LocalDateTime time specifications.
 */
public class Event extends Task {

    /**
     * Formatter for displaying date-time in the format "MMM dd yyyy, h:mma".
     */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    protected LocalDateTime from;
    protected LocalDateTime to;

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
        return "E | " + (isDone ? "1" : "0") + " | " + priority.ordinal() + " | " + description + " | " +
                from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")) + " | " +
                to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a string representation of the event.
     * Format: "[E][✓] description (from: MMM dd yyyy, h:mma to: MMM dd yyyy, h:mma)"
     *
     * @return The string representation of the event
     */
    @Override
    public String toString() {
        String baseString = super.toString(); // Gets [X] description #high
        return baseString + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) +
                " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }
}
