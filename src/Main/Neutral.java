package Main;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Neutral {

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

}
