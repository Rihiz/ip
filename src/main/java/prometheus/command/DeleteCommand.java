package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Task;
import prometheus.TaskList;

/**
 * Handles the deletion of tasks from the task list.
 * This command removes a task at the specified index from the task list
 * and updates the storage accordingly.
 */
public class DeleteCommand extends Command {
    private String arguments;

    /**
     * Constructs a DeleteCommand with the specified index argument.
     *
     * @param arguments The string containing the index of the task to delete
     */
    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the delete command by removing the specified task.
     * Parses the index, removes the task from the list, saves the updated list,
     * and shows a confirmation message.
     *
     * @param tasks The task list to delete from
     * @param ui The UI handler for displaying messages
     * @param storage The storage handler for saving the updated task list
     * @throws PrometheusException If the index is invalid or task removal fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        int index = parseIndex(arguments, tasks.size());
        Task removedTask = tasks.remove(index);
        storage.save(tasks);

        ui.showMessage("Noted. I've removed this task:\n  " + removedTask +
                "\nNow you have " + tasks.size() + " tasks in the list.");
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