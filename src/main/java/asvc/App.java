package asvc;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ASVC Application class.
 */
public class App extends Application {

    private static final Logger logger = LogManager.getLogger();
    // === Helper methods === //

    /**
     * Checks if there is a .ini file present for user settings.
     *
     * @return {@code false} if not present, {@code true} otherwise.
     */
    private boolean settingsIniFilePresent() {
        return false;
    }

    public static void main(String[] args) {
        logger.info("Starting application...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initialiseSettings();
        createGUI();
    }

    /**
     * Initialises the settings.
     */
    private void initialiseSettings() {
        logger.info("Initialise settings...");
        // Check if .ini file present
        if (!settingsIniFilePresent()) {
            // if not present -- set defaults and save to .ini
            logger.info("Settings .ini file not present. Creating one...");
        } else {
            // if present -- set settings from .ini
            logger.info("Settings .ini file present. Setting settings from file...");
        }
    }
    /**
     * Creates the GUI
     */
    private void createGUI() {
        logger.info("Build application GUI...");
    }
}
