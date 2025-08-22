import java.util.Scanner;
import java.util.ArrayList;

public class Prometheus {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hello! I'm Prometheus");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input;
        while (true) {
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else {
                try {
                    processCommand(input);
                } catch (PrometheusException e) {
                    showError(e.getMessage());
                } catch (Exception e) {
                    showError("An unexpected error occurred: " + e.getMessage());
                }
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private static void processCommand(String input) throws PrometheusException {
        if (input.equalsIgnoreCase("list")) {
            printTaskList();
        } else if (input.startsWith("delete ")) {
            deleteTask(input);
        } else if (input.startsWith("mark ")) {
            markTask(input);
        } else if (input.startsWith("unmark ")) {
            unmarkTask(input);
        } else if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            throw new PrometheusException("'" + input + "' is not a valid command!");
        }
    }

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

    private static void addTodo(String input) throws PrometheusException {
        if (input.length() <= 4 || input.substring(4).trim().isEmpty()) {
            throw new PrometheusException("The description of a todo cannot be empty.");
        }
        String description = input.substring(4).trim();
        Todo todo = new Todo(description);
        tasks.add(todo);
        printAddConfirmation(todo);
    }

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

    private static void printAddConfirmation(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

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

    private static void showError(String errorMessage) {
        System.out.println("____________________________________________________________");
        System.out.println("Error! " + errorMessage);
        System.out.println("____________________________________________________________");
    }
}