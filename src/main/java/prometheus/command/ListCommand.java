package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.TaskList;
import prometheus.Ui;

/**
 * Represents a command to display all tasks in the task list.
 * This command shows all tasks with their indices and details,
 * or displays a message if the list is empty.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command by displaying all tasks in the task list.
     * If the list is empty, shows a message indicating so.
     * Otherwise, displays each task with its index number.
     *
     * @param tasks The task list to display
     * @param ui The UI handler for displaying messages
     * @param storage The storage handler (not used in this command)
     * @throws PrometheusException If there's an error accessing the task list
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        if (tasks.isEmpty()) {
            ui.showMessage("Your task list is empty!");
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
            }
            ui.showMessage(sb.toString());
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
