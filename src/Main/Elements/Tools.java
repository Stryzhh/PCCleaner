package Main.Elements;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private static final ArrayList<String> fileExtensions = new ArrayList<>();
    private static final String[] imageExtensions = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
    private static final String[] musicExtensions = {".aif", ".aifc", ".aiff", ".m4a", ".mp3", ".wav", ".wma"};
    private static final String[] documentsExtensions = {".doc", ".docx", ".pdf", ".ppt", ".xls"};
    private static final String[] videoExtensions = {".mpg", ".mp4", ".mov", ".m4v"};
    private static final String[] compressedExtensions = {".7z", ".gz", ".cab", ".zip"};
    private static final String[] emailExtensions = {".eml", ".mbox", ".msg"};
    private static final String[] all = {"."};

    public static TableView<FileModel> fileTable;
    public static TableColumn<FileModel, String> name;
    public static TableColumn<FileModel, String> path;
    public static TableColumn<FileModel, String> type;
    public static ArrayList<FileModel> list = new ArrayList<>();
    public static ObservableList<FileModel> filesList = FXCollections.observableList(list);

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
        fileTable.getItems().clear();
        filesList.clear();
        list.clear();
        fileTable.refresh();
        File[] paths;
        paths = File.listRoots();

        for (File path : paths) {
            analyzeDrives.getItems().add(path);
        }

        btnAnalyze.setOnAction(e -> {
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
                                new Thread(() -> {
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
                                }).start();
                            }
                        }
                    }
                }
            }
        });

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
            if (fileExtensions.contains(".")) {
                list.add(new FileModel(file.getName(), file.getAbsolutePath(), "." + FilenameUtils.getExtension(file.getName())));
                break;
            } else if (file.getName().endsWith(ext)) {
                list.add(new FileModel(file.getName(), file.getAbsolutePath(), "." + FilenameUtils.getExtension(file.getName())));
                break;
            }
        }
        System.out.println(list.size());
        System.out.println(filesList.size());
        Platform.runLater(() -> fileTable.setItems(filesList));
    }

}