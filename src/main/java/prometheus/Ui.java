package prometheus;

import java.util.Scanner;

/**
 * Handles user interface operations for the Prometheus application.
 * Manages input/output interactions and maintains the last output message.
 */
public class Ui {
    private final Scanner scanner;
    private String lastOutput;

    /**
     * Constructs a new UI handler.
     * Initializes the scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
        lastOutput = "";
    }

    /**
     * Shows the welcome message to the user.
     */
    public void showWelcome() {
        setLastOutput("Hello! I'm Prometheus\nWhat can I do for you?");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        setLastOutput("Error! " + message);
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        setLastOutput(message);
    }

    /**
     * Sets the last output message.
     *
     * @param message The message to store
     */
    private void setLastOutput(String message) {
        this.lastOutput = message;
    }

    /**
     * Gets the last output message.
     *
     * @return The last output message
     */
    public String getLastOutput() {
        return lastOutput;
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command string entered by the user
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner and releases system resources.
     */
    public void close() {
        scanner.close();
    }
}