package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.TaskList;

public class ListCommand extends Command {
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

    @Override
    public boolean isExit() {
        return false;
    }
}