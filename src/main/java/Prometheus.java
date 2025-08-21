import java.util.Scanner;
import java.util.ArrayList;

public class Prometheus {
    public static void main(String[] args) {
        // Initialize tasks list
        ArrayList<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Prometheus");
        System.out.println("What can I do for you?");
        System.out.println("______");

        String input;
        while (true) {
            input = scanner.nextLine();
            System.out.println(input);
            System.out.println("______");

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                // Display all tasks
                if (tasks.isEmpty()) {
                    System.out.println("No tasks added yet.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
                System.out.println("______");
            } else {
                // Add task to list
                tasks.add(input);
                System.out.println("added: " + input);
                System.out.println("______");
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}