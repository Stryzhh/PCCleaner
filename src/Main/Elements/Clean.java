package Main.Elements;

import Main.Configuration.Config;
import Main.Configuration.Junk;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.scene.image.ImageView;

public class Clean {

    public static ImageView icon;
    public static JFXButton clean;
    public static Config cfg;

    public static void load() {
        clean.setOnAction(e -> {
            cfg = Options.config;

            ArrayList<String> deleted = new ArrayList<>();
            try {
                for (int i = 0; i < Junk.quickClean.length; i++) {
                    File[] f = Junk.quickClean[i].listFiles();

                    if (f != null) {
                        ArrayList<File> files = new ArrayList<>(Arrays.asList(f));
                        files.removeAll(cfg.getExcludeItems());

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

                for (File include : cfg.getIncludeItems()) {
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
                try {
                    PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\Clean.txt", "UTF-8");
                    for (String line : deleted) {
                        writer.println(line);
                    }
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
