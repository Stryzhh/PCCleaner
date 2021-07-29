package Main.Elements;

import Main.Configuration.AdvancedSettings;
import Main.Configuration.BasicSettings;
import Main.Configuration.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javax.swing.*;

public class Options {

    //main ui elements
    public static AnchorPane panel;
    public static AnchorPane settingsPane;
    public static AnchorPane includePane;
    public static AnchorPane excludePane;
    public static AnchorPane advancedPane;
    public static AnchorPane aboutPane;
    public static JFXButton settings;
    public static JFXButton include;
    public static JFXButton exclude;
    public static JFXButton advanced;
    public static JFXButton about;

    //settings elements
    public static JFXCheckBox startup;
    public static JFXCheckBox recycle;

    //include elements
    public static ListView<File> includeList;
    public static JFXButton includeAddFile;
    public static JFXButton includeAddFolder;
    public static JFXButton includeRemove;

    //exclude elements
    public static ListView<File> excludeList;
    public static JFXButton excludeAddFile;
    public static JFXButton excludeAddFolder;
    public static JFXButton excludeRemove;

    //advanced settings elements
    public static JFXCheckBox produceList;
    public static JFXCheckBox hideWarnings;
    public static JFXCheckBox closeQuick;
    public static JFXCheckBox shutdownQuick;
    public static JFXCheckBox closeCustom;
    public static JFXCheckBox shutdownCustom;
    public static JFXButton restoreAdvanced;

    public static void load() {
        settings();
        include();
        exclude();
        advanced();
    }

    public static void settings() {
        Gson gson = new Gson();
        File file = new File("config/settings.json");
        try {
            Reader reader = new FileReader(file);
            BasicSettings settings = gson.fromJson(reader, BasicSettings.class);

            startup.setSelected(settings.isStartup());
            recycle.setSelected(settings.isRecycle());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startup.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendBasicSettings());
        recycle.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendBasicSettings());
    }

    public static void include() {
        includeRemove.disableProperty().bind(includeList.getSelectionModel().selectedItemProperty().isNull());

        includeAddFile.setOnAction(e-> {
            FileDialog dialog = new FileDialog((Dialog) null, "Select file to include");
            dialog.setMode(FileDialog.LOAD);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);

            if (dialog.getFile() != null) {
                File file = new File(dialog.getFile());
                Platform.runLater(() -> includeList.getItems().add(file));
                System.out.println(file);
                Config.includeFiles.add(file);
            }
        });
        includeAddFolder.setOnAction(e-> {

        });
        includeRemove.setOnAction(e-> {
            File item = includeList.getSelectionModel().getSelectedItem();
            if (item != null) {
                if (Config.includeFiles.contains(item)) {
                    Config.includeFiles.remove(item);
                } else Config.includeFolders.remove(item);
            }
        });
    }

    public static void exclude() {
        excludeRemove.disableProperty().bind(excludeList.getSelectionModel().selectedItemProperty().isNull());

        excludeAddFile.setOnAction(e-> {
            FileDialog dialog = new FileDialog((Dialog) null, "Select file to exclude");
            dialog.setMode(FileDialog.LOAD);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);

            if (dialog.getFile() != null) {
                File file = new File(dialog.getFile());
                Config.excludeFiles.add(file);
                excludeList.getItems().add(file);
            }
        });
        excludeAddFolder.setOnAction(e-> {

        });
        excludeRemove.setOnAction(e-> {
            File item = excludeList.getSelectionModel().getSelectedItem();
            if (item != null) {
                if (Config.excludeFiles.contains(item)) {
                    Config.excludeFiles.remove(item);
                } else Config.excludeFolders.remove(item);
            }
        });
    }

    public static void advanced() {
        restoreAdvanced.setOnAction(e-> {
            produceList.setSelected(false);
            hideWarnings.setSelected(false);
            closeQuick.setSelected(false);
            shutdownQuick.setSelected(false);
            closeCustom.setSelected(false);
            shutdownCustom.setSelected(false);
            amendAdvancedSettings();
        });

        Gson gson = new Gson();
        File file = new File("config/advanced.json");
        try {
            Reader reader = new FileReader(file);
            AdvancedSettings settings = gson.fromJson(reader, AdvancedSettings.class);

            produceList.setSelected(settings.isProduce());
            hideWarnings.setSelected(settings.isHide());
            closeQuick.setSelected(settings.isCloseQuick());
            shutdownQuick.setSelected(settings.isShutdownQuick());
            closeCustom.setSelected(settings.isCloseCustom());
            shutdownCustom.setSelected(settings.isShutdownCustom());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        produceList.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
        hideWarnings.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
        closeQuick.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
        shutdownQuick.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
        closeCustom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
        shutdownCustom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendAdvancedSettings());
    }

    private static void amendBasicSettings() {
        try {
            FileWriter writer = new FileWriter("config/settings.json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(new BasicSettings(startup.isSelected(), recycle.isSelected())));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void amendAdvancedSettings() {
        try {
            FileWriter writer = new FileWriter("config/advanced.json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(new AdvancedSettings(produceList.isSelected(), hideWarnings.isSelected(), closeQuick.isSelected(),
                    shutdownQuick.isSelected(), closeCustom.isSelected(), shutdownCustom.isSelected())));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
