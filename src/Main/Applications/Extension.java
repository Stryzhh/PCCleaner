package Main.Applications;

import javafx.beans.property.SimpleStringProperty;

public class Extension {

    private final SimpleStringProperty program;
    private final SimpleStringProperty file;
    private final SimpleStringProperty version;
    private String URL;

    public Extension(String name, String file, String version, String URL) {
        this.program = new SimpleStringProperty(name);
        this.file = new SimpleStringProperty(file);
        this.version = new SimpleStringProperty(version);
        this.URL = URL;
    }

    public void setProgram(String name) {
        this.program.set(name);
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

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getVersion() {
        return version.get();
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

}
