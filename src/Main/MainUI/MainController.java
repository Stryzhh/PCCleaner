package Main.MainUI;

import Main.Applications.Application;
import Main.Applications.Extension;
import Main.Applications.Software;
import Main.Elements.FileModel;
import Main.Neutral;
import Main.Elements.Clean;
import Main.Elements.Custom;
import Main.Elements.Drivers;
import Main.Elements.Options;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import oshi.SystemInfo;

public class MainController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private AnchorPane cleanPane;
    @FXML private AnchorPane customPane;
    @FXML private AnchorPane windowsPanel;
    @FXML private AnchorPane applicationPanel;
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
    @FXML private AnchorPane toolsCleanUpPanel;
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
    @FXML private ImageView exitIcon;
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
    @FXML private JFXButton driveOpen;
    @FXML private JFXButton settingsSettings;
    @FXML private JFXButton settingsInclude;
    @FXML private JFXButton settingsExclude;
    @FXML private JFXButton settingsAdvanced;
    @FXML private JFXButton settingsAbout;
    @FXML private JFXButton toolsUninstall;
    @FXML private JFXButton toolsCleanUp;
    @FXML private JFXButton toolsStartup;
    @FXML private JFXButton toolsPlugins;
    @FXML private JFXButton toolsWiper;
    @FXML private JFXButton toolsAnalyzer;

    @FXML private Label OS;
    @FXML private Label version;
    @FXML private Label specs;
    @FXML private Label website;
    @FXML private Label github;
    @FXML private Label repo;
    @FXML private Label analyzeStatus;

    @FXML private ListView<String> customList;
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
    @FXML private JFXButton btnCancel;

    @FXML private ListView<File> includeList;
    @FXML private JFXButton includeAddFile;
    @FXML private JFXButton includeAddFolder;
    @FXML private JFXButton includeRemove;

    @FXML private ListView<File> excludeList;
    @FXML private JFXButton excludeAddFile;
    @FXML private JFXButton excludeAddFolder;
    @FXML private JFXButton excludeRemove;

    @FXML private TableView<FileModel> fileTable;
    @FXML private TableColumn<FileModel, String> analyzeName;
    @FXML private TableColumn<FileModel, String> analyzePath;
    @FXML private TableColumn<FileModel, String> analyzeType;

    @FXML private TextField uninstallSearch;
    @FXML private TableView<Software> uninstallTable;
    @FXML private TableColumn<Software, String> uninstallName;
    @FXML private TableColumn<Software, String> uninstallPublisher;
    @FXML private TableColumn<Software, String> uninstallLocation;
    @FXML private TableColumn<Software, LocalDateTime> uninstallDate;
    @FXML private TableColumn<Software, String> uninstallVersion;
    @FXML private JFXButton btnUninstall;
    @FXML private JFXButton uninstallSave;

    @FXML private ListView<String> wiperDeleted;
    @FXML private ListView<File> wiperDrives;
    @FXML private JFXButton wipeDrive;
    @FXML private JFXButton cancelWipe;
    @FXML private ComboBox<Integer> comboWipes;
    @FXML private Label wiperStatus;

    @FXML private ListView<File> cleanupDrives;
    @FXML private JFXButton cleanupClean;

    @FXML private TableView<Application> startupTable;
    @FXML private TableColumn<Application, String> startupKey;
    @FXML private TableColumn<Application, String> startupPath;
    @FXML private JFXButton startupAdd;
    @FXML private JFXButton startupSave;

    @FXML private TableView<Extension> pluginTable;
    @FXML private TableColumn<Extension, String> pluginProgram;
    @FXML private TableColumn<Extension, String> pluginFile;
    @FXML private TableColumn<Extension, String> pluginVersion;
    @FXML private JFXButton pluginGoogleChrome;
    @FXML private JFXButton pluginInternetExplorer;
    @FXML private JFXButton pluginSave;
    @FXML private Label pluginStatus;

    @FXML private AnchorPane explorerPanel;
    @FXML private ImageView explorerIcon;
    @FXML private JFXButton selectExplorer;
    @FXML private JFXCheckBox explorerTemp;
    @FXML private JFXCheckBox explorerHistory;
    @FXML private JFXCheckBox explorerCookies;

    @FXML private AnchorPane edgePanel;
    @FXML private ImageView edgeIcon;
    @FXML private JFXButton selectEdge;
    @FXML private JFXCheckBox edgeCache;
    @FXML private JFXCheckBox edgeHistory;
    @FXML private JFXCheckBox edgeCookies;
    @FXML private JFXCheckBox edgeSession;

    @FXML private AnchorPane systemPanel;
    @FXML private ImageView systemIcon;
    @FXML private JFXButton selectSystem;
    @FXML private JFXCheckBox systemBin;
    @FXML private JFXCheckBox systemTemp;
    @FXML private JFXCheckBox systemClipboard;
    @FXML private JFXCheckBox systemDump;
    @FXML private JFXCheckBox systemLog;
    @FXML private JFXCheckBox systemReport;
    @FXML private JFXCheckBox systemStartMenu;
    @FXML private JFXCheckBox systemDesktop;

    @FXML private AnchorPane chromePanel;
    @FXML private ImageView chromeIcon;
    @FXML private JFXButton selectChrome;
    @FXML private JFXCheckBox chromeCache;
    @FXML private JFXCheckBox chromeHistory;
    @FXML private JFXCheckBox chromeCookies;
    @FXML private JFXCheckBox chromeSession;

    @FXML private AnchorPane spotifyPanel;
    @FXML private ImageView spotifyIcon;
    @FXML private JFXButton selectSpotify;
    @FXML private JFXCheckBox spotifyCache;
    @FXML private JFXCheckBox spotifyMusic;
    @FXML private JFXCheckBox spotifyOffline;

    @FXML private AnchorPane steamPanel;
    @FXML private ImageView steamIcon;
    @FXML private JFXButton selectSteam;
    @FXML private JFXCheckBox steamDump;

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cleanPane.toFront();
        setUIElements();
        comboWipes.getItems().add(1);
        comboWipes.getItems().add(2);
        comboWipes.getItems().add(3);

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

        analyzeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        analyzePath.setCellValueFactory(new PropertyValueFactory<>("Path"));
        analyzeType.setCellValueFactory(new PropertyValueFactory<>("Type"));

        uninstallName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        uninstallPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        uninstallLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        uninstallDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        uninstallVersion.setCellValueFactory(new PropertyValueFactory<>("Version"));

        startupKey.setCellValueFactory(new PropertyValueFactory<>("Name"));
        startupPath.setCellValueFactory(new PropertyValueFactory<>("Path"));

        pluginProgram.setCellValueFactory(new PropertyValueFactory<>("Program"));
        pluginFile.setCellValueFactory(new PropertyValueFactory<>("File"));
        pluginVersion.setCellValueFactory(new PropertyValueFactory<>("Version"));

        //loads date onto panels
        Tools.load();
        Clean.load();
        Custom.load();
        Drivers.load();
        Options.load();
        Specifications.load();

        //setting images
        quickSetup.setImage(new Image(new File("images\\setup.png").toURI().toString()));
        driverSetup.setImage(new Image(new File("images\\setup1.png").toURI().toString()));
        minimizedLogo.setImage(new Image(new File("images\\PCCleaner.png").toURI().toString()));
        logo.setImage(new Image(new File("images\\PCCleaner.png").toURI().toString()));
        quickIcon.setImage(new Image(new File("images\\quick.png").toURI().toString()));
        customIcon.setImage(new Image(new File("images\\custom.png").toURI().toString()));
        exitIcon.setImage(new Image(new File("images\\exit.png").toURI().toString()));
        driverIcon.setImage(new Image(new File("images\\drivers.png").toURI().toString()));
        toolsIcon.setImage(new Image(new File("images\\tools.png").toURI().toString()));
        optionsIcon.setImage(new Image(new File("images\\settings.png").toURI().toString()));
        specificationsIcon.setImage(new Image(new File("images\\specifications.png").toURI().toString()));
        explorerIcon.setImage(new Image(new File("images\\explorer.png").toURI().toString()));
        edgeIcon.setImage(new Image(new File("images\\chromium.png").toURI().toString()));
        systemIcon.setImage(new Image(new File("images\\windows.png").toURI().toString()));
        chromeIcon.setImage(new Image(new File("images\\chrome.png").toURI().toString()));
        spotifyIcon.setImage(new Image(new File("images\\spotify.png").toURI().toString()));
        steamIcon.setImage(new Image(new File("images\\steam.png").toURI().toString()));
        minimizeIcon.setImage(new Image(new File("images\\minimize.png").toURI().toString()));
        maximiseIcon.setImage(new Image(new File("images\\maximise.png").toURI().toString()));
        closeIcon.setImage(new Image(new File("images\\close.png").toURI().toString()));
    }

    private void setUIElements() {
        //clean UI elements
        Clean.icon = quickSetup;
        Clean.clean = quickClean;

        //custom UI elements
        Custom.windowsPanel = windowsPanel;
        Custom.applicationPanel = applicationPanel;
        Custom.list = customList;
        Custom.windows = customWindows;
        Custom.applications = customApplications;
        Custom.analyze = customAnalyze;
        Custom.clean = customClean;
        Custom.explorerPanel = explorerPanel;
        Custom.selectExplorer = selectExplorer;
        Custom.explorerTemp = explorerTemp;
        Custom.explorerHistory = explorerHistory;
        Custom.explorerCookies = explorerCookies;
        Custom.edgePanel = edgePanel;
        Custom.selectEdge = selectEdge;
        Custom.edgeCache = edgeCache;
        Custom.edgeHistory = edgeHistory;
        Custom.edgeCookies = edgeCookies;
        Custom.edgeSession = edgeSession;
        Custom.systemPanel = systemPanel;
        Custom.selectSystem = selectSystem;
        Custom.systemBin = systemBin;
        Custom.systemTemp = systemTemp;
        Custom.systemClipboard = systemClipboard;
        Custom.systemDump = systemDump;
        Custom.systemLog = systemLog;
        Custom.systemReport = systemReport;
        Custom.systemStartMenu = systemStartMenu;
        Custom.systemDesktop = systemDesktop;
        Custom.chromePanel = chromePanel;
        Custom.selectChrome = selectChrome;
        Custom.chromeCache = chromeCache;
        Custom.chromeHistory = chromeHistory;
        Custom.chromeCookies = chromeCookies;
        Custom.chromeSession = chromeSession;
        Custom.spotifyPanel= spotifyPanel;
        Custom.selectSpotify = selectSpotify;
        Custom.spotifyCache = spotifyCache;
        Custom.spotifyMusic = spotifyMusic;
        Custom.spotifyOffline = spotifyOffline;
        Custom.steamPanel = steamPanel;
        Custom.selectSteam = selectSteam;
        Custom.steamDump = steamDump;

        //drivers UI elements
        Drivers.icon = driverSetup;
        Drivers.open = driveOpen;

        //tools UI elements
        Tools.panel = toolsPanel;
        Tools.uninstall = toolsUninstall;
        Tools.cleanup = toolsCleanUp;
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
        Tools.name = analyzeName;
        Tools.path = analyzePath;
        Tools.type = analyzeType;
        Tools.analyzeStatus = analyzeStatus;
        Tools.btnCancel = btnCancel;
        Tools.wiperDeleted = wiperDeleted;
        Tools.wiperDrives = wiperDrives;
        Tools.wipeDrive = wipeDrive;
        Tools.wiperStatus = wiperStatus;
        Tools.comboWipes = comboWipes;
        Tools.cancelWipe = cancelWipe;
        Tools.uninstallSearch = uninstallSearch;
        Tools.uninstallTable = uninstallTable;
        Tools.uninstallName = uninstallName;
        Tools.uninstallPublisher = uninstallPublisher;
        Tools.uninstallLocation = uninstallLocation;
        Tools.uninstallDate = uninstallDate;
        Tools.uninstallVersion = uninstallVersion;
        Tools.uninstallSave = uninstallSave;
        Tools.btnUninstall = btnUninstall;
        Tools.cleanupDrives = cleanupDrives;
        Tools.cleanupClean = cleanupClean;
        Tools.startupTable = startupTable;
        Tools.startupKey = startupKey;
        Tools.startupPath = startupPath;
        Tools.startupAdd = startupAdd;
        Tools.startupSave = startupSave;
        Tools.pluginTable = pluginTable;
        Tools.pluginProgram = pluginProgram;
        Tools.pluginFile = pluginFile;
        Tools.pluginVersion = pluginVersion;
        Tools.pluginGoogleChrome = pluginGoogleChrome;
        Tools.pluginInternetExplorer = pluginInternetExplorer;
        Tools.pluginSave = pluginSave;
        Tools.pluginStatus = pluginStatus;

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

    public void drivers() {
        driverPane.toFront();
    }

    public void tools() {
        toolsPane.toFront();
    }

    public void toolsUninstall() {
        toolsUninstallPanel.toFront();
    }

    public void toolsCleanUp() {
        toolsCleanUpPanel.toFront();
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
