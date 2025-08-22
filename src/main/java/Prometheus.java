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
            } else {
                addTask(input);
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private static void printTaskList() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    private static void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("____________________________________________________________");
        System.out.println("Added: " + description);
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