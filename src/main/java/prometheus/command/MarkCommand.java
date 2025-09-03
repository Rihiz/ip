package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Task;
import prometheus.TaskList;

/**
 * Represents a command to mark or unmark tasks as done in the task list.
 * This command can toggle the completion status of a task at the specified index.
 * It supports both marking a task as done and marking it as not done.
 */
public class MarkCommand extends Command {
    private String arguments;
    private boolean isMark;

    /**
     * Constructs a MarkCommand with the specified index and marking action.
     *
     * @param arguments The string containing the index of the task to mark/unmark
     * @param isMark If true, marks the task as done; if false, marks it as not done
     */
    public MarkCommand(String arguments, boolean isMark) {
        this.arguments = arguments;
        this.isMark = isMark;
    }

    /**
     * Executes the mark/unmark command on the specified task.
     * Updates the task's completion status, saves the changes,
     * and displays a confirmation message.
     *
     * @param tasks The task list containing the task to mark/unmark
     * @param ui The UI handler for displaying messages
     * @param storage The storage handler for saving the updated task list
     * @throws PrometheusException If the index is invalid or task update fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        int index = parseIndex(arguments, tasks.size());
        Task task = tasks.get(index);

        if (isMark) {
            task.markAsDone();
            ui.showMessage("Nice! I've marked this task as done:\n  " + task);
        } else {
            task.markAsNotDone();
            ui.showMessage("OK, I've marked this task as not done yet:\n  " + task);
        }

        storage.save(tasks);
    }

    /**
     * Parses and validates the task index from the command argument.
     * Converts the 1-based user input index to a 0-based array index.
     *
     * @param argument The string argument containing the task index
     * @param maxIndex The maximum valid index (size of the task list)
     * @return The parsed and validated 0-based index
     * @throws PrometheusException If the index is not a number or out of valid range
     */
    private int parseIndex(String argument, int maxIndex) throws PrometheusException {
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