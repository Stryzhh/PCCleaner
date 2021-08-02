package Main.Applications;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class StartUpApps {

    private static final ArrayList<Application> applications = new ArrayList<>();

    public static ArrayList<Application> getInstalledApps() {
        applications.clear();
        getPrograms(WinReg.HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run");
        getPrograms(WinReg.HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\RunOnce");
        getPrograms(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run");
        getPrograms(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\RunOnce");

        return applications;
    }

    private static void getPrograms(WinReg.HKEY user, String dir) {
        TreeMap<String, Object> vals = Advapi32Util.registryGetValues(user, dir);
        for (Map.Entry<String, Object> entry : vals.entrySet()) {
            Application app = new Application(entry.getKey(), entry.getValue(), user, dir);
            applications.add(app);
        }
    }

}
