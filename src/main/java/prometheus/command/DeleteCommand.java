package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Task;
import prometheus.TaskList;

public class DeleteCommand extends Command {
    private String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        int index = parseIndex(arguments, tasks.size());
        Task removedTask = tasks.remove(index);
        storage.save(tasks);

        ui.showMessage("Noted. I've removed this task:\n  " + removedTask +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}