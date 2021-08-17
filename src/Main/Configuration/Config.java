package Main.Configuration;

import java.io.File;
import java.util.ArrayList;

public class Config {

    private ArrayList<String> include = new ArrayList<>();
    private ArrayList<String> exclude = new ArrayList<>();

    public void setIncludeItems(ArrayList<String> includeItems) {
        this.include = includeItems;
    }

    public ArrayList<String> getIncludeItems() {
        return include;
    }

    public void setExcludeItems(ArrayList<String> excludeItems) {
        this.exclude = excludeItems;
    }

    public java.util.ArrayList<String> getExcludeItems() {
        return exclude;
    }

}
