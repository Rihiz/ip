package prometheus;

import prometheus.command.Command;

/**
 * A command-line chatbot that manages a task list with various operations.
 * This class serves as the main entry point for the Prometheus task management system.
 * It handles user interactions, task operations, and persistent storage of tasks.
 *
 * @author Prometheus
 */
public class Prometheus {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Prometheus chatbot instance.
     * Initializes the UI, storage, and task list components.
     * Attempts to load existing tasks from storage, creating an empty list if loading fails.
     *
     * @param filePath The path to the file where tasks are stored
     */
    public Prometheus(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (PrometheusException e) {
            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main interaction loop of the chatbot.
     * Continuously reads user commands and executes them until an exit command is received.
     * Handles and displays any errors that occur during command execution.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (PrometheusException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the application.
     * Creates a new Prometheus instance and starts the interaction loop.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new Prometheus("./data/Prometheus.txt").run();
    }
}