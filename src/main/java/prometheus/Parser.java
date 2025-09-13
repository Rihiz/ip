package prometheus;

import prometheus.command.*;

/**
 * Parses user input commands and converts them into executable Command objects.
 * This class handles the interpretation of text commands into their corresponding
 * Command implementations. It supports various command types including task
 * management commands (add, delete, mark) and system commands (list, exit).
 */
public class Parser {
    /**
     * Parses a full command string into a Command object.
     * The command format should be: commandWord [arguments]
     * Supported commands:
     * - bye: Exit the application
     * - list: Show all tasks
     * - mark/unmark [index]: Mark or unmark a task as done
     * - todo [description]: Add a todo task
     * - deadline [description] /by [time]: Add a deadline task
     * - event [description] /from [start] /to [end]: Add an event task
     * - delete [index]: Delete a task
     *
     * @param fullCommand The complete command string to parse
     * @return A Command object corresponding to the input command
     * @throws PrometheusException If the command is empty or invalid
     */
    public static Command parse(String fullCommand) throws PrometheusException {
        assert fullCommand != null : "Command string cannot be null";
        if (fullCommand.isEmpty()) {
            throw new PrometheusException("prometheus.command.Command cannot be empty!");
        }

        String[] parts = fullCommand.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        assert !commandWord.isEmpty() : "Command word cannot be empty";
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (commandWord) {
        case "welcome" -> new WelcomeCommand();
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(arguments, true);
        case "unmark" -> new MarkCommand(arguments, false);
        case "todo" -> new AddCommand("todo", arguments);
        case "deadline" -> new AddCommand("deadline", arguments);
        case "event" -> new AddCommand("event", arguments);
        case "delete" -> new DeleteCommand(arguments);
        case "find" -> new FindCommand(arguments);
        default -> throw new PrometheusException("Unknown command: " + commandWord);
        };
    }
}