package Main.ErrorUI;

import Main.Default.Functions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ErrorController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private TextArea message;
    @FXML private ImageView closeIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message.setText(Functions.error);
        closeIcon.setImage(new Image(new java.io.File("images\\close-white.png").toURI().toString()));
    }

    public void drag() {
        Functions.dragWindow(window);
    }

    public void close() {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.close();
    }

}
