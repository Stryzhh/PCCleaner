package Main.Configuration;

public class BasicSettings {

    private final boolean startup;
    private final boolean recycle;

    public BasicSettings(boolean startup, boolean recycle) {
        super();
        this.startup = startup;
        this.recycle = recycle;
    }

    public boolean isStartup() {
        return startup;
    }

    public boolean isRecycle() {
        return recycle;
    }

}
