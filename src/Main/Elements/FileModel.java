package Main.Elements;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileModel {

    private final SimpleStringProperty name;
    private final SimpleStringProperty path;
    private final SimpleStringProperty type;

    public FileModel(String name, String path, String type) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
        this.type = new SimpleStringProperty(type);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getPath() {
        return path.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getType() {
        return type.get();
    }

}
