package prometheus.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import prometheus.PrometheusException;

import prometheus.Storage;
import prometheus.TaskList;
import prometheus.Ui;
import prometheus.task.Deadline;
import prometheus.task.Event;
import prometheus.task.Task;
import prometheus.task.Todo;

/**
 * Handles the creation and addition of new tasks to the task list.
 * This class supports creation of three types of tasks:
 * - Todo: Simple tasks without time constraints
 * - Deadline: Tasks with a completion deadline
 * - Event: Tasks with start and end times
 */
public class AddCommand extends Command {
    private final String commandWord;
    private final String arguments;

    /**
     * Constructs an AddCommand with the specified command type and arguments.
     *
     * @param commandWord The type of task to create ("todo", "deadline", or "event")
     * @param arguments The description and time-related arguments for the task
     */
    public AddCommand(String commandWord, String arguments) {
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    /**
     * Executes the add command by creating and storing a new task.
     * Creates the appropriate task type, adds it to the task list,
     * saves the updated list, and shows a confirmation message.
     *
     * @param tasks The task list to add the new task to
     * @param ui The UI handler for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws PrometheusException If task creation or storage fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        Task task = createTask();
        tasks.add(task);
        storage.save(tasks);
        ui.showMessage("Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Creates a task based on the command type.
     *
     * @return The created task object
     * @throws PrometheusException If the task creation fails due to invalid input
     */
    Task createTask() throws PrometheusException {
        return switch (commandWord) {
        case "todo" -> createTodo();
        case "deadline" -> createDeadline();
        case "event" -> createEvent();
        default -> throw new PrometheusException("Unknown command: " + commandWord);
        };
    }

    /**
     * Creates a Todo task from the provided arguments.
     *
     * @return A new Todo task
     * @throws PrometheusException If the task description is empty
     */
    private Todo createTodo() throws PrometheusException {
        if (arguments.trim().isEmpty()) {
            throw new PrometheusException("The description of a todo cannot be empty.");
        }
        return new Todo(arguments.trim());
    }

    /**
     * Creates a Deadline task from the provided arguments.
     * Expected format: description /by yyyy-MM-dd HHmm
     *
     * @return A new Deadline task
     * @throws PrometheusException If the format is invalid or parsing fails
     */
    private Deadline createDeadline() throws PrometheusException {
        String[] parts = arguments.split("/by", 2);
        boolean isValid = parts.length >= 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty();
        if (isValid) {
            throw new PrometheusException("Please use format: deadline <description> /by yyyy-MM-dd HHmm");
        }

        String description = parts[0].trim();
        String byString = parts[1].trim();
        LocalDateTime by = parseDateTime(byString);

        return new Deadline(description, by);
    }

    /**
     * Creates an Event task from the provided arguments.
     * Expected format: description /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm
     *
     * @return A new Event task
     * @throws PrometheusException If the format is invalid or parsing fails
     */
    private Event createEvent() throws PrometheusException {
        String[] parts = arguments.split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new PrometheusException(
                    "Please use format: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();
        LocalDateTime from = parseDateTime(fromString);
        LocalDateTime to = parseDateTime(toString);

        return new Event(description, from, to);
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     * Expected format: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)
     *
     * @param dateTimeString The date-time string to parse
     * @return The parsed LocalDateTime
     * @throws PrometheusException If the date-time format is invalid
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (Exception e) {
            throw new PrometheusException("Invalid date format. Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return false as this command does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
