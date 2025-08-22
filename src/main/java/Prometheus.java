// File: src/main/java/Aurora.java
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
            } else if (input.equalsIgnoreCase("list")) {
                printTaskList();
            } else if (input.startsWith("mark ")) {
                markTask(input);
            } else if (input.startsWith("unmark ")) {
                unmarkTask(input);
            } else if (input.startsWith("todo ")) {
                addTodo(input);
            } else if (input.startsWith("deadline ")) {
                addDeadline(input);
            } else if (input.startsWith("event ")) {
                addEvent(input);
            } else {
                System.out.println("Unknown command! Use: todo, deadline, event, list, mark, unmark, bye");
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private static void printTaskList() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private static void addTodo(String input) {
        String description = input.substring(5).trim();
        Todo todo = new Todo(description);
        tasks.add(todo);
        printAddConfirmation(todo);
    }

    private static void addDeadline(String input) {
        String[] parts = input.substring(9).split("/by");
        String description = parts[0].trim();
        String by = parts[1].trim();
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        printAddConfirmation(deadline);
    }

    private static void addEvent(String input) {
        String[] parts = input.substring(6).split("/from|/to");
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

    private static void markTask(String input) {
        int index = Integer.parseInt(input.substring(5).trim()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(index));
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    private static void unmarkTask(String input) {
        int index = Integer.parseInt(input.substring(7).trim()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(index));
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("Invalid task number!");
        }
    }
}