/// The Prometheus class represents a simple command-line chatbot
/// that manages a task list. Users can add, delete, mark, unmark,
/// and list tasks via text commands. The chatbot runs in an
/// interactive loop until the user types "bye".
///
/// Supported commands include:
/// - list: Displays all tasks.
/// - todo description: Adds a Todo task.
/// - deadline <description> /by <time>: Adds a Deadline task.
/// - event <description> /from <start> /to <end>: Adds an Event task.
/// - mark <task number>: Marks a task as completed.
/// - unmark <task number>: Marks a task as not completed.
/// - delete <task number>: Deletes a task.
/// - bye: Exits the chatbot.*
///
///  @Errors are reported back to the user with descriptive messages.
///  * All task operations are wrapped in exception handling to ensure
///  * predictable behavior.

public class Prometheus {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Prometheus("./data/Prometheus.txt").run();
    }
}