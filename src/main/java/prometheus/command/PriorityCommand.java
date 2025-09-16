// File: src/main/java/prometheus/command/PriorityCommand.java
package prometheus.command;

import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.Ui;
import prometheus.task.Priority;
import prometheus.task.Task;
import prometheus.TaskList;

public class PriorityCommand extends Command {
    private int index;
    private Priority priority;

    public PriorityCommand(String arguments) throws PrometheusException {
        try {
            String[] parts = arguments.split("\\s+", 2); // Split on whitespace

            if (parts.length < 2) {
                throw new PrometheusException("Please use format: priority <task number> <high/medium/low>");
            }

            // Parse task number
            this.index = Integer.parseInt(parts[0].trim()) - 1;

            // Parse priority (case-insensitive)
            String priorityStr = parts[1].trim().toLowerCase();
            this.priority = parsePriority(priorityStr);

        } catch (NumberFormatException e) {
            throw new PrometheusException("Please enter a valid task number.");
        }
    }

    private Priority parsePriority(String priorityStr) throws PrometheusException {
        switch (priorityStr) {
        case "high": return Priority.HIGH;
        case "medium": return Priority.MEDIUM;
        case "low": return Priority.LOW;
        default:
            throw new PrometheusException("Invalid priority '" + priorityStr + "'. Use: high, medium, or low");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws PrometheusException {
        if (index < 0 || index >= tasks.size()) {
            throw new PrometheusException("Invalid task number! Please choose between 1 and " + tasks.size());
        }

        Task task = tasks.get(index);
        Priority oldPriority = task.getPriority();
        task.setPriority(priority);
        storage.save(tasks);

        String message;
        if (oldPriority == priority) {
            message = "Task " + (index + 1) + " already has " + priority + " priority:\n  " + task;
        } else {
            message = "Priority updated for task " + (index + 1) + ":\n" +
                    "  From: " + (oldPriority == Priority.MEDIUM ? "medium (default)" : oldPriority) + "\n" +
                    "  To: " + priority + "\n" +
                    "  " + task;
        }

        ui.showMessage(message);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}