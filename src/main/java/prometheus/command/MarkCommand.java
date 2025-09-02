package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Task;
import prometheus.TaskList;

public class MarkCommand extends Command {
    private String arguments;
    private boolean isMark;

    public MarkCommand(String arguments, boolean isMark) {
        this.arguments = arguments;
        this.isMark = isMark;
    }

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