package Main.Elements;

import Main.Applications.Application;
import Main.Applications.ListApps;
import Main.Applications.MyEntry;
import Main.Applications.Software;
import Main.Applications.StartUpApps;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileExistsException;
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
    public static ListView<File> analyzeDrives;
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
    private static boolean cancelled = false;
    private static final ArrayList<FileModel> list = new ArrayList<>();
    private static final ObservableList<FileModel> filesList = FXCollections.observableList(list);

    public static TableView<FileModel> fileTable;
    public static TableColumn<FileModel, String> name;
    public static TableColumn<FileModel, String> path;
    public static TableColumn<FileModel, String> type;
    public static JFXButton btnCancel;
    public static ListView<String> wiperDeleted;
    public static ListView<File> wiperDrives;
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

    public static TableView<Application> startupTable;
    public static TableColumn<Application, String> startupKey;
    public static TableColumn<Application, String> startupPath;
    public static JFXButton startupAdd;
    public static JFXButton startupSave;

    public static ListView<File> cleanupDrives;
    public static JFXButton cleanupClean;

    public static void load() {
        uninstall();
        analyzer();
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
                        e.printStackTrace();
                    }
                });
            }
        });
        uninstallSave.setOnAction(actionEvent -> {
            try {
                saveApplicationsTXT();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnUninstall.setOnAction(actionEvent -> {
            try {
                for (Software item : uninstallTable.getSelectionModel().getSelectedItems()) {
                    new ProcessBuilder(item.getUninstall()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void cleanup() {
        File[] paths;
        paths = File.listRoots();
        for (File path : paths) {
            cleanupDrives.getItems().add(path);
        }

        cleanupClean.setOnAction(e -> {
            File drive = cleanupDrives.getSelectionModel().getSelectedItem();
            if (drive != null) {
                try {
                    new ProcessBuilder(System.getenv("WINDIR") + "\\system32\\cleanmgr.exe", "/d " + drive).start();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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
                    if (startupTable.getSelectionModel().getSelectedItems() != null) {
                        for (Application item : startupTable.getSelectionModel().getSelectedItems()) {
                            Advapi32Util.registryDeleteValue(item.getKey(), item.getDir(), item.getName());
                            startupTable.getItems().remove(item);
                        }
                    }
                });
            }
        });

        startupAdd.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File result = fileChooser.showOpenDialog(null);

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
            try {
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        System.getProperty("user.home") + "\\Documents\\listOfStartUpApps.txt"), StandardCharsets.UTF_8));
                startupTable.getItems().forEach((o) -> {
                    try {
                        writer.write(o.getName() + " - @" + o.getPath() + "\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }


    private static void loadStartup() {
        ArrayList<Application> startupApps = StartUpApps.getInstalledApps();
        for (Application app : startupApps) {
            startupTable.getItems().add(app);
        }
    }

    public static void plugins() {

    }

    public static void wiper() {
        File[] paths;
        paths = File.listRoots();
        for (File path : paths) {
            wiperDrives.getItems().add(path);
        }

        wipeDrive.setOnAction(e -> {
            cancelled = false;
            wiperDeleted.getItems().clear();

            int count = 1;
            if (comboWipes.getSelectionModel().getSelectedItem() != null) {
                count = comboWipes.getSelectionModel().getSelectedItem();
            }

            File drive = wiperDrives.getSelectionModel().getSelectedItem();
            File[] files = drive.listFiles();
            int c = 0;
            if (files != null) {
                while (!cancelled && count != c) {
                    for (File file : files) {
                        new Thread(() -> {
                            if (file.isDirectory()) {
                                try {
                                    FileUtils.deleteDirectory(file);
                                    Platform.runLater(() -> wiperDeleted.getItems().add("deleted: " + file));
                                } catch (IOException ioException) {
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
        });

        cancelWipe.setOnAction(e -> cancelled = true);
    }

    public static void analyzer() {
        File[] paths;
        paths = File.listRoots();
        for (File path : paths) {
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
                    try {
                        saveFilesTXT();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                delete.setOnAction(actionEvent -> deleteFiles(fileTable.getSelectionModel().getSelectedItems()));
                open.setOnAction(actionEvent -> {
                    try {
                        openContainingFolder(fileTable.getSelectionModel().getSelectedItem().getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        btnAnalyze.setOnAction(e -> {
            cancelled = false;
            filesList.clear();
            list.clear();
            fileTable.getItems().clear();
            threads.clear();
            fileExtensions.clear();

            if (analyzeDrives.getSelectionModel().getSelectedItem() != null) {
                File drive = analyzeDrives.getSelectionModel().getSelectedItem();

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
                    File[] files = drive.listFiles();

                    if (files != null) {
                        for (File file : files) {
                            if (java.lang.Thread.activeCount() < 256) {
                                Thread thread = new Thread(() -> {
                                    if (file.isDirectory()) {
                                        File[] filesInFolder = file.listFiles();

                                        if (filesInFolder != null) {
                                            for (File innerFile : filesInFolder) {
                                                if (innerFile.isFile()) {
                                                    isTheSearchedFile(innerFile);
                                                } else if (innerFile.isDirectory()) {
                                                    File[] list = innerFile.listFiles();
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
        for (int i = 0; i < threads.size(); i++) {
            if (threads.get(i).isAlive())
                return true;
        }
        return false;
    }

    private static void search(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                File[] filesInFolder = file.listFiles();

                if (filesInFolder != null) {
                    for (File innerFiles : filesInFolder) {
                        if (innerFiles.isFile()) {
                            isTheSearchedFile(innerFiles);
                        } else if (innerFiles.isDirectory()) {
                            File[] list = innerFiles.listFiles();
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

    private static void isTheSearchedFile(File file) {
        for (String ext : fileExtensions) {
            if (file.getName().contains(ext)) {
                list.add(new FileModel(file.getName(), file.getAbsolutePath(), "." + FilenameUtils.getExtension(file.getName())));
                break;
            }
        }
    }

    private static void loadUninstall() {
        uninstallTable.getItems().removeAll(uninstallTable.getItems());
        Map<String, Software> inst = ListApps.getInstalledApps(false);
        for (Map.Entry<String, Software> app : inst.entrySet()) {
            uninstallTable.getItems().add(app.getValue());
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

    private static void saveFilesTXT() throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                System.getProperty("user.home") + "\\Documents\\listOfFiles.txt"), StandardCharsets.UTF_8))) {
            fileTable.getItems().forEach((o) -> {
                try {
                    writer.write(o.getPath() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void saveApplicationsTXT() throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                System.getProperty("user.home") + "\\Documents\\listOfApps.txt"), StandardCharsets.UTF_8))) {
            uninstallTable.getItems().forEach((o) -> {
                try {
                    writer.write(o.getName() + " - " + o.getLocation() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private static void deleteFiles(ObservableList<FileModel> selectedItems) {
        for (FileModel item : selectedItems) {
            File file = new File(item.getPath());
            if (file.delete()) {
                fileTable.getItems().remove(item);
            }
        }
    }

    private static void openContainingFolder(String path) throws IOException {
        File file = new File(path);
        Desktop.getDesktop().open(new File(file.getParent()));
    }

}