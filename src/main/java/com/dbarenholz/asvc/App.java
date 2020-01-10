package com.dbarenholz.asvc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ASVC Application class.
 *
 * TODO: Write class documentation.
 * TODO: Write package.java documentation.
 */
public class App extends Application {

    // === Variables === //
    private static final Logger logger = LogManager.getLogger();

    // === Helpers === //
    /**
     * Checks if there is a .ini file present for user settings.
     *
     * @return {@code true} if present, readable, and writeable. Return {@code false} otherwise.
     */
    private boolean settingsIniFilePresent() {
        logger.debug("Checking {} for ini settings", Settings.iniPath);
        File tempFileObject = new File(Settings.iniPath);
        // Check for existence, reading and writing
        if (!tempFileObject.exists()) {
            return false;
        } else if (!tempFileObject.canRead()) {
            logger.debug("File exists at previously logged path, but application cannot read.");
            return false;
        } else if (!tempFileObject.canWrite()){
            logger.debug("File exists at previously logged path, but application cannot write.");
            return false;
        }
        return true;
    }

    private Tab mainTab() {
        Tab tab = new Tab("ASVC");
        GridPane container = new GridPane();
        Label temp = new Label("ASVC Tab");

        container.add(temp, 0, 0);

        tab.setContent(container);
        return tab;
    }

    /**
     * Creates the about tab for the ASVC application.
     *
     * @return about tab
     */
    private Tab aboutTab() {
        Tab tab = new Tab("About");

        GridPane container = new GridPane();
        Label temp = new Label("About Tab");

        container.add(temp, 0, 0);

        tab.setContent(container);
        return tab;
    }

    /**
     * Creates the settings tab for the ASVC application
     *
     * @return settings tab
     */
    private Tab settingsTab() {
        Tab tab = new Tab("Settings");

        GridPane container = new GridPane();
        Button updateButton = new Button("Reload from .ini");
        Button saveButton = new Button("Save to .ini");

        updateButton.setOnAction(e -> {
            if (!Settings.readFromIni()) {
                logger.warn("Could not reload settings from ini.");
            }
        });

        saveButton.setOnAction(e -> {
            if (!Settings.writeToIni()) {
                logger.warn("Could not write settings to ini.");
            }
        });

        container.add(updateButton, 0, 0);
        container.add(saveButton, 1, 0);

        tab.setContent(container);
        return tab;
    }

    /**
     * Creates tab pane.
     *
     * @return tab pane
     */
    private TabPane buildTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);
        return tabPane;
    }

    // === Methods === //
    /**
     * Helper method to build the GUI.
     */
    private void createGUI(Stage stage) {
        logger.info("Build application GUI...");

        // Create list of all tabs of application
        List<Tab> tabs = new ArrayList<>();
        tabs.add(mainTab());
        tabs.add(settingsTab());
        tabs.add(aboutTab());

        // Add all created tabs to the root
        TabPane rootTabPane = buildTabPane();
        rootTabPane.getTabs().addAll(tabs);

        // Add root to the scene, and set the scene
        Scene scene = new Scene(rootTabPane, Settings.prefWidth, Settings.prefHeight);
        stage.setMinWidth(Settings.minWidth);
        stage.setMinHeight(Settings.minHeight);
        stage.setScene(scene);
        stage.setTitle(Settings.applicationTitle);
        stage.show();
    }

    /**
     * Helper method to initialise settings.
     */
    private void initialiseSettings() {
        logger.info("Initialise settings...");
        // Check if .ini file present
        if (!settingsIniFilePresent()) {
            // if not present -- set defaults and save to .ini
            logger.debug("Settings .ini file cannot be used. Creating one...");
            if (!Settings.writeToIni()) {
                logger.error("Writing settings to ini failed. Closing application...");
            }
        }
        // .ini is present (newly created, or was already present)
        logger.debug("Settings .ini can be used. Setting settings from file...");
        if (!Settings.readFromIni()) {
            logger.error("Reading settings from ini failed. Closing application...");
        }
    }

    /**
     * JavaFX {@code start} method.
     * Initialises application settings and starts application.
     *
     * @param applicationStage JavaFX stage to show for application
     */
    @Override
    public void start(Stage applicationStage) {
        initialiseSettings();
        createGUI(applicationStage);
        // Log closing of application
        applicationStage.setOnCloseRequest(event -> {
            logger.info("Closing application...");
            System.exit(0);
        });

    }

    /**
     * Java's {@code main} method. Used to launch application.
     *
     * @param arguments command-line arguments (not used)
     */
    public static void main(String[] arguments) {
        logger.info("Starting application...");
        try {
            launch(arguments);
        } catch (Exception exception) {
            logger.fatal("Application crash.");
            logger.fatal("Error class:\n{}", exception.getClass());
            logger.fatal("Error cause:\n{}", (Object) exception.getCause());
            logger.fatal("Error message:\n{}", exception.getMessage());
            logger.fatal("Error stacktrace:\n{}", (Object) exception.getStackTrace());
        }
    }
}
