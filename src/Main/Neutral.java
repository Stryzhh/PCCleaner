package Main;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Neutral {

    public static double gigabyte(long value) {
        return (double) Math.round((double) value / (1073741824) * 1) / 1;
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
