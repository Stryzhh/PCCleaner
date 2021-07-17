package Main.MainUI;

import Main.Neutral;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import oshi.SystemInfo;

public class MainController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private AnchorPane quickPane;
    @FXML
    private AnchorPane customPane;
    @FXML
    private AnchorPane registryPane;
    @FXML
    private AnchorPane driverPane;
    @FXML
    private AnchorPane toolsPane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private AnchorPane specificationsPane;
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
    @FXML
    private Label OS;
    @FXML
    private ListView<String> specsList;
    private SystemInfo info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quickPane.toFront();
        info = new SystemInfo();
        System.out.println(info.getHardware().getGraphicsCards());
        OS.setText(System.getProperty("os.name"));

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

    public void specifications() {
        specificationsPane.toFront();

        Platform.runLater(() -> {
            long maxMemory = Runtime.getRuntime().maxMemory();
            specsList.getItems().add(String.valueOf(info.getHardware().getGraphicsCards()));
            specsList.getItems().add("Available processors (threads): " + Runtime.getRuntime().availableProcessors());
            specsList.getItems().add("Free memory (MB): " + megabyte(Runtime.getRuntime().freeMemory()));
            specsList.getItems().add("Maximum memory (MB): " + (megabyte(maxMemory) == Long.MAX_VALUE ? "no limit" : megabyte(maxMemory)));
            specsList.getItems().add("Total memory available to JVM (MB): " + megabyte(Runtime.getRuntime().totalMemory()));

            File[] roots = File.listRoots();
            for (File root : roots) {
                specsList.getItems().add("File system root: " + root.getAbsolutePath());
                specsList.getItems().add("Total space (GB): " + gigabyte(root.getTotalSpace()));
                specsList.getItems().add("Free space (GB): " + gigabyte(root.getFreeSpace()));
                specsList.getItems().add("Usable space (GB): " + gigabyte(root.getUsableSpace()));
            }
        });
    }

    public void drag() {
        Neutral.dragWindow(window);
    }

    public void minimize() {
        Neutral.minimize(window);
    }

    public long megabyte(long value) {
        return value / (1024 * 1024);
    }

    public long gigabyte(long value) {
        return value / (1024 * 1024 * 1024);
    }

    public void exitApplication() { System.exit(1); }

}
