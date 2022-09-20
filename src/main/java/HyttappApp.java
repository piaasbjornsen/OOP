import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * App launcher.
 */

public class HyttappApp extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Hyttapp.fxml")));
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Hyttapp");
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}