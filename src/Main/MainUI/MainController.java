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
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

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
    private Label specs;
    @FXML
    private ListView<String> specsList;
    private final SystemInfo info = new SystemInfo();
    private CentralProcessor processor;
    private HardwareAbstractionLayer hardware;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processor = info.getHardware().getProcessor();
        hardware = info.getHardware();

        quickPane.toFront();

        StringBuilder gpus = new StringBuilder();
        for (int i = 0; i < info.getHardware().getGraphicsCards().size(); i++) {
            gpus.append(hardware.getGraphicsCards().get(i).getName()).append(", ");
        }
        OS.setText(String.valueOf(info.getOperatingSystem()));
        specs.setText(gpus + processor.getProcessorIdentifier().getName() + ", " +
                megabyte(hardware.getMemory().getTotal()) + " (MB) RAM");

        loadSpecifications();

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

    private void loadSpecifications() {
        Platform.runLater(() -> {
            specsList.getItems().add("------ GPU INFO ------");
            for (int i = 0; i < info.getHardware().getGraphicsCards().size(); i++) {
                specsList.getItems().add("GPU " + (i + 1) + " : " + hardware.getGraphicsCards().get(i).getName());
                specsList.getItems().add("VRAM Size: " + megabyte(hardware.getGraphicsCards().get(i).getVRam() * 2));
                specsList.getItems().add("Vendor: " + hardware.getGraphicsCards().get(i).getVendor());
                specsList.getItems().add("Version: " + hardware.getGraphicsCards().get(i).getVersionInfo());
                specsList.getItems().add("Device ID: " + hardware.getGraphicsCards().get(i).getDeviceId());
            }

            specsList.getItems().add("------ CPU INFO ------");
            specsList.getItems().add("Name: " + processor.getProcessorIdentifier().getName());
            specsList.getItems().add("Processor Family: " + processor.getProcessorIdentifier());
            specsList.getItems().add("Micro-architecture: " + processor.getProcessorIdentifier().getMicroarchitecture());
            specsList.getItems().add("Frequency (GHz): " + processor.getProcessorIdentifier().getVendorFreq() / 1000000000.0);
            specsList.getItems().add("Available processors (physical packages): " + processor.getPhysicalPackageCount());
            specsList.getItems().add("Available processors (physical packages): " + processor.getPhysicalPackageCount());
            specsList.getItems().add("Available processors (physical cores): " + processor.getLogicalProcessorCount());

            specsList.getItems().add("------ MOTHERBOARD INFO ------");
            specsList.getItems().add("Manufacturer: " + hardware.getComputerSystem().getBaseboard().getManufacturer());
            specsList.getItems().add("Model: " + hardware.getComputerSystem().getModel());
            specsList.getItems().add("Serial Number: " + hardware.getComputerSystem().getBaseboard().getSerialNumber());
            specsList.getItems().add("Hardware UUID: " + hardware.getComputerSystem().getHardwareUUID());

            specsList.getItems().add("------ RAM INFO ------");
            specsList.getItems().add("Total Size (MB): " + megabyte(hardware.getMemory().getTotal()));
            specsList.getItems().add("Available (MB): " + megabyte(hardware.getMemory().getAvailable()));
            specsList.getItems().add("Page Size (KB): " + hardware.getMemory().getPageSize());

            specsList.getItems().add("------ STORAGE INFO ------");
            File[] roots = File.listRoots();
            for (int i = 0; i < roots.length; i++) {
                specsList.getItems().add("Drive " + (i + 1) + " :");
                specsList.getItems().add("File system root: " + roots[i].getAbsolutePath());
                specsList.getItems().add("Total space (GB): " + gigabyte(roots[i].getTotalSpace()));
                specsList.getItems().add("Free space (GB): " + gigabyte(roots[i].getFreeSpace()));
                specsList.getItems().add("Usable space (GB): " + gigabyte(roots[i].getUsableSpace()));
            }

            specsList.getItems().add("------ POWER INFO ------");
            for (int i = 0; i < hardware.getPowerSources().size(); i++) {
                specsList.getItems().add("Source " + (i + 1) + " :");
                specsList.getItems().add("Name: " + hardware.getPowerSources().get(i).getName());
                specsList.getItems().add("Manufacturer: " + hardware.getPowerSources().get(i).getManufacturer());
                specsList.getItems().add("Serial number: " + hardware.getPowerSources().get(i).getSerialNumber());
                specsList.getItems().add("Max capacity: " + hardware.getPowerSources().get(i).getMaxCapacity());
                specsList.getItems().add("Temperature: " + hardware.getPowerSources().get(i).getTemperature());
            }
        });
    }

    public void specifications() {
        specificationsPane.toFront();
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
