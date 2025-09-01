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

import java.util.Scanner;
import java.util.ArrayList;

public class Prometheus {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Entry point of the Prometheus chatbot.
     * Starts the interactive command loop and processes user input
     * until the "bye" command is entered.
     *
     * @param args Command-line arguments (not used).
     */

    public static void main(String[] args) {
        try {
            // Load tasks from file on startup
            tasks = Storage.loadTasks();
            System.out.println("Loaded " + tasks.size() + " tasks from storage");
        } catch(PrometheusException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }
        System.out.println("Hello! I'm Prometheus");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input;
        while (true) {
            input = scanner.nextLine().trim();
            Command command = Command.parseCommand(input);

            if (command == Command.BYE) {
                break;
            } else {
                try {
                    processCommand(command, input);
                    // Auto-save after every successful command
                    Storage.saveTasks(tasks);
                } catch (PrometheusException e) {
                    showError(e.getMessage());
                } catch (Exception e) {
                    showError("An unexpected error occurred: " + e.getMessage());
                }
            }
        }

        // Final save before exit
        try {
            Storage.saveTasks(tasks);
            System.out.println("Tasks saved successfully.");
        } catch (PrometheusException e) {
            showError("Failed to save tasks: " + e.getMessage());
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    /**
     * Processes a user command by delegating to the appropriate handler method.
     *
     * @param command The parsed {@link Command} enum representing the user command.
     * @param input The raw user input string for extracting arguments.
     * @throws PrometheusException If the command is invalid or arguments are missing.
     */
    private static void processCommand(Command command, String input) throws PrometheusException {
        switch (command) {
            case LIST:
                printTaskList();
                break;
            case MARK:
                markTask(input);
                break;
            case UNMARK:
                unmarkTask(input);
                break;
            case TODO:
                addTodo(input);
                break;
            case DEADLINE:
                addDeadline(input);
                break;
            case EVENT:
                addEvent(input);
                break;
            case DELETE:
                deleteTask(input);
                break;
            case UNKNOWN:
                throw new PrometheusException("'" + input.split(" ")[0] + "' is not a valid command!");
            default:
                throw new PrometheusException("Unexpected command: " + command);
        }
    }

    /**
     * Prints the list of tasks currently stored.
     * If there are no tasks, prints a message indicating that the list is empty.
     */
    private static void printTaskList() {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Adds a Todo task to the task list.
     *
     * @param input User input in the format: {@code todo <description>}.
     * @throws PrometheusException If the description is missing or empty.
     */
    private static void addTodo(String input) throws PrometheusException {
        if (input.length() <= 4 || input.substring(4).trim().isEmpty()) {
            throw new PrometheusException("The description of a todo cannot be empty.");
        }
        String description = input.substring(4).trim();
        Todo todo = new Todo(description);
        tasks.add(todo);
        printAddConfirmation(todo);
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input User input in the format:
     *              {@code deadline <description> /by <time>}.
     * @throws PrometheusException If the format is invalid or required parts are missing.
     */
    private static void addDeadline(String input) throws PrometheusException {
        if (input.length() <= 8 || !input.contains("/by")) {
            throw new PrometheusException("Please use format: deadline <description> /by <time>");
        }

        String[] parts = input.substring(8).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new PrometheusException("Please use format: deadline <description> /by <time>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        printAddConfirmation(deadline);
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param input User input in the format:
     *              {@code event <description> /from <start> /to <end>}.
     * @throws PrometheusException If the format is invalid or required parts are missing.
     */
    private static void addEvent(String input) throws PrometheusException {
        if (input.length() <= 5 || !input.contains("/from") || !input.contains("/to")) {
            throw new PrometheusException("Please use format: event <description> /from <start> /to <end>");
        }

        String[] parts = input.substring(5).split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new PrometheusException("Please use format: event <description> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        Event event = new Event(description, from, to);
        tasks.add(event);
        printAddConfirmation(event);
    }

    /**
     * Prints a confirmation message after successfully adding a task.
     *
     * @param task The {@link Task} that was added.
     */
    private static void printAddConfirmation(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Marks the specified task as done.
     *
     * @param input User input in the format: {@code mark <task number>}.
     * @throws PrometheusException If the task number is invalid or not an integer.
     */
    private static void markTask(String input) throws PrometheusException {
        try {
            int index = Integer.parseInt(input.substring(5).trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks.get(index));
                System.out.println("____________________________________________________________");
            } else {
                throw new PrometheusException("Invalid task number! Please choose between 1 and " + tasks.size());
            }
        } catch (NumberFormatException e) {
            throw new PrometheusException("Please enter a valid task number after 'mark'");
        }
    }

    /**
     * Marks the specified task as not done.
     *
     * @param input User input in the format: {@code unmark <task number>}.
     * @throws PrometheusException If the task number is invalid or not an integer.
     */
    private static void unmarkTask(String input) throws PrometheusException {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks.get(index));
                System.out.println("____________________________________________________________");
            } else {
                throw new PrometheusException("Invalid task number! Please choose between 1 and " + tasks.size());
            }
        } catch (NumberFormatException e) {
            throw new PrometheusException("Please enter a valid task number after 'unmark'");
        }
    }

    /**
     * Deletes the specified task from the task list.
     *
     * @param input User input in the format: {@code delete <task number>}.
     * @throws PrometheusException If the task number is invalid or not an integer.
     */
    private static void deleteTask(String input) throws PrometheusException {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task removedTask = tasks.get(index);
                tasks.remove(index);
                System.out.println("____________________________________________________________");
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removedTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else {
                throw new PrometheusException("Invalid task number! Please choose between 1 and " + tasks.size());
            }
        } catch (NumberFormatException e) {
            throw new PrometheusException("Please enter a valid task number after 'delete'");
        }
    }

    /**
     * Prints an error message to the console with a standard formatting block.
     *
     * @param errorMessage The error message to display.
     */
    private static void showError(String errorMessage) {
        System.out.println("____________________________________________________________");
        System.out.println("Error! " + errorMessage);
        System.out.println("____________________________________________________________");
    }
}