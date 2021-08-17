package Main.Elements;

import Main.Configuration.Config;
import Main.Configuration.Junk;
import Main.Default.Functions;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;

public class Clean {

    public static ImageView icon;
    public static JFXButton clean;
    public static Config cfg;

    public static void load() {
        clean.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                cfg = Options.config;

                ArrayList<String> deleted = new ArrayList<>();
                try {
                    for (int i = 0; i < Junk.quickClean.length; i++) {
                        File[] f = Junk.quickClean[i].listFiles();

                        if (f != null) {
                            ArrayList<File> files = new ArrayList<>(Arrays.asList(f));
                            for (String item : cfg.getExcludeItems()) {
                                files.remove(new File(item));
                            }

                            for (File file : files) {
                                if (file.isDirectory()) {
                                    recursiveDelete(file, deleted);
                                } else {
                                    if (file.delete()) {
                                        deleted.add("deleted: " + file.getName());
                                    }
                                }
                            }
                        }
                    }

                    for (String location : cfg.getIncludeItems()) {
                        File include = new File(location);
                        if (include.isDirectory()) {
                            recursiveDelete(include, deleted);
                        } else {
                            if (include.delete()) {
                                deleted.add("deleted: " + include.getName());
                            }
                        }
                    }

                    File[] drives = File.listRoots();
                    for (File drive : drives) {
                        String[] driveLetter = drive.getAbsolutePath().split(":");
                        new ProcessBuilder(System.getenv("WINDIR") + "\\system32\\cleanmgr.exe", "/d " + driveLetter[0], "/verylowdisk").start();
                        deleted.add("Cleaned: " + drive + " drive");
                    }

                    Collections.reverse(deleted);
                    if (Options.advancedSettings.isProduce()) {
                        try {
                            PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\Clean.txt");
                            for (String line : deleted) {
                                writer.println(line);
                            }
                            writer.close();
                        } catch (FileNotFoundException fileNotFoundException) {
                            Functions.error = "Couldn't write to file";
                            try {
                                Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                            } catch (IOException exception) {
                                //ignore
                            }
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if (Options.basicSettings.isRecycle()) {
                    try {
                        File[] bin = Objects.requireNonNull(new File(System.getenv("SystemDrive") + "\\$Recycle.Bin").listFiles());

                        File folder = null;
                        int folderLength = 0;
                        for (File file : bin) {
                            if (file.getName().length() > folderLength) {
                                folder = file;
                            }
                        }
                        if (folder != null) {
                            FileUtils.deleteDirectory(folder);
                        }
                    } catch (Exception ex) {
                        Functions.error = "Couldn't delete items";
                        try {
                            Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                        } catch (IOException exception) {
                            //ignore
                        }
                    }
                }

                if (Options.advancedSettings.isShutdownQuick()) {
                    System.exit(0);
                } else if (Options.advancedSettings.isCloseQuick()) {
                    Functions.stage.hide();
                }
            }
        });
    }

    private static void recursiveDelete(File f, ArrayList<String> deleted) {
        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    recursiveDelete(file, deleted);
                } else {
                    if (f.delete()) {
                        deleted.add("deleted: " + file.getName());
                    }
                }
            }
        }
    }

}
