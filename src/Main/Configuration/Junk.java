package Main.Configuration;

import java.io.File;

public class Junk {

    public static String data = System.getProperty("user.home") + "\\";
    public static String files = System.getenv("ProgramFiles(X86)") + "\\";
    public static String windows = System.getenv("WINDIR") + "\\";
    public static String program = System.getenv("ProgramData") + "\\";

    public static File[] quickClean = {new File(windows + "Temp"), new File(data + "AppData\\Local\\Temp"),
            new File(windows + "Prefetch")};

    public static File[] edgeCache = {new File("AppData\\Local\\Microsoft\\Edge\\User Data\\GrShaderCache\\GPUCache"),
            new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\ShaderCache\\GPUCache"),
            new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Cache"),
            new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Service Worker\\CacheStorage")};
    public static File[] edgeHistory = {new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Service Worker\\Database")};
    public static File[] edgeCookies = {new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\IndexedDB")};
    public static File[] edgeSession = {new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Session Storage"),
            new File(data + "AppData\\Local\\Microsoft\\Edge\\User Data\\Default\\Sessions")};

    public static File[] explorerTemp = {new File(data + "AppData\\Local\\Microsoft\\Windows\\INetCache\\IE"),
            new File(data + "AppData\\Local\\Microsoft\\Windows\\INetCache\\Low\\IE"),};
    public static File[] explorerHistory = {new File(data + "AppData\\Local\\Microsoft\\Windows\\INetCache\\Virtualized\\C\\ProgramData\\NVIDIA Corporation\\NV_Cache"),
            new File(data + "AppData\\Local\\Microsoft\\Internet Explorer\\Recovery\\Last Active")};
    public static File[] explorerCookies = {new File(data + "AppData\\LocalLow\\Microsoft\\Internet Explorer\\DOMStore")};

    public static File[] systemTemp = {new File(windows + "Temp"), new File(data + "AppData\\Local\\Temp")};
    public static File[] systemDump = {new File(data + "AppData\\Local\\CrashDumps")};
    public static File[] systemLog = {new File(data + "AppData\\Local\\Microsoft\\CLR_v4.0\\UsageLogs"),
            new File(windows + "SoftwareDistribution\\DataStore\\Logs"),
            new File(data + "AppData\\Local\\Microsoft\\Windows\\WebCache")};
    public static File[] systemReport = {new File(program + "Microsoft\\Windows\\WER\\ReportArchive")};
    public static File[] systemStartMenu = {new File(program + "Microsoft\\Windows\\Start Menu\\Programs")};
    public static File[] systemDesktop = {new File(data + "Desktop")};

    public static File[] chromeCache = {new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Service Worker\\CacheStorage"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Service Worker\\ScriptCache"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\GrShaderCache\\GPUCache"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\ShaderCache\\GPUCache"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\GPUCache")};
    public static File[] chromeHistory = {new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Service Worker\\Database")};
    public static File[] chromeCookies = {new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\IndexedDB")};
    public static File[] chromeSession = {new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Session Storage"),
            new File(data + "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Sessions")};

    public static File[] spotifyCache = {new File(data + "AppData\\Local\\Spotify\\Data"),
            new File(data + "AppData\\Local\\Spotify\\Browser\\GPUCache")};
    public static File[] spotifyMusic = {new File(data + "AppData\\Local\\Spotify\\Browser\\Cache")};
    public static File[] spotifyOffline = {new File(data + "AppData\\Local\\Spotify\\Storage")};

    public static File[] steamDump = {new File(files + "Steam\\Dumps")};

}
