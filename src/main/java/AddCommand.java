import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCommand extends Command {
    private String commandWord;
    private String arguments;

    public AddCommand(String commandWord, String arguments) {
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        Task task = createTask();
        tasks.add(task);
        storage.save(tasks);
        ui.showMessage("Got it. I've added this task:\n  " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    private Task createTask() throws PrometheusException {
        switch (commandWord) {
            case "todo":
                return createTodo();
            case "deadline":
                return createDeadline();
            case "event":
                return createEvent();
            default:
                throw new PrometheusException("Unknown command: " + commandWord);
        }
    }

    private Todo createTodo() throws PrometheusException {
        if (arguments.trim().isEmpty()) {
            throw new PrometheusException("The description of a todo cannot be empty.");
        }
        return new Todo(arguments.trim());
    }

    private Deadline createDeadline() throws PrometheusException {
        String[] parts = arguments.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new PrometheusException("Please use format: deadline <description> /by yyyy-MM-dd HHmm");
        }

        String description = parts[0].trim();
        String byString = parts[1].trim();
        LocalDateTime by = parseDateTime(byString);

        return new Deadline(description, by);
    }

    private Event createEvent() throws PrometheusException {
        String[] parts = arguments.split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new PrometheusException("Please use format: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();
        LocalDateTime from = parseDateTime(fromString);
        LocalDateTime to = parseDateTime(toString);

        return new Event(description, from, to);
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws PrometheusException {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (Exception e) {
            throw new PrometheusException("Invalid date format. Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}