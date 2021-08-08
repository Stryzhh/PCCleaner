package Main.Objects;

import javafx.beans.property.SimpleStringProperty;

public class Extension {

    private final SimpleStringProperty program;
    private final SimpleStringProperty file;
    private final SimpleStringProperty version;
    private final String URL;

    public Extension(String name, String file, String version, String URL) {
        this.program = new SimpleStringProperty(name);
        this.file = new SimpleStringProperty(file);
        this.version = new SimpleStringProperty(version);
        this.URL = URL;
    }

    public String getProgram() {
        return program.get();
    }

    public void setFile(String file) {
        this.file.set(file);
    }

    public String getFile() {
        return file.get();
    }

    public String getVersion() {
        return version.get();
    }

    public String getURL() {
        return URL;
    }

}
