package prometheus;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private String lastOutput;

    public Ui() {
        scanner = new Scanner(System.in);
        lastOutput = "";
    }

    public void showWelcome() {
        setLastOutput("Hello! I'm Prometheus\nWhat can I do for you?");
    }

    public void showError(String message) {
        setLastOutput("Error! " + message);
    }

    public void showMessage(String message) {
        setLastOutput(message);
    }

    private void setLastOutput(String message) {
        this.lastOutput = message;
    }

    public String getLastOutput() {
        return lastOutput;
    }

    public void close() {
        scanner.close();
    }
}