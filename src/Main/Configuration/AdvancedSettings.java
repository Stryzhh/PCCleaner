package Main.Configuration;

public class AdvancedSettings {

    private final boolean produce;
    private final boolean hide;
    private final boolean closeQuick;
    private final boolean shutdownQuick;
    private final boolean closeCustom;
    private final boolean shutdownCustom;

    public AdvancedSettings(boolean produce, boolean hide, boolean closeQuick, boolean shutdownQuick, boolean closeCustom, boolean shutdownCustom) {
        super();
        this.produce = produce;
        this.hide = hide;
        this.closeQuick = closeQuick;
        this.shutdownQuick = shutdownQuick;
        this.closeCustom = closeCustom;
        this.shutdownCustom = shutdownCustom;
    }

    public boolean isProduce() {
        return produce;
    }

    public boolean isHide() {
        return hide;
    }

    public boolean isCloseQuick() {
        return closeQuick;
    }

    public boolean isShutdownQuick() {
        return shutdownQuick;
    }

    public boolean isCloseCustom() {
        return closeCustom;
    }

    public boolean isShutdownCustom() {
        return shutdownCustom;
    }

}
