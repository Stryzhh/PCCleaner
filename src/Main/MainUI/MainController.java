package Main.MainUI;

import Main.Neutral;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView quickIcon;
    @FXML
    private ImageView customIcon;
    @FXML
    private ImageView registryIcon;
    @FXML
    private ImageView driverIcon;
    @FXML
    private ImageView toolsIcon;
    @FXML
    private ImageView settingsIcon;
    @FXML
    private ImageView specificationsIcon;
    @FXML
    private ImageView minimizeIcon;
    @FXML
    private ImageView closeIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(new Image(new File("images\\PCCleaner.png").toURI().toString()));
        quickIcon.setImage(new Image(new File("images\\quick.png").toURI().toString()));
        customIcon.setImage(new Image(new File("images\\custom.png").toURI().toString()));
        registryIcon.setImage(new Image(new File("images\\registry.png").toURI().toString()));
        driverIcon.setImage(new Image(new File("images\\drivers.png").toURI().toString()));
        toolsIcon.setImage(new Image(new File("images\\tools.png").toURI().toString()));
        settingsIcon.setImage(new Image(new File("images\\settings.png").toURI().toString()));
        specificationsIcon.setImage(new Image(new File("images\\specifications.png").toURI().toString()));
        minimizeIcon.setImage(new Image(new File("images\\minimize.png").toURI().toString()));
        closeIcon.setImage(new Image(new File("images\\close.png").toURI().toString()));
    }

    public void drag() {
        Neutral.dragWindow(window);
    }

    public void minimize() {
        Neutral.minimize(window);
    }

    public void exitApplication() { System.exit(1); }

}
