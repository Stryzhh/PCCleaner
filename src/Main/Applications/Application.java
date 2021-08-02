package Main.Applications;

import com.sun.jna.platform.win32.WinReg;
import javafx.beans.property.SimpleStringProperty;

public class Application {

    private final SimpleStringProperty name;
    private final SimpleStringProperty path;
    private final WinReg.HKEY key;
    private final String dir;

    public Application(String name, Object path, WinReg.HKEY key, String dir) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty((String) path);
        this.key = key;
        this.dir = dir;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public String getPath() {
        return path.get();
    }

    public WinReg.HKEY getKey() {
        return key;
    }

    public String getDir() {
        return dir;
    }

}
