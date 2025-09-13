package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.TaskList;
import prometheus.Ui;

/**
 * Represents a command that can be executed by the Prometheus chatbot.
 * Each command encapsulates a specific action to be performed on the task list,
 * such as adding, deleting, or listing tasks.
 * Commands are executed with access to the task list, user interface, and storage.
 * This abstract class defines the structure for all concrete command implementations.
 *
 * @author Prometheus
 * @see prometheus.command.AddCommand
 * @see prometheus.command.DeleteCommand
 * @see prometheus.command.ListCommand
 * @see prometheus.command.ExitCommand
 * @see prometheus.command.FindCommand
 * @see prometheus.command.MarkCommand
 */
public abstract class Command {
    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on
     * @param ui The UI handler for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws PrometheusException If the command execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException;

    /**
     * Indicates whether this command should cause the application to exit.
     *
     * @return true if the application should exit after this command, false otherwise
     */
    public abstract boolean isExit();

    /**
     * Parses and validates the task index from a command argument.
     * Converts the 1-based user input index to a 0-based array index.
     *
     * @param argument The string argument containing the task index
     * @param maxIndex The maximum valid index (size of the task list)
     * @return The parsed and validated 0-based index
     * @throws PrometheusException If the index is not a number or out of valid range
     */
    protected int parseIndex(String argument, int maxIndex) throws PrometheusException {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= maxIndex) {
                throw new PrometheusException("Invalid task number! Please choose between 1 and " + maxIndex);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new PrometheusException("Please enter a valid task number.");
        }
    }
}
