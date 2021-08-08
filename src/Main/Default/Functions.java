package Main.Default;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Functions {

    public static Stage stage;
    public static String error;

    public static double gigabyte(long value) {
        return (double) Math.round((double) value / (1073741824) * 1);
    }

    public static void openWindow(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Functions.class.getClassLoader().getResource(fxml));
        Parent window = fxmlLoader.load();
        Stage newWindow = new Stage();

        newWindow.setAlwaysOnTop(true);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(window));
        newWindow.getIcons().add(new javafx.scene.image.Image("PCCleaner.png"));
        newWindow.show();
    }

    public static void dragWindow(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();

        window.setOnMousePressed(pressEvent -> window.setOnMouseDragged(dragEvent -> {
            thisWindow.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            thisWindow.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

    public static void minimize(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setIconified(true);
    }

    public static void maximise(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setMaximized(!thisWindow.isMaximized());
    }

}
