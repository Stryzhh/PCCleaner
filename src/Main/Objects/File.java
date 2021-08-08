package Main.Objects;

import javafx.beans.property.SimpleStringProperty;

public class File {

    private final SimpleStringProperty name;
    private final SimpleStringProperty path;
    private final SimpleStringProperty type;

    public File(String name, String path, String type) {
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

    public String getPath() {
        return path.get();
    }

    public String getType() {
        return type.get();
    }

}
