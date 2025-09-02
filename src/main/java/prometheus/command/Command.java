package prometheus.command;
import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.TaskList;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException;
    public abstract boolean isExit();
}