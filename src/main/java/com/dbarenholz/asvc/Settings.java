package com.dbarenholz.asvc;

/**
 * Settings class.
 *
 * Contains all (global, immutable) application settings.
 *
 * TODO: Change all items to 'public static final Object' order.
 */
public class Settings {
    // === Non editable settings === //
    final static double minWidth = 250.0;
    final static double minHeight = 250.0;
    final static double prefWidth = 500.0;
    final static double prefHeight = 500.0;

    final static String applicationTitle = "ASVC";

    final static String FS = System.getProperty("file.separator");
    final static String LS = System.getProperty("line.separator");
    final static String homeDirectory = System.getProperty("user.home");

    // === User editable settings === //
    final static String iniPath = homeDirectory + FS + applicationTitle + FS + "settings.ini";
    final static String applicationPath = homeDirectory + FS + applicationTitle;
    public static final String cachePath = applicationPath + FS + "cache";

    // === exporting settings === //
    final static String ankiProfileName = "User 1";
    final static String mediaPath = System.getenv("APPDATA") + FS + "Anki2" + FS + ankiProfileName + FS + "collection.media";
    public static String delim = ",";

    // === scraping settings === //
    public static final String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";
    public static final int timeout = 10000;
    public static final int replaceAfter = 10000; // 10ms
    public static final String jisho = "https://jisho.org/";
    public static final String forvo = "https://forvo.com/";
    public static final String tatoeba = "https://tatoeba.org/";

    // TODO: Implement method to write all settings to ini file
    /**
     * Writes settings to .ini file.
     *
     * @return {@code true} if success, {@code false} otherwise
     */
    static boolean writeToIni() {
        return false;
    }

    // TODO: Implement method to read all settings from ini file
    /**
     * Reads settings from .ini file.
     *
     * @return {@code true} if success, {@code false} otherwise
     */
    static boolean readFromIni() {
        return false;
    }

    /**
     * Creates string representation of paths and user inputted items.
     *
     * @return String of some settings items
     */
    static String stringify() {
        String ret = "\n";
        ret += "--> applicationTitle: '" + applicationTitle + "'\n";
        ret += "--> homeDirectory: '" + homeDirectory + "'\n";
        ret += "--> iniPath: '" + iniPath + "'\n";
        ret += "--> applicationPath: '" + applicationPath + "'\n";
        ret += "--> cachePath: '" + cachePath + "'\n";
        ret += "--> ankiProfileName: '" + ankiProfileName + "'\n";
        ret += "--> mediaPath: '" + mediaPath + "'";
        return ret;
    }
}
