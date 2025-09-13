package prometheus.command;

import prometheus.PrometheusException;
import prometheus.Storage;
import prometheus.task.Task;
import prometheus.TaskList;
import prometheus.Ui;
import java.util.ArrayList;

/**
 * Represents a command to search for tasks containing a specific keyword.
 * This command searches through all tasks in the task list and displays those
 * that contain the specified keyword in their description.
 */
public class FindCommand extends Command {
    /** The keyword to search for in task descriptions. */
    private final String keyword;

    /**
     * Constructs a new FindCommand with the given search keyword.
     *
     * @param arguments The search keyword provided by the user
     * @throws PrometheusException If the search keyword is null or empty
     */
    public FindCommand(String arguments) throws PrometheusException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new PrometheusException("Please enter a keyword to search for.");
        }
        this.keyword = arguments.trim().toLowerCase();
    }

    /**
     * Executes the find command. Searches through all tasks in the task list
     * for those containing the specified keyword and displays the matching tasks.
     *
     * @param tasks The list of tasks to search through
     * @param ui The user interface to display results
     * @param storage The storage object (unused in this command)
     * @throws PrometheusException If there is an error during execution
     */
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
