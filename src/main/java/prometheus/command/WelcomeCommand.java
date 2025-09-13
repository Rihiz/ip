package prometheus.command;
import prometheus.Storage;
import prometheus.TaskList;
import prometheus.Ui;

/**
 * Represents a command to welcome the Prometheus application.
 */
public class WelcomeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showWelcome();
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return true as this command signals application termination
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
