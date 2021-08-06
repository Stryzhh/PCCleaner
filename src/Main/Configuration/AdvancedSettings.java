package Main.Configuration;

public class AdvancedSettings {

    private boolean produce;
    private boolean hide;
    private boolean closeQuick;
    private boolean shutdownQuick;
    private boolean closeCustom;
    private boolean shutdownCustom;

    public AdvancedSettings(boolean produce, boolean hide, boolean closeQuick, boolean shutdownQuick, boolean closeCustom, boolean shutdownCustom) {
        super();
        this.produce = produce;
        this.hide = hide;
        this.closeQuick = closeQuick;
        this.shutdownQuick = shutdownQuick;
        this.closeCustom = closeCustom;
        this.shutdownCustom = shutdownCustom;
    }

    public void setProduce(boolean value) {
        produce = value;
    }

    public boolean isProduce() {
        return produce;
    }

    public void setHide(boolean value) {
        hide = value;
    }

    public boolean isHide() {
        return hide;
    }

    public void setCloseQuick(boolean value) {
        closeQuick = value;
    }

    public boolean isCloseQuick() {
        return closeQuick;
    }

    public void setShutdownQuick(boolean value) {
        shutdownQuick = value;
    }

    public boolean isShutdownQuick() {
        return shutdownQuick;
    }

    public void setCloseCustom(boolean value) {
        closeCustom = value;
    }

    public boolean isCloseCustom() {
        return closeCustom;
    }

    public void setShutdownCustom(boolean value) {
        shutdownCustom = value;
    }

    public boolean isShutdownCustom() {
        return shutdownCustom;
    }

}
