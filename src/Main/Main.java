package Main;

import Main.Default.Functions;
import Main.Default.ResizeHelper;
import java.awt.*;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Platform.setImplicitExit(false);
        Parent startWindow = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("Main/MainUI/main.fxml")));

        //adds tray icon
        PopupMenu popupMenu = new PopupMenu();
        ImageIcon logo = new ImageIcon("images\\PCCleaner.png");
        Image image = logo.getImage();

        SystemTray tray = SystemTray.getSystemTray();
        Image trayImage = image.getScaledInstance(tray.getTrayIconSize().width,
                tray.getTrayIconSize().height, java.awt.Image.SCALE_SMOOTH);
        TrayIcon trayIcon = new TrayIcon(trayImage, "PCCleaner", popupMenu);

        MenuItem open = new MenuItem("Open PCCleaner");
        MenuItem exit = new MenuItem("Close PCCleaner");

        open.addActionListener(e -> {
            Platform.runLater(window::show);
        });
        exit.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        popupMenu.add(open);
        popupMenu.add(exit);
        tray.add(trayIcon);

        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(startWindow, 1000, 700));
        window.setMinWidth(1000);
        window.setMinHeight(700);
        ResizeHelper.addResizeListener(window);
        window.getIcons().add(new javafx.scene.image.Image("PCCleaner.png"));
        window.show();
        Functions.stage = window;
    }

}
