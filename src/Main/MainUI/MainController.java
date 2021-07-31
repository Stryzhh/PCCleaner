package Main.MainUI;

import Main.Elements.FileModel;
import Main.Neutral;
import Main.Elements.Clean;
import Main.Elements.Custom;
import Main.Elements.Drivers;
import Main.Elements.Options;
import Main.Elements.Registry;
import Main.Elements.Specifications;
import Main.Elements.Tools;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import oshi.SystemInfo;

public class MainController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private AnchorPane cleanPane;
    @FXML private AnchorPane customPane;
    @FXML private AnchorPane customPanel;
    @FXML private AnchorPane registryPane;
    @FXML private AnchorPane registryPanel;
    @FXML private AnchorPane driverPane;
    @FXML private AnchorPane toolsPane;
    @FXML private AnchorPane toolsPanel;
    @FXML private AnchorPane settingsPane;
    @FXML private AnchorPane settingsPanel;
    @FXML private AnchorPane specificationsPane;
    @FXML private AnchorPane optSettingsPane;
    @FXML private AnchorPane optIncludePane;
    @FXML private AnchorPane optExcludePane;
    @FXML private AnchorPane optAdvancedPane;
    @FXML private AnchorPane optAboutPane;
    @FXML private AnchorPane toolsUninstallPanel;
    @FXML private AnchorPane toolsStartupPanel;
    @FXML private AnchorPane toolsUpdaterPanel;
    @FXML private AnchorPane toolsPluginsPanel;
    @FXML private AnchorPane toolsAnalyzerPanel;
    @FXML private AnchorPane toolsWiperPanel;

    @FXML private ImageView minimizedLogo;
    @FXML private ImageView logo;
    @FXML private ImageView minimizeIcon;
    @FXML private ImageView maximiseIcon;
    @FXML private ImageView closeIcon;
    @FXML private ImageView quickIcon;
    @FXML private ImageView quickSetup;
    @FXML private ImageView customIcon;
    @FXML private ImageView registryIcon;
    @FXML private ImageView driverIcon;
    @FXML private ImageView driverSetup;
    @FXML private ImageView toolsIcon;
    @FXML private ImageView optionsIcon;
    @FXML private ImageView specificationsIcon;

    @FXML private JFXButton quickClean;
    @FXML private JFXButton customWindows;
    @FXML private JFXButton customApplications;
    @FXML private JFXButton customAnalyze;
    @FXML private JFXButton customClean;
    @FXML private JFXButton registryReview;
    @FXML private JFXButton registryScan;
    @FXML private JFXButton driverSee;
    @FXML private JFXButton driverScan;
    @FXML private JFXButton driverUpdate;
    @FXML private JFXButton settingsSettings;
    @FXML private JFXButton settingsInclude;
    @FXML private JFXButton settingsExclude;
    @FXML private JFXButton settingsAdvanced;
    @FXML private JFXButton settingsAbout;
    @FXML private JFXButton toolsUninstall;
    @FXML private JFXButton toolsUpdater;
    @FXML private JFXButton toolsStartup;
    @FXML private JFXButton toolsPlugins;
    @FXML private JFXButton toolsWiper;
    @FXML private JFXButton toolsAnalyzer;

    @FXML private Label OS;
    @FXML private Label version;
    @FXML private Label specs;
    @FXML private Label driversScanned;
    @FXML private Label website;
    @FXML private Label github;
    @FXML private Label repo;

    @FXML private TableView<String> registryTable;
    @FXML private ListView<JFXCheckBox> customList;
    @FXML private ListView<String> specsList;

    @FXML private JFXCheckBox advancedProduceFileList;
    @FXML private JFXCheckBox advancedHideWarning;
    @FXML private JFXCheckBox advancedCloseQuick;
    @FXML private JFXCheckBox advancedShutdownQuick;
    @FXML private JFXCheckBox advancedCloseCustom;
    @FXML private JFXCheckBox advancedShutdownCustom;
    @FXML private JFXButton advancedRestore;
    @FXML private JFXCheckBox basicStartup;
    @FXML private JFXCheckBox basicRecycle;

    @FXML private JFXCheckBox analyzePictures;
    @FXML private JFXCheckBox analyzeMusic;
    @FXML private JFXCheckBox analyzeDocuments;
    @FXML private JFXCheckBox analyzeVideo;
    @FXML private JFXCheckBox analyzeEmail;
    @FXML private JFXCheckBox analyzeCompressed;
    @FXML private JFXCheckBox analyzeEverything;
    @FXML private ListView<File> analyzeDrives;
    @FXML private JFXButton btnAnalyze;

    @FXML private ListView<File> includeList;
    @FXML private JFXButton includeAddFile;
    @FXML private JFXButton includeAddFolder;
    @FXML private JFXButton includeRemove;

    @FXML private ListView<File> excludeList;
    @FXML private JFXButton excludeAddFile;
    @FXML private JFXButton excludeAddFolder;
    @FXML private JFXButton excludeRemove;

    @FXML private TableView<FileModel> fileTable;
    @FXML private TableColumn<FileModel, String> name;
    @FXML private TableColumn<FileModel, String> path;
    @FXML private TableColumn<FileModel, String> type;

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cleanPane.toFront();
        setUIElements();

        //sets operating system and basic specifications
        SystemInfo info = new SystemInfo();
        StringBuilder gpus = new StringBuilder();
        for (int i = 0; i < info.getHardware().getGraphicsCards().size(); i++) {
            gpus.append(info.getHardware().getGraphicsCards().get(i).getName()).append(", ");
        }
        version.setText("v1.0.0");
        OS.setText(String.valueOf(info.getOperatingSystem()));
        specs.setText(gpus + info.getHardware().getProcessor().getProcessorIdentifier().getName() + ", " +
                Neutral.gigabyte(info.getHardware().getMemory().getTotal()) + " (GB) RAM");

        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        path.setCellValueFactory(new PropertyValueFactory<>("Path"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));

        //loads date onto panels
        Clean.load();
        Custom.load();
        Registry.load();
        Drivers.load();
        Tools.load();
        Options.load();
        Specifications.load();

        //setting images
        quickSetup.setImage(new Image(new File("images\\setup.png").toURI().toString()));
        driverSetup.setImage(new Image(new File("images\\setup1.png").toURI().toString()));
        minimizedLogo.setImage(new Image(new File("images\\PCCleaner.png").toURI().toString()));
        logo.setImage(new Image(new File("images\\PCCleaner.png").toURI().toString()));
        quickIcon.setImage(new Image(new File("images\\quick.png").toURI().toString()));
        customIcon.setImage(new Image(new File("images\\custom.png").toURI().toString()));
        registryIcon.setImage(new Image(new File("images\\registry.png").toURI().toString()));
        driverIcon.setImage(new Image(new File("images\\drivers.png").toURI().toString()));
        toolsIcon.setImage(new Image(new File("images\\tools.png").toURI().toString()));
        optionsIcon.setImage(new Image(new File("images\\settings.png").toURI().toString()));
        specificationsIcon.setImage(new Image(new File("images\\specifications.png").toURI().toString()));
        minimizeIcon.setImage(new Image(new File("images\\minimize.png").toURI().toString()));
        maximiseIcon.setImage(new Image(new File("images\\maximise.png").toURI().toString()));
        closeIcon.setImage(new Image(new File("images\\close.png").toURI().toString()));
    }

    private void setUIElements() {
        //clean UI elements
        Clean.icon = quickSetup;
        Clean.clean = quickClean;

        //custom UI elements
        Custom.panel = customPanel;
        Custom.list = customList;
        Custom.windows = customWindows;
        Custom.applications = customApplications;
        Custom.analyze = customAnalyze;
        Custom.clean = customClean;

        //registry UI elements
        Registry.panel = registryPanel;
        Registry.table = registryTable;
        Registry.review = registryReview;
        Registry.scan = registryScan;

        //drivers UI elements
        Drivers.scanned = driversScanned;
        Drivers.icon = driverSetup;
        Drivers.scan = driverScan;
        Drivers.see = driverSee;
        Drivers.update = driverUpdate;

        //tools UI elements
        Tools.panel = toolsPanel;
        Tools.uninstall = toolsUninstall;
        Tools.updater = toolsUpdater;
        Tools.startup = toolsStartup;
        Tools.plugins = toolsPlugins;
        Tools.wiper = toolsWiper;
        Tools.analyze = toolsAnalyzer;
        Tools.analyzePictures = analyzePictures;
        Tools.analyzeMusic = analyzeMusic;
        Tools.analyzeDocuments = analyzeDocuments;
        Tools.analyzeVideo = analyzeVideo;
        Tools.analyzeCompressed = analyzeCompressed;
        Tools.analyzeEmail = analyzeEmail;
        Tools.analyzeEverything = analyzeEverything;
        Tools.analyzeDrives = analyzeDrives;
        Tools.btnAnalyze = btnAnalyze;
        Tools.fileTable = fileTable;
        Tools.name = name;
        Tools.path = path;
        Tools.type = type;

        //options UI elements
        Options.panel = settingsPanel;
        Options.settingsPane = optSettingsPane;
        Options.includePane = optIncludePane;
        Options.excludePane = optExcludePane;
        Options.advancedPane = optAdvancedPane;
        Options.aboutPane = optAboutPane;
        Options.settings = settingsSettings;
        Options.include = settingsInclude;
        Options.exclude = settingsExclude;
        Options.advanced = settingsAdvanced;
        Options.about = settingsAbout;
        Options.produceList = advancedProduceFileList;
        Options.hideWarnings = advancedHideWarning;
        Options.closeQuick = advancedCloseQuick;
        Options.shutdownQuick = advancedShutdownQuick;
        Options.closeCustom = advancedCloseCustom;
        Options.shutdownCustom = advancedShutdownCustom;
        Options.restoreAdvanced = advancedRestore;
        Options.startup = basicStartup;
        Options.recycle = basicRecycle;
        Options.includeAddFile = includeAddFile;
        Options.includeAddFolder = includeAddFolder;
        Options.includeRemove = includeRemove;
        Options.includeList = includeList;
        Options.excludeAddFile = excludeAddFile;
        Options.excludeAddFolder = excludeAddFolder;
        Options.excludeRemove = excludeRemove;
        Options.excludeList = excludeList;

        //specifications UI elements
        Specifications.list = specsList;
    }

    public void clean() {
        cleanPane.toFront();
    }

    public void custom() {
        customPane.toFront();
    }

    public void registry() {
        registryPane.toFront();
    }

    public void drivers() {
        driverPane.toFront();
    }

    public void tools() {
        toolsPane.toFront();
    }

    public void toolsUninstall() {
        toolsUninstallPanel.toFront();
    }

    public void toolsUpdater() {
        toolsUpdaterPanel.toFront();
    }

    public void toolsStartup() {
        toolsStartupPanel.toFront();
    }

    public void toolsPlugins() {
        toolsPluginsPanel.toFront();
    }

    public void toolsAnalyzer() {
        toolsAnalyzerPanel.toFront();
    }

    public void toolsWiper() {
        toolsWiperPanel.toFront();
    }

    public void options() {
        settingsPane.toFront();
    }

    public void optionsSettings() {
        optSettingsPane.toFront();
    }

    public void optionsInclude() {
        optIncludePane.toFront();
    }

    public void optionsExclude() {
        optExcludePane.toFront();
    }

    public void optionsAdvanced() {
        optAdvancedPane.toFront();
    }

    public void optionsAbout() {
        optAboutPane.toFront();
    }

    public void specifications() {
        specificationsPane.toFront();
    }

    public void openWebsite() throws URISyntaxException, IOException {
        desktop.browse(new URI("https://stryzhh.github.io/"));
    }

    public void openGithub() throws URISyntaxException, IOException {
        desktop.browse(new URI("https://github.com/Stryzhh"));
    }

    public void openRepo() throws URISyntaxException, IOException {
        desktop.browse(new URI("https://github.com/Stryzhh/PCCleaner"));
    }

    public void handIcon() {
        website.setCursor(Cursor.HAND);
        github.setCursor(Cursor.HAND);
        repo.setCursor(Cursor.HAND);
    }

    public void minimize() {
        Neutral.minimize(window);
    }

    public void maximise() {
        Neutral.maximise(window);
    }

    public void exit() {
        System.exit(0);
    }

}
