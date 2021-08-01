package Main.Applications;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;

public class Software {

    private final Set<String> keys = new HashSet<>();
    private final SimpleStringProperty key;
    private final SimpleStringProperty name;
    private final SimpleStringProperty publisher;
    private final SimpleStringProperty location;
    private final SimpleStringProperty date;
    private final SimpleStringProperty version;
    private final SimpleStringProperty icon;
    private final SimpleStringProperty uninstall;
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public Software(String key, String name, String publisher, String location, LocalDateTime date, String version, String icon, String uninstall) {
        addRegKey(key);

        this.key = new SimpleStringProperty(key);
        this.name = new SimpleStringProperty(name);
        this.publisher = new SimpleStringProperty(publisher);
        this.location = new SimpleStringProperty(notNull(location));
        this.date = new SimpleStringProperty(getDateString(date));
        this.version = new SimpleStringProperty(version);
        this.icon = new SimpleStringProperty(icon);
        this.uninstall = new SimpleStringProperty(uninstall);
    }

    private String notNull(String string) {
        if (string == null) {
            return "Unknown";
        } else if (string.equals("")) {
            return "Unknown";
        }
        return string;
    }

    private String getDateString(LocalDateTime date) {
        String full = date.format(formatter);
        String[] parts = full.split("T");
        return parts[0];
    }

    public void addRegKeys(Collection<String> keys) {
        this.keys.addAll(keys);
    }

    public void addRegKey(String key) {
        if (key == null) {
            return;
        }
        keys.add(key);
    }

    public Set<String> getRegKeys() {
        return keys;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getKey() {
        return key.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setPublisher(String path) {
        this.publisher.set(path);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setLocation(String type) {
        this.location.set(type);
    }

    public String getLocation() {
        return location.get();
    }

    public void setDate(LocalDateTime date) {
        this.date.set(getDateString(date));
    }

    public String getDate() {
        return date.get();
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getVersion() {
        return version.get();
    }

    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    public String getIcon() {
        return icon.get();
    }

    public void setUninstall(String uninstall) {
        this.uninstall.set(uninstall);
    }

    public String getUninstall() {
        return uninstall.get();
    }
}
