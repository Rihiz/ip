// File: src/main/java/prometheus/command/FindCommand.java
package prometheus.command;

import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Task;
import prometheus.TaskList;

import java.util.ArrayList;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String arguments) throws PrometheusException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new PrometheusException("Please enter a keyword to search for.");
        }
        this.keyword = arguments.trim().toLowerCase();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No tasks found containing: " + keyword);
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(matchingTasks.get(i)).append("\n");
            }
            ui.showMessage(sb.toString());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}