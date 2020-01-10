package com.dbarenholz.asvc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ASVC Application class.
 */
public class App extends Application {

    // === Variables === //
    private static final Logger logger = LogManager.getLogger();

    // === Helper methods === //
    /**
     * Checks if there is a .ini file present for user settings.
     *
     * @return {@code false} if not present, {@code true} otherwise.
     */
    private boolean settingsIniFilePresent() {
        // TODO: Write settingsIniFilePresent method
        return false;
    }

    /**
     * Helper method to initialise settings.
     */
    private void initialiseSettings() {
        logger.info("Initialise settings...");
        // Check if .ini file present
        if (!settingsIniFilePresent()) {
            // if not present -- set defaults and save to .ini
            logger.debug("Settings .ini file not present. Creating one...");
        }
        // .ini is present (newly created, or was already present)
        logger.debug("Settings .ini file present. Setting settings from file...");
    }

    /**
     * Helper method to build the GUI.
     */
    private void createGUI(Stage stage) {
        logger.info("Build application GUI...");

        // TODO: Build entire GUI.
        Label label = new Label("Hello World, JavaFX !");
        Scene scene = new Scene(label, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Temp title");
        stage.show();
    }

    // === Main methods === //
    /**
     * Java's {@code main} method. Used to launch application.
     *
     * @param arguments command-line arguments (not used)
     */
    public static void main(String[] arguments) {
        logger.info("Starting application...");
        launch(arguments);
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
    }
}
