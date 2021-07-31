package Main.Configuration;

import java.io.File;
import java.util.ArrayList;

public class Config {

    private ArrayList<File> includeItems = new ArrayList<>();
    private ArrayList<File> excludeItems = new ArrayList<>();

    public void setIncludeItems(ArrayList<File> includeItems) {
        this.includeItems = includeItems;
    }

    public ArrayList<File> getIncludeItems() {
        return includeItems;
    }

    public void setExcludeItems(ArrayList<File> excludeItems) {
        this.excludeItems = excludeItems;
    }

    public ArrayList<File> getExcludeItems() {
        return excludeItems;
    }
}
