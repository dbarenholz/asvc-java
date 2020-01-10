package com.dbarenholz.asvc;

import javafx.geometry.Dimension2D;
import javafx.util.Pair;

/**
 * Settings class.
 *
 * Contains all (global, immutable) application settings.
 */
public class Settings {
    // === Non editable settings === //
    // TODO: Set default application title
    final static String applicationTitle = "ASVC";
    final static double minWidth = 250.0;
    final static double minHeight = 250.0;

    // === User editable settings for ini === //
    final static double prefWidth = 500.0;
    final static double prefHeight = 500.0;

    // TODO: Set default path
    final static String iniPath = "path/to/file.ini";

    // TODO: Implement method to write all settings to ini file
    static boolean writeToIni() {
        return false;
    }

    static boolean readFromIni() {
        return false;
    }
}
