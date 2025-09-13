package prometheus.command;

import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.TaskList;
import prometheus.Ui;
import prometheus.task.Task;

/**
 * Handles the deletion of tasks from the task list.
 * This command removes a task at the specified index from the task list
 * and updates the storage accordingly.
 */
public class DeleteCommand extends Command {
    private final String arguments;

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

        ui.showMessage("Noted. I've removed this task:\n  " + removedTask
                + "\nNow you have " + tasks.size() + " tasks in the list.");
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
