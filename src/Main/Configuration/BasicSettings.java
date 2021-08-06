package Main.Configuration;

public class BasicSettings {

    private boolean startup;
    private boolean recycle;

    public BasicSettings(boolean startup, boolean recycle) {
        super();
        this.startup = startup;
        this.recycle = recycle;
    }

    public void setStartup(boolean value) {
        startup = value;
    }

    public boolean isStartup() {
        return startup;
    }

    public void setRecycle(boolean value) {
        recycle = value;
    }

    public boolean isRecycle() {
        return recycle;
    }

}
