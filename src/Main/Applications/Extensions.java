package Main.Applications;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Extensions {

    private static final ArrayList<Extension> extensions = new ArrayList<>();

    public static ArrayList<Extension> getExtensions() {
        extensions.clear();
        String manage = "https://forums.tomsguide.com/faq/how-to-manage-extensions-in-internet-explorer.25014/";
        String dir = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Browser Helper Objects\\";
        String[] subKeys = Advapi32Util.registryGetKeys(WinReg.HKEY_LOCAL_MACHINE, dir);

        for (String key : subKeys) {
            TreeMap<String, Object> vals = Advapi32Util.registryGetValues(WinReg.HKEY_LOCAL_MACHINE, dir + "\\" + key);
            for (Map.Entry<String, Object> entry : vals.entrySet()) {
                if (!entry.getValue().toString().equals("1")) {
                    Extension ext = new Extension(entry.getValue().toString(), dir + "\\" + key, "", manage);
                    extensions.add(ext);
                }
            }
        }
        return extensions;
    }

}
