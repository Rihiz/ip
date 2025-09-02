package prometheus;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Prometheus");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        showLine();
        System.out.println("Error! " + message);
        showLine();
    }

    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void close() {
        scanner.close();
    }
}