package Main.Configuration;

import java.io.File;
import java.util.ArrayList;

public class Config {

    private final ArrayList<File> includeItems = new ArrayList<>();
    private final ArrayList<File> excludeItems = new ArrayList<>();

    public ArrayList<File> getIncludeItems() {
        return includeItems;
    }

    public java.util.ArrayList<File> getExcludeItems() {
        return excludeItems;
    }


}
