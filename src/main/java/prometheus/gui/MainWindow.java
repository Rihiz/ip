package prometheus.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import prometheus.Prometheus;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Prometheus prometheus;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/oppenheimer.png"));
    private Image prometheusImage = new Image(this.getClass().getResourceAsStream("/images/Einstein.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setPrometheus(Prometheus p) {
        prometheus = p;
        // Show welcome message
        String welcomeMsg = prometheus.getResponse("welcome");
        dialogContainer.getChildren().add(
                DialogBox.getPrometheusDialog(welcomeMsg, prometheusImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = prometheus.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPrometheusDialog(response, prometheusImage)
        );
        userInput.clear();
    }
}
