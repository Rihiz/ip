package prometheus.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import prometheus.Prometheus;

/**
 * A GUI for Prometheus using FXML.
 */
public class Main extends Application {

    private Prometheus prometheus = new Prometheus("data/prometheus.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            StackPane sp = fxmlLoader.load();
            Scene scene = new Scene(sp);
            stage.setScene(scene);
            stage.setTitle("Prometheus Task Manager");
            stage.setMinWidth(320);
            stage.setMinHeight(400);
            fxmlLoader.<MainWindow>getController().setPrometheus(prometheus);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
