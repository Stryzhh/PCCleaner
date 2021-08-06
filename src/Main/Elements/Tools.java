package Main.Elements;

import Main.Default.Functions;
import Main.Objects.Extension;
import Main.System.Extensions;
import Main.System.Applications;
import Main.Objects.Software;
import Main.System.StartUp;
import Main.Objects.File;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Tools {

    public static AnchorPane panel;
    public static JFXButton uninstall;
    public static JFXButton cleanup;
    public static JFXButton startup;
    public static JFXButton plugins;
    public static JFXButton wiper;
    public static JFXButton analyze;

    public static JFXCheckBox analyzePictures;
    public static JFXCheckBox analyzeMusic;
    public static JFXCheckBox analyzeDocuments;
    public static JFXCheckBox analyzeVideo;
    public static JFXCheckBox analyzeEmail;
    public static JFXCheckBox analyzeCompressed;
    public static JFXCheckBox analyzeEverything;
    public static ListView<java.io.File> analyzeDrives;
    public static JFXButton btnAnalyze;
    public static Label analyzeStatus;

    private static final ArrayList<String> fileExtensions = new ArrayList<>();
    private static final String[] imageExtensions = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    private static final String[] musicExtensions = {".aif", ".aifc", ".aiff", ".m4a", ".mp3", ".wav", ".wma"};
    private static final String[] documentsExtensions = {".doc", ".docx", ".pdf", ".ppt", ".xls"};
    private static final String[] videoExtensions = {".mpg", ".mp4", ".mov", ".m4v"};
    private static final String[] compressedExtensions = {".7z", ".gz", ".cab", ".zip"};
    private static final String[] emailExtensions = {".eml", ".mbox", ".msg"};
    private static final String[] all = {"."};
    private static final ArrayList<Thread> threads = new ArrayList<>();
    public static Label pluginStatus;
    private static boolean cancelled = false;
    private static final ArrayList<File> filesArrayList = new ArrayList<>();
    private static final ObservableList<File> filesList = FXCollections.observableList(filesArrayList);

    public static TableView<File> fileTable;
    public static TableColumn<File, String> name;
    public static TableColumn<File, String> path;
    public static TableColumn<File, String> type;
    public static JFXButton btnCancel;
    public static ListView<String> wiperDeleted;
    public static ListView<java.io.File> wiperDrives;
    public static JFXButton wipeDrive;
    public static JFXButton cancelWipe;
    public static ComboBox<Integer> comboWipes;
    public static Label wiperStatus;

    public static TextField uninstallSearch;
    public static TableView<Software> uninstallTable;
    public static TableColumn<Software, String> uninstallName;
    public static TableColumn<Software, String> uninstallPublisher;
    public static TableColumn<Software, String> uninstallLocation;
    public static TableColumn<Software, LocalDateTime> uninstallDate;
    public static TableColumn<Software, String> uninstallVersion;
    public static JFXButton btnUninstall;
    public static JFXButton uninstallSave;

    public static TableView<Main.Objects.Application> startupTable;
    public static TableColumn<Main.Objects.Application, String> startupKey;
    public static TableColumn<Main.Objects.Application, String> startupPath;
    public static JFXButton startupAdd;
    public static JFXButton startupSave;

    public static TableView<Extension> pluginTable;
    public static TableColumn<Extension, String> pluginProgram;
    public static TableColumn<Extension, String> pluginFile;
    public static TableColumn<Extension, String> pluginVersion;
    public static JFXButton pluginGoogleChrome;
    public static JFXButton pluginInternetExplorer;
    public static JFXButton pluginSave;

    public static ListView<java.io.File> cleanupDrives;
    public static JFXButton cleanupClean;
    public static boolean chrome = true;

    public static void load() {
        uninstall();
        analyzer();
        plugins();
        wiper();
        startup();
        cleanup();
    }

    public static void uninstall() {
        loadUninstall();
        searchListener();
        uninstallTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        uninstallTable.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();
                MenuItem open = new MenuItem("Open containing folder");

                uninstallTable.setContextMenu(contextMenu);
                if (uninstallTable.getSelectionModel().getSelectedItem().getLocation().equals("Unknown")) {
                    open.setDisable(true);
                }
                contextMenu.getItems().addAll(open);
                contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                open.setOnAction(actionEvent -> {
                    try {
                        openContainingFolder(uninstallTable.getSelectionModel().getSelectedItem().getLocation());
                    } catch (IOException e) {
                        Functions.error = "Couldn't open folder";
                        try {
                            Functions.openWindow("Main/Error/error.fxml", "Error");
                        } catch (IOException ioException) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        uninstallSave.setOnAction(actionEvent -> {
            if (Options.advancedSettings.isProduce()) {
                try {
                    PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\Installed.txt", "UTF-8");
                    for (Software soft : uninstallTable.getItems()) {
                        writer.println(soft.getName() + " - @" + soft.getLocation());
                    }
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                    Functions.error = "Couldn't write to file";
                    try {
                        Functions.openWindow("Main/Error/error.fxml", "Error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnUninstall.setOnAction(actionEvent -> {
            if (!Options.advancedSettings.isHide()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    try {
                        for (Software item : uninstallTable.getSelectionModel().getSelectedItems()) {
                            new ProcessBuilder(item.getUninstall()).start();
                        }
                    } catch (IOException e) {
                        Functions.error = "Couldn't uninstall program - need administrator privileges";
                        try {
                            Functions.openWindow("Main/Error/error.fxml", "Error");
                        } catch (IOException exception) {
                            //ignore
                        }
                    }
                }
            }
        });
    }

    public static void cleanup() {
        java.io.File[] paths;
        paths = java.io.File.listRoots();
        for (java.io.File path : paths) {
            cleanupDrives.getItems().add(path);
        }

        cleanupClean.setOnAction(e -> {
            if (!Options.advancedSettings.isHide()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    java.io.File drive = cleanupDrives.getSelectionModel().getSelectedItem();
                    if (drive != null) {
                        try {
                            new ProcessBuilder(System.getenv("WINDIR") + "\\system32\\cleanmgr.exe", "/d " + drive).start();
                        } catch (IOException ioException) {
                            Functions.error = "Couldn't run disk clean-up";
                            try {
                                Functions.openWindow("Main/Error/error.fxml", "Error");
                            } catch (IOException exception) {
                                //ignore
                            }
                        }
                    }
                }
            }
        });
    }

    public static void startup() {
        loadStartup();
        startupTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        startupTable.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();
                MenuItem delete = new MenuItem("Remove application(s)");

                startupTable.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(delete);
                contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                delete.setOnAction(actionEvent -> {
                    if (!Options.advancedSettings.isHide()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            if (startupTable.getSelectionModel().getSelectedItems() != null) {
                                for (Main.Objects.Application item : startupTable.getSelectionModel().getSelectedItems()) {
                                    Advapi32Util.registryDeleteValue(item.getKey(), item.getDir(), item.getName());
                                    startupTable.getItems().remove(item);
                                }
                            }
                        }
                    }
                });
            }
        });

        startupAdd.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            java.io.File result = fileChooser.showOpenDialog(null);

            if (result != null) {
                if (result.getName().contains(".exe")) {
                    String name = result.getName().replace(".exe", "");
                    Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run",
                            name, result.getAbsolutePath());
                    startupTable.getItems().clear();
                    loadStartup();
                }
            }
        });

        startupSave.setOnAction(e -> {
            if (Options.advancedSettings.isProduce()) {
                try {
                    PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\StartUp.txt", "UTF-8");
                    for (Main.Objects.Application app : startupTable.getItems()) {
                        writer.println(app.getName() + " - @" + app.getPath());
                    }
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                    Functions.error = "Couldn't write to file";
                    try {
                        Functions.openWindow("Main/Error/error.fxml", "Error");
                    } catch (IOException exception) {
                        //ignore
                    }
                }
            }
        });
    }

    private static void loadStartup() {
        ArrayList<Main.Objects.Application> startupApps = StartUp.getInstalledApps();
        for (Main.Objects.Application app : startupApps) {
            startupTable.getItems().add(app);
        }
    }

    public static void plugins() {
        new Thread(Tools::loadChrome).start();

        pluginTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pluginTable.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                if (pluginTable.getSelectionModel().getSelectedItems() != null) {
                    ObservableList<Extension> selected = pluginTable.getSelectionModel().getSelectedItems();

                    final ContextMenu contextMenu = new ContextMenu();
                    final AnchorPane pane = new AnchorPane();
                    MenuItem open = new MenuItem("Open extension on web");
                    MenuItem delete = new MenuItem("Delete extension");
                    MenuItem folder = new MenuItem("Open containing folder");

                    if (!chrome) {
                        open.setDisable(true);
                        folder.setDisable(true);
                    }
                    pluginTable.setContextMenu(contextMenu);
                    contextMenu.getItems().addAll(open, delete, folder);
                    contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                    open.setOnAction(e -> {
                        for (Extension ext : selected) {
                            try {
                                Desktop.getDesktop().browse(URI.create(ext.getURL()));
                            } catch (IOException ioException) {
                                Functions.error = "Can't open extension";
                                try {
                                    Functions.openWindow("Main/Error/error.fxml", "Error");
                                } catch (IOException exception) {
                                    //ignore
                                }
                            }
                        }
                    });
                    delete.setOnAction(e -> {
                        if (!Options.advancedSettings.isHide()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                if (pluginTable.getSelectionModel().getSelectedItems() != null) {
                                    for (Extension item : pluginTable.getSelectionModel().getSelectedItems()) {
                                        if (chrome) {
                                            try {
                                                FileUtils.deleteDirectory(new java.io.File(item.getFile()));
                                                pluginTable.getItems().remove(item);
                                            } catch (IOException ioException) {
                                                Functions.error = "Can't delete extension";
                                                try {
                                                    Functions.openWindow("Main/Error/error.fxml", "Error");
                                                } catch (IOException exception) {
                                                    //ignore
                                                }
                                            }
                                        } else {
                                            try {
                                                Advapi32Util.registryDeleteKey(WinReg.HKEY_LOCAL_MACHINE, item.getFile());
                                                pluginTable.getItems().remove(item);
                                            } catch (Exception ex) {
                                                Functions.error = "Couldn't delete key - important key";
                                                try {
                                                    Functions.openWindow("Main/Error/error.fxml", "Error");
                                                } catch (IOException exc) {
                                                    //ignore
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                    folder.setOnAction(e -> {
                        try {
                            Desktop.getDesktop().open(new java.io.File(pluginTable.getSelectionModel().getSelectedItem().getFile()));
                        } catch (IOException ioException) {
                            Functions.error = "Couldn't open file";
                            try {
                                Functions.openWindow("Main/Error/error.fxml", "Error");
                            } catch (IOException ex) {
                                //ignore
                            }
                        }
                    });
                }
            }
        });

        pluginInternetExplorer.setOnAction(e -> new Thread(Tools::loadExplorer).start());
        pluginGoogleChrome.setOnAction(e -> new Thread(Tools::loadChrome).start());
        pluginSave.setOnAction(e -> {
            if (Options.advancedSettings.isProduce()) {
                try {
                    PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\Plugins.txt", "UTF-8");
                    for (Extension ext : pluginTable.getItems()) {
                        writer.println(ext.getProgram() + " - @" + ext.getFile());
                    }
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                    Functions.error = "Couldn't write to file";
                    try {
                        Functions.openWindow("Main/Error/error.fxml", "Error");
                    } catch (IOException ioException) {
                        //ignore
                    }
                }
            }
        });
    }

    private static void loadExplorer() {
        Platform.runLater(() -> pluginStatus.setText("Loading..."));
        chrome = false;
        pluginInternetExplorer.setDisable(true);
        pluginGoogleChrome.setDisable(true);

        pluginTable.getItems().clear();
        ArrayList<Extension> extensions = Extensions.getExtensions();
        for (Extension ext : extensions) {
            pluginTable.getItems().add(ext);
        }

        pluginGoogleChrome.setDisable(false);
        Platform.runLater(() -> pluginStatus.setText("Loaded."));
    }

    private static void loadChrome() {
        Platform.runLater(() -> pluginStatus.setText("Loading..."));
        chrome = true;
        pluginGoogleChrome.setDisable(true);
        pluginInternetExplorer.setDisable(true);

        pluginTable.getItems().clear();
        ArrayList<Extension> extensions = getExtensions(System.getProperty("user.home") +
                "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions");
        for (Extension ext : extensions) {
            pluginTable.getItems().add(ext);
        }
        pluginInternetExplorer.setDisable(false);
        Platform.runLater(() -> pluginStatus.setText("Loaded."));
    }

    private static ArrayList<Extension> getExtensions(String path) {
        ArrayList<Extension> pluginList = new ArrayList<>();
        java.io.File[] folders = new java.io.File(path).listFiles();

        if (folders != null) {
            for (java.io.File folder : folders) {
                if (folder.isDirectory()) {
                    java.io.File[] files = folder.listFiles();
                    String URL = "https://chrome.google.com/webstore/detail/" + folder.getName();
                    String name = convertToName(URL);
                    String file = folder.getAbsolutePath();

                    String version;
                    assert files != null;
                    if (files.length > 0) {
                        version = Objects.requireNonNull(folder.listFiles())[0].getName();
                    } else {
                        version = "Unknown";
                    }
                    pluginList.add(new Extension(name, file, version, URL));
                }
            }
        }
        return pluginList;
    }

    private static String convertToName(String page) {
        try {
            URL url = new URL(page);
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            String line = br.readLine();
            if (line != null) {
                String[] before = line.split("<title>");
                String[] after = before[1].split("- Chrome Web Store");
                return after[0];
            }
        } catch (Exception ex) {
            Functions.error = "Couldn't get title";
            try {
                Functions.openWindow("Main/Error/error.fxml", "Error");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Unknown";
        }
        return "Unknown";
    }

    public static void wiper() {
        java.io.File[] paths;
        paths = java.io.File.listRoots();
        for (java.io.File path : paths) {
            wiperDrives.getItems().add(path);
        }

        wipeDrive.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you 100% sure you want to fully wipe this drive?", ButtonType.YES, ButtonType.NO);
                confirm.showAndWait();

                if (confirm.getResult() == ButtonType.YES) {
                    cancelled = false;
                    wiperDeleted.getItems().clear();

                    int count = 1;
                    if (comboWipes.getSelectionModel().getSelectedItem() != null) {
                        count = comboWipes.getSelectionModel().getSelectedItem();
                    }

                    java.io.File drive = wiperDrives.getSelectionModel().getSelectedItem();
                    java.io.File[] files = drive.listFiles();
                    int c = 0;
                    if (files != null) {
                        while (!cancelled && count != c) {
                            for (java.io.File file : files) {
                                new Thread(() -> {
                                    if (file.isDirectory()) {
                                        try {
                                            FileUtils.deleteDirectory(file);
                                            Platform.runLater(() -> wiperDeleted.getItems().add("deleted: " + file));
                                        } catch (IOException ioException) {
                                            Functions.error = "Status: some important files can't be deleted";
                                            try {
                                                Functions.openWindow("Main/Error/error.fxml", "Error");
                                            } catch (IOException exception) {
                                                exception.printStackTrace();
                                            }
                                            Platform.runLater(() -> wiperStatus.setText("Status: some important files can't be deleted"));
                                        }
                                    } else if (file.delete()) {
                                        Platform.runLater(() -> wiperDeleted.getItems().add("deleted: " + file));
                                    }
                                }).start();
                            }
                            Platform.runLater(() -> wiperStatus.setText("Status: deleted all files off drive"));
                            c++;
                        }
                    }
                }
            }
        });

        cancelWipe.setOnAction(e -> cancelled = true);
    }

    public static void analyzer() {
        java.io.File[] paths;
        paths = java.io.File.listRoots();
        for (java.io.File path : paths) {
            analyzeDrives.getItems().add(path);
        }

        fileTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fileTable.setOnMouseClicked(MouseEvent -> {
            if (MouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();
                MenuItem delete = new MenuItem("Delete selected files");
                MenuItem save = new MenuItem("Save to .txt file");
                MenuItem open = new MenuItem("Open containing folder");

                fileTable.setContextMenu(contextMenu);
                contextMenu.getItems().addAll(delete, save, open);
                contextMenu.show(pane, MouseEvent.getScreenX(), MouseEvent.getScreenY());

                save.setOnAction(actionEvent -> {
                    if (Options.advancedSettings.isProduce()) {
                        try {
                            PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\Files.txt", "UTF-8");
                            for (File file : fileTable.getItems()) {
                                writer.println(file.getName() + " - @" + file.getPath());
                            }
                            writer.close();
                        } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                            Functions.error = "Couldn't write to file";
                            try {
                                Functions.openWindow("Main/Error/error.fxml", "Error");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                delete.setOnAction(actionEvent -> {
                    if (!Options.advancedSettings.isHide()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            deleteFiles(fileTable.getSelectionModel().getSelectedItems());
                        }
                    }
                });
                open.setOnAction(actionEvent -> {
                    try {
                        openContainingFolder(fileTable.getSelectionModel().getSelectedItem().getPath());
                    } catch (IOException e) {
                        Functions.error = "Couldn't open folder";
                        try {
                            Functions.openWindow("Main/Error/error.fxml", "Error");
                        } catch (IOException ioException) {
                            //ignore
                        }
                    }
                });
            }
        });

        btnAnalyze.setOnAction(e -> {
            cancelled = false;
            filesList.clear();
            filesArrayList.clear();
            fileTable.getItems().clear();
            threads.clear();
            fileExtensions.clear();

            if (analyzeDrives.getSelectionModel().getSelectedItem() != null) {
                java.io.File drive = analyzeDrives.getSelectionModel().getSelectedItem();

                if (analyzePictures.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(imageExtensions));
                }
                if (analyzeMusic.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(musicExtensions));
                }
                if (analyzeDocuments.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(documentsExtensions));
                }
                if (analyzeVideo.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(videoExtensions));
                }
                if (analyzeCompressed.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(compressedExtensions));
                }
                if (analyzeEmail.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(emailExtensions));
                }
                if (analyzeEverything.isSelected()) {
                    fileExtensions.addAll(Arrays.asList(all));
                }

                if (fileExtensions.size() != 0) {
                    java.io.File[] files = drive.listFiles();

                    if (files != null) {
                        for (java.io.File file : files) {
                            if (java.lang.Thread.activeCount() < 256) {
                                Thread thread = new Thread(() -> {
                                    if (file.isDirectory()) {
                                        java.io.File[] filesInFolder = file.listFiles();

                                        if (filesInFolder != null) {
                                            for (java.io.File innerFile : filesInFolder) {
                                                if (innerFile.isFile()) {
                                                    isTheSearchedFile(innerFile);
                                                } else if (innerFile.isDirectory()) {
                                                    java.io.File[] list = innerFile.listFiles();
                                                    if (list != null) {
                                                        search(list);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        isTheSearchedFile(file);
                                    }
                                });
                                thread.start();
                                threads.add(thread);
                            }
                        }
                    }
                }
                new Thread(() -> {
                    while (verifyThreads(threads) && !cancelled) {
                        if (!analyzeStatus.getText().equals("Status: loading..."))
                            Platform.runLater(() -> analyzeStatus.setText("Status: loading..."));
                    }

                    Platform.runLater(() -> analyzeStatus.setText("Status: finished"));
                    fileTable.setItems(filesList);
                    fileTable.refresh();
                }).start();
            }
        });

        btnCancel.setOnAction(e -> cancelled = true);
    }

    public static boolean verifyThreads(ArrayList<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive())
                return true;
        }
        return false;
    }

    private static void search(java.io.File[] files) {
        for (java.io.File file : files) {
            if (file.isDirectory()) {
                java.io.File[] filesInFolder = file.listFiles();

                if (filesInFolder != null) {
                    for (java.io.File innerFiles : filesInFolder) {
                        if (innerFiles.isFile()) {
                            isTheSearchedFile(innerFiles);
                        } else if (innerFiles.isDirectory()) {
                            java.io.File[] list = innerFiles.listFiles();
                            if (list != null) {
                                search(list);
                            }
                        }
                    }
                }
            } else {
                isTheSearchedFile(file);
            }
        }
    }

    private static void isTheSearchedFile(java.io.File file) {
        for (String ext : fileExtensions) {
            if (file.getName().contains(ext)) {
                filesArrayList.add(new File(file.getName(), file.getAbsolutePath(), "." + FilenameUtils.getExtension(file.getName())));
                break;
            }
        }
    }

    private static void loadUninstall() {
        uninstallTable.getItems().removeAll(uninstallTable.getItems());
        Map<String, Software> installed = Applications.getInstalledApps(false);
        for (Map.Entry<String, Software> app : installed.entrySet()) {
            uninstallTable.getItems().add(app.getValue());
            switch (app.getValue().getName()) {
                case "Google Chrome":
                    Custom.chrome = true;
                    break;
                case "Spotify":
                    Custom.spotify = true;
                    break;
                case "Steam":
                    Custom.steam = true;
                    break;
            }
        }
    }

    private static void searchListener() {
        FilteredList<Software> filteredData = new FilteredList<>(uninstallTable.getItems(), p -> true);
        uninstallSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(myObject -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter);
        }));

        SortedList<Software> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(uninstallTable.comparatorProperty());
        uninstallTable.setItems(sortedData);
    }

    private static void deleteFiles(ObservableList<File> selectedItems) {
        for (File item : selectedItems) {
            java.io.File file = new java.io.File(item.getPath());
            if (file.delete()) {
                fileTable.getItems().remove(item);
            }
        }
    }

    private static void openContainingFolder(String path) throws IOException {
        java.io.File file = new java.io.File(path);
        Desktop.getDesktop().open(new java.io.File(file.getParent()));
    }

}
