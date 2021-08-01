package Main.Elements;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FilenameUtils;

public class Tools {

    public static AnchorPane panel;
    public static JFXButton uninstall;
    public static JFXButton updater;
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

    public static TableView<FileModel> fileTable;
    public static TableColumn<FileModel, String> name;
    public static TableColumn<FileModel, String> path;
    public static TableColumn<FileModel, String> type;
    public static JFXButton btnCancel;
    private static boolean cancelled = false;

    private static final ArrayList<FileModel> list = new ArrayList<>();
    private static final ObservableList<FileModel> filesList = FXCollections.observableList(list);

    public static void load() {
        analyzer();
    }

    public static void uninstall() {

    }

    public static void updater() {

    }

    public static void startup() {

    }

    public static void plugins() {

    }

    public static void wiper() {

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
                        saveTXT();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                delete.setOnAction(actionEvent -> deleteFiles(fileTable.getSelectionModel().getSelectedItems()));
                open.setOnAction(actionEvent -> {
                    try {
                        openContainingFolder();
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
                    while (!verifyThreads(threads) && !cancelled) {
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

    private static void saveTXT() throws IOException {
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

    private static void deleteFiles(ObservableList<FileModel> selectedItems) {
        for (FileModel item : selectedItems) {
            File file = new File(item.getPath());
            if (file.delete()) {
                fileTable.getItems().remove(item);
            }
        }
    }

    private static void openContainingFolder() throws IOException {
        File file = new File(fileTable.getSelectionModel().getSelectedItem().getPath());
        Desktop.getDesktop().open(new File(file.getParent()));
    }

    public static boolean verifyThreads(ArrayList<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive())
                return false;
        }
        return true;
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

}