package Main.Elements;

import Main.Configuration.AdvancedSettings;
import Main.Configuration.BasicSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;

public class Options {

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
    public static JFXCheckBox startup;
    public static JFXCheckBox recycle;
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

    }

    public static void exclude() {

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
