package Main.Elements;

import com.jfoenix.controls.JFXButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.ImageView;

public class Drivers {

    public static ImageView icon;
    public static JFXButton open;

    public static void load() {
        open.setOnAction(e -> {
            try {
                Desktop.getDesktop().open(new File(System.getenv("WINDIR") + "\\system32\\devmgmt.msc"));
            } catch (IOException io) {
                io.printStackTrace();
            }
        });
    }

}
