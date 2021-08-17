package Main.Elements;

import Main.Configuration.AdvancedSettings;
import Main.Configuration.BasicSettings;
import Main.Configuration.Config;
import Main.Default.Functions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.jimmc.jshortcut.JShellLink;

public class Options extends Component {

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

    public static Config config = new Config();
    public static BasicSettings basicSettings;
    public static AdvancedSettings advancedSettings;

    public static void load() {
        aboutPane.toFront();
        loadSettings();
        loadLists();
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
            basicSettings = gson.fromJson(reader, BasicSettings.class);

            startup.setSelected(basicSettings.isStartup());
            recycle.setSelected(basicSettings.isRecycle());
            reader.close();
        } catch (IOException e) {
            Functions.error = "Couldn't basic load settings";
            try {
                Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
            } catch (IOException exception) {
                //ignore
            }
        }

        startup.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            basicSettings.setStartup(startup.isSelected());

            amendBasicSettings();
            try {
                setStartup(startup.isSelected());
            } catch (IOException e) {
                Functions.error = "Couldn't create/delete startup shortcut";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
        recycle.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            basicSettings.setRecycle(recycle.isSelected());
            amendBasicSettings();
        });
    }

    public static void include() {
        includeRemove.disableProperty().bind(includeList.getSelectionModel().selectedItemProperty().isNull());

        includeAddFile.setOnAction(e -> Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            File result = fileChooser.showOpenDialog(null);

            if (result != null) {
                if (config.getIncludeItems() != null) {
                    if (!config.getIncludeItems().contains(result.toString())) {
                        config.getIncludeItems().add(result.toString());
                        includeList.getItems().add(result);
                    }
                    config.getExcludeItems().remove(result.toString());
                }
            }
            try {
                updateJSON();
                loadSettings();
                loadLists();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        }));
        includeAddFolder.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File result = chooser.showDialog(null);

            if (result != null) {
                if (config.getIncludeItems() != null) {
                    if (!config.getIncludeItems().contains(result.toString())) {
                        config.getIncludeItems().add(result.toString());
                        includeList.getItems().add(result);
                    }
                    config.getExcludeItems().remove(result.toString());
                }
            }
            try {
                updateJSON();
                loadSettings();
                loadLists();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
        includeRemove.setOnAction(e -> {
            File item = includeList.getSelectionModel().getSelectedItem();
            if (item != null) {
                includeList.getItems().remove(item);
                config.getIncludeItems().remove(item.toString());
            }
            try {
                updateJSON();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
    }

    public static void exclude() {
        excludeRemove.disableProperty().bind(excludeList.getSelectionModel().selectedItemProperty().isNull());

        excludeAddFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File result = fileChooser.showOpenDialog(null);

            if (result != null) {
                if (config.getExcludeItems() != null) {
                    if (!config.getExcludeItems().contains(result.toString())) {
                        config.getExcludeItems().add(result.toString());
                        excludeList.getItems().add(result);
                    }
                    config.getIncludeItems().remove(result.toString());
                }
            }
            try {
                updateJSON();
                loadSettings();
                loadLists();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
        excludeAddFolder.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File result = chooser.showDialog(null);

            if (result != null) {
                if (config.getExcludeItems() != null) {
                    if (!config.getExcludeItems().contains(result.toString())) {
                        config.getExcludeItems().add(result.toString());
                        excludeList.getItems().add(result);
                    }
                    config.getIncludeItems().remove(result.toString());
                }
            }
            try {
                updateJSON();
                loadSettings();
                loadLists();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
        excludeRemove.setOnAction(e -> {
            File item = excludeList.getSelectionModel().getSelectedItem();
            if (item != null) {
                excludeList.getItems().remove(item);
                config.getExcludeItems().remove(item.toString());
            }
            try {
                updateJSON();
            } catch (IOException ioException) {
                Functions.error = "Couldn't update changes";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        });
    }

    private static void setStartup(boolean selected) throws IOException {
        File folder = new File(System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
        File batch = new File("PCCleaner.bat");

        if (selected) {
            FileOutputStream output = new FileOutputStream(batch);
            DataOutputStream input = new DataOutputStream(output);
            input.writeBytes("PCCleaner.exe");
            input.close();
            output.close();

            JShellLink link = new JShellLink();
            String filePath = JShellLink.getDirectory("") + batch.getAbsolutePath();
            link.setFolder(folder.getAbsolutePath());
            link.setName(batch.getName());
            link.setPath(filePath);
            link.save();
        } else {
            File startupFile = new File(System.getProperty("user.home") +
                    "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/PCCleaner.bat.lnk");
            if (!startupFile.delete() || !batch.delete()) {
                Functions.error = "Couldn't delete startup file";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        }
    }

    public static void advanced() {
        restoreAdvanced.setOnAction(e -> {
            produceList.setSelected(true);
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
            advancedSettings = gson.fromJson(reader, AdvancedSettings.class);

            produceList.setSelected(advancedSettings.isProduce());
            hideWarnings.setSelected(advancedSettings.isHide());
            closeQuick.setSelected(advancedSettings.isCloseQuick());
            shutdownQuick.setSelected(advancedSettings.isShutdownQuick());
            closeCustom.setSelected(advancedSettings.isCloseCustom());
            shutdownCustom.setSelected(advancedSettings.isShutdownCustom());
            reader.close();
        } catch (IOException e) {
            Functions.error = "Couldn't load advanced settings";
            try {
                Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
            } catch (IOException exception) {
                //ignore
            }
        }

        produceList.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setProduce(produceList.isSelected());
            amendAdvancedSettings();
        });
        hideWarnings.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setHide(hideWarnings.isSelected());
            amendAdvancedSettings();
        });
        closeQuick.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setCloseQuick(closeQuick.isSelected());
            amendAdvancedSettings();
        });
        shutdownQuick.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setShutdownQuick(shutdownQuick.isSelected());
            amendAdvancedSettings();
        });
        closeCustom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setCloseCustom(closeCustom.isSelected());
            amendAdvancedSettings();
        });
        shutdownCustom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            advancedSettings.setShutdownCustom(shutdownCustom.isSelected());
            amendAdvancedSettings();
        });
    }

    private static void updateJSON() throws IOException {
        FileWriter fw = new FileWriter("config/files.json");
        Gson g = new GsonBuilder().setPrettyPrinting().create();

        fw.write(g.toJson(config));
        fw.close();
    }

    private static void loadSettings() {
        try {
            config = new Gson().fromJson(new FileReader("config/files.json"), Config.class);
        } catch (IOException e) {
            //ignore
        }
    }

    private static void loadLists() {
        includeList.getItems().clear();
        excludeList.getItems().clear();

        for (String file : config.getIncludeItems()) {
            includeList.getItems().add(new File(file));
        }
        for (String file : config.getExcludeItems()) {
            excludeList.getItems().add(new File(file));
        }
    }

    private static void amendBasicSettings() {
        try {
            FileWriter writer = new FileWriter("config/settings.json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(basicSettings));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }

    private static void amendAdvancedSettings() {
        try {
            FileWriter writer = new FileWriter("config/advanced.json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(advancedSettings));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }

}
