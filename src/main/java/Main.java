import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.InputStream;

public class Main extends Application {
    InputStream stream = Main.class.getResourceAsStream("/images/Einstein.png");
    Image userImage = new Image(String.valueOf(stream));

    //private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Einstein.png"));
    //private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/oppenheimer.png"));

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        // Load Einstein image
        InputStream einsteinStream = Main.class.getResourceAsStream("/images/Einstein.png");
        if (einsteinStream == null) {
            throw new RuntimeException("Image not found: /images/Einstein.png");
        }
        Image userImage = new Image(einsteinStream);

        // Load Oppenheimer image
        InputStream oppStream = Main.class.getResourceAsStream("/images/oppenheimer.png");
        if (oppStream == null) {
            throw new RuntimeException("Image not found: /images/oppenheimer.png");
        }
        Image dukeImage = new Image(oppStream);

        // Now continue building your UI...
        ScrollPane scrollPane = new ScrollPane();
        VBox dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        TextField userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

}
