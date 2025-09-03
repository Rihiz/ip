package prometheus.command;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.TaskList;

/**
 * Represents a command to exit the Prometheus application.
 * This command displays a farewell message and signals the application to terminate.
 */
public class ExitCommand extends Command {
    /**
     * Executes the exit command by showing a farewell message to the user.
     *
     * @param tasks The task list (not used in this command)
     * @param ui The UI handler used to display the farewell message
     * @param storage The storage handler (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return true as this command signals application termination
     */
    @Override
    public boolean isExit() {
        return true;
    }
}