package prometheus.task;

import prometheus.PrometheusException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public Event(String description, String fromString, String toString) throws PrometheusException {
        super(description);
        this.from = parseDateTime(fromString);
        this.to = parseDateTime(toString);

        if (this.from.isAfter(this.to)) {
            throw new PrometheusException("End time must be after start time!");
        }
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) throws PrometheusException {
        super(description);
        this.from = from;
        this.to = to;

        if (this.from.isAfter(this.to)) {
            throw new PrometheusException("End time must be after start time!");
        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new PrometheusException("Invalid date format! Please use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")) + " | "
                + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMATTER) + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }
}