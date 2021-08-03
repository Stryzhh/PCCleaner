package Main.Elements;

import Main.Configuration.Junk;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;

public class Custom {

    public static AnchorPane applicationPanel;
    public static AnchorPane windowsPanel;
    public static ListView<String> list;
    public static JFXButton windows;
    public static JFXButton applications;
    public static JFXButton analyze;
    public static JFXButton clean;
    public static boolean chrome = false;
    public static boolean spotify = false;
    public static boolean steam = false;

    public static AnchorPane explorerPanel;
    public static JFXButton selectExplorer;
    public static JFXCheckBox explorerTemp;
    public static JFXCheckBox explorerHistory;
    public static JFXCheckBox explorerCookies;
    public static AnchorPane edgePanel;
    public static JFXButton selectEdge;
    public static JFXCheckBox edgeCache;
    public static JFXCheckBox edgeHistory;
    public static JFXCheckBox edgeCookies;
    public static JFXCheckBox edgeSession;
    public static AnchorPane systemPanel;
    public static JFXButton selectSystem;
    public static JFXCheckBox systemBin;
    public static JFXCheckBox systemTemp;
    public static JFXCheckBox systemClipboard;
    public static JFXCheckBox systemDump;
    public static JFXCheckBox systemLog;
    public static JFXCheckBox systemReport;
    public static JFXCheckBox systemStartMenu;
    public static JFXCheckBox systemDesktop;
    public static AnchorPane chromePanel;
    public static JFXButton selectChrome;
    public static JFXCheckBox chromeCache;
    public static JFXCheckBox chromeHistory;
    public static JFXCheckBox chromeCookies;
    public static JFXCheckBox chromeSession;
    public static AnchorPane spotifyPanel;
    public static JFXButton selectSpotify;
    public static JFXCheckBox spotifyCache;
    public static JFXCheckBox spotifyMusic;
    public static JFXCheckBox spotifyOffline;
    public static AnchorPane steamPanel;
    public static JFXButton selectSteam;
    public static JFXCheckBox steamDump;

    private static boolean IE = false;
    private static boolean ED = false;
    private static boolean SYS = false;
    private static boolean CH = false;
    private static boolean SP = false;
    private static boolean ST = false;

    private static boolean chromeSelected = false;
    private static boolean spotifySelected = false;
    private static boolean steamSelected = false;

    public static void load() {
        windowsPanel.toFront();
        chromePanel.setDisable(!chrome);
        spotifyPanel.setDisable(!spotify);
        steamPanel.setDisable(!steam);

        selectExplorer.setOnAction(e -> {
            IE = !IE;
            explorerCookies.setSelected(IE);
            explorerHistory.setSelected(IE);
            explorerTemp.setSelected(IE);
        });
        selectEdge.setOnAction(e -> {
            ED = !ED;
            edgeCache.setSelected(ED);
            edgeCookies.setSelected(ED);
            edgeHistory.setSelected(ED);
            edgeSession.setSelected(ED);
        });
        selectSystem.setOnAction(e -> {
            SYS = !SYS;
            systemDesktop.setSelected(SYS);
            systemStartMenu.setSelected(SYS);
            systemReport.setSelected(SYS);
            systemLog.setSelected(SYS);
            systemDump.setSelected(SYS);
            systemClipboard.setSelected(SYS);
            systemTemp.setSelected(SYS);
            systemBin.setSelected(SYS);
        });
        selectChrome.setOnAction(e -> {
            CH = !CH;
            chromeSession.setSelected(CH);
            chromeCookies.setSelected(CH);
            chromeHistory.setSelected(CH);
            chromeCache.setSelected(CH);
        });
        selectSpotify.setOnAction(e -> {
            SP = !SP;
            spotifyOffline.setSelected(SP);
            spotifyMusic.setSelected(SP);
            spotifyCache.setSelected(SP);
        });
        selectSteam.setOnAction(e -> {
            ST = !ST;
            steamDump.setSelected(ST);
        });
        analyze.setOnAction(e -> analyzeFiles());
        clean.setOnAction(e -> {
            try {
                cleanFiles();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        windows.setOnAction(e -> windowsPanel.toFront());
        applications.setOnAction(e -> applicationPanel.toFront());
    }

    private static void cleanFiles() throws IOException {
        list.getItems().clear();

        if (systemClipboard.isSelected()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
        }
        if (systemBin.isSelected()) {
            try {
                File[] bin = Objects.requireNonNull(new File(System.getenv("SystemDrive") + "\\$Recycle.Bin").listFiles());

                File folder = null;
                int folderLength = 0;
                for (File file : bin) {
                    if (file.getName().length() > folderLength) {
                        folder = file;
                    }
                }
                assert folder != null;
                FileUtils.deleteDirectory(folder);
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        if (chromeSelected || spotifySelected || steamSelected) {
            try {
                String line;
                Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

                while ((line = input.readLine()) != null) {
                    if (line.contains("chrome.exe"))
                        Runtime.getRuntime().exec("taskkill /im chrome.exe");
                    if (line.contains("Spotify.exe"))
                        Runtime.getRuntime().exec("taskkill /im Spotify.exe");
                    if (line.contains("steam.exe"))
                        Runtime.getRuntime().exec("taskkill /im steam.exe");
                }
                input.close();
            } catch (Exception exception) {
                //ignore
            }
        }

        //cleans files
        ArrayList<File> allFiles = getFiles();
        for (File file : allFiles) {
            if (file.exists()) {
                File[] files = file.listFiles();
                assert files != null;
                for (File f : files) {
                    if (f.isDirectory()) {
                        recursiveDelete(f);
                    } else {
                        if (f.delete()) {
                            Platform.runLater(() -> list.getItems().add("deleted: " + f.getAbsolutePath()));
                        }
                    }
                }
            }
        }
    }

    private static void recursiveDelete(File f) {
        File[] files = f.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                recursiveDelete(file);
            } else {
                if (f.delete()) {
                    Platform.runLater(() -> list.getItems().add("deleted: " + f.getAbsolutePath()));
                }
            }
        }
    }

    private static void analyzeFiles() {
        list.getItems().clear();
        try {
            for (File file : getFiles()) {
                if (file.isDirectory()) {
                    Platform.runLater(() -> list.getItems().add("Folder to be cleared: " + file.getAbsolutePath()));
                } else {
                    Platform.runLater(() -> list.getItems().add("File to be removed: " + file.getAbsolutePath()));
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    private static ArrayList<File> getFiles() {
        ArrayList<File> files = new ArrayList<>();

        if (explorerTemp.isSelected())
            files.addAll(Arrays.asList(Junk.explorerTemp));
        if (explorerHistory.isSelected())
            files.addAll(Arrays.asList(Junk.explorerHistory));
        if (explorerCookies.isSelected())
            files.addAll(Arrays.asList(Junk.explorerCookies));
        if (edgeCache.isSelected())
            files.addAll(Arrays.asList(Junk.edgeCache));
        if (edgeHistory.isSelected())
            files.addAll(Arrays.asList(Junk.edgeHistory));
        if (edgeCookies.isSelected())
            files.addAll(Arrays.asList(Junk.edgeCookies));
        if (edgeSession.isSelected())
            files.addAll(Arrays.asList(Junk.edgeSession));
        if (systemTemp.isSelected())
            files.addAll(Arrays.asList(Junk.systemTemp));
        if (systemDump.isSelected())
            files.addAll(Arrays.asList(Junk.systemDump));
        if (systemLog.isSelected())
            files.addAll(Arrays.asList(Junk.systemLog));
        if (systemReport.isSelected())
            files.addAll(Arrays.asList(Junk.systemReport));
        if (systemStartMenu.isSelected())
            files.addAll(Arrays.asList(Junk.systemStartMenu));
        if (systemDesktop.isSelected())
            files.addAll(Arrays.asList(Junk.systemDesktop));
        if (chromeCache.isSelected()) {
            chromeSelected = true;
            files.addAll(Arrays.asList(Junk.chromeCache));
        }
        if (chromeHistory.isSelected()) {
            chromeSelected = true;
            files.addAll(Arrays.asList(Junk.chromeHistory));
        }
        if (chromeCookies.isSelected()) {
            chromeSelected = true;
            files.addAll(Arrays.asList(Junk.chromeCookies));
        }
        if (chromeSession.isSelected()) {
            chromeSelected = true;
            files.addAll(Arrays.asList(Junk.chromeSession));
        }
        if (spotifyCache.isSelected()) {
            spotifySelected = true;
            files.addAll(Arrays.asList(Junk.spotifyCache));
        }
        if (spotifyMusic.isSelected()) {
            spotifySelected = true;
            files.addAll(Arrays.asList(Junk.spotifyMusic));
        }
        if (spotifyOffline.isSelected()) {
            spotifySelected = true;
            files.addAll(Arrays.asList(Junk.spotifyOffline));
        }
        if (steamDump.isSelected()) {
            steamSelected = true;
            files.addAll(Arrays.asList(Junk.steamDump));
        }

        return files;
    }

}
