package com.dbarenholz.asvc;

import javafx.application.Application;;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dbarenholz.asvc.vocabitem.VocabItem;

import java.io.File;

/**
 * ASVC Application class.
 *
 * TODO: Write class documentation.
 * TODO: Write package.java documentation.
 */
public class App extends Application {

    // === Variables === //
    private static final Logger logger = LogManager.getLogger();

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

    private boolean internetAvailable() {
        // TODO: Use jSoup to check if internet connection exists.
        return false;
    }

    // === GUI Helpers === //
    private void restart() {
        // TODO: Implement restarting functionality.
    }

    private VBox step6(HBox root) {
        VBox container = new VBox();

        Label stepSixLabel = new Label("Step 6");
        Label stepSixSubLabel = new Label("Enjoy!");
        Button nextButton = new Button("Restart");

        container.getChildren().add(stepSixLabel);
        container.getChildren().add(stepSixSubLabel);
        container.getChildren().add(nextButton);

        nextButton.setOnAction(e -> {
            // TODO: Button functionality of final step...
            // add here.

            // Move to next view
            Region nextContainer = step1(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving back to step 1...");
        });

        return container;
    }

    private VBox step5(HBox root) {
        VBox container = new VBox();

        Label stepFiveLabel = new Label("Step 5");
        Label stepFiveSubLabel = new Label("Set exporting options");

        Button nextButton = new Button("Next");

        container.getChildren().add(stepFiveLabel);
        container.getChildren().add(stepFiveSubLabel);
        container.getChildren().add(nextButton);



        nextButton.setOnAction(e -> {
            // TODO: Button functionality of first step...
            // add here.

            // Move to next view
            Region nextContainer = step6(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving to step 6...");
        });

        return container;
    }

    private VBox step4(HBox root) {
        VBox container = new VBox();

        // TODO: Set font styles
        Label stepFourLabel = new Label("Step 4");
        Label stepFourSubLabel = new Label("Verify scraped results");

        // Create editable tableview
        TableView<VocabItem> vocabTable = new TableView<>();
        vocabTable.setEditable(true);

        // build table columns
        TableColumn<VocabItem, String> kanjiColumn = new TableColumn<>("Kanji");
        kanjiColumn.setCellValueFactory(new PropertyValueFactory<>("kanji"));

        TableColumn<VocabItem, String> kanaColumn = new TableColumn<>("Kana");
        kanaColumn.setCellValueFactory(new PropertyValueFactory<>("kana"));

        vocabTable.getColumns().add(kanjiColumn);
        vocabTable.getColumns().add(kanaColumn);

        // add table items
        vocabTable.getItems().add(new VocabItem("私", "kanaa")); //watashi
        vocabTable.getItems().add(new VocabItem("僕", "kanoo")); //boku

        Button nextButton = new Button("Looks good!");

        container.getChildren().add(stepFourLabel);
        container.getChildren().add(stepFourSubLabel);
        container.getChildren().add(new VBox(vocabTable));
        container.getChildren().add(nextButton);

        nextButton.setOnAction(e -> {
            // TODO: Button functionality of fourth step...
            // add here.

            // Move to next view
            Region nextContainer = step5(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving to step 5...");
        });

        return container;
    }

    private VBox step3(HBox root) {
        VBox container = new VBox();

        // TODO: set bold style and font
        Label stepThreeLabel = new Label("Step 3");
        Label stepThreeSubLabel = new Label("Set scraping settings");

        CheckBox kanaBuiltIn = new CheckBox("Use built-in kana (kuromoji)");
        CheckBox kanaJisho = new CheckBox("Use kana from Jisho");
        CheckBox sentenceJisho = new CheckBox("Use sentence from Jisho");
        CheckBox sentenceTatoeba = new CheckBox("Use sentence from Tatoeba");
        CheckBox audioForvo = new CheckBox("Use audio from Forvo");
        CheckBox audioOJAD = new CheckBox("Use (generated) audio from OJAD");

        GridPane internetPane = new GridPane();
        Label noInternetLabel = new Label("Internet is not available at the moment...");
        Button retryInternetButton = new Button("Retry Connection");
        internetPane.add(noInternetLabel, 0, 0);
        internetPane.add(retryInternetButton, 0, 1);

        Button nextButton = new Button("Scrape!");

        // Ignore IntelliJ 'duplicate' line thing. It's code for GUIs.
        container.getChildren().add(stepThreeLabel);
        container.getChildren().add(stepThreeSubLabel);
        container.getChildren().add(kanaBuiltIn);
        container.getChildren().add(kanaJisho);
        container.getChildren().add(sentenceJisho);
        container.getChildren().add(sentenceTatoeba);
        container.getChildren().add(audioForvo);
        container.getChildren().add(audioOJAD);
        container.getChildren().add(internetPane);
        container.getChildren().add(nextButton);

        nextButton.setOnAction(e -> {
            // TODO: Button functionality of third step...
            // add here.

            // Move to next view
            Region nextContainer = step4(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving to step 4...");
        });

        return container;
    }

    private ScrollPane step2(HBox root) {
        // TODO: Set scroll direction to vertical
        ScrollPane container = new ScrollPane();
        VBox internalContainer = new VBox();

        // TODO: set bold style and font
        Label stepTwoLabel = new Label("Step 2");
        Label stepTwoSubLabel = new Label("Check parsed words!");

        ListView<String> parsedWordsList = new ListView<String>();
        // TODO: add actual items to list in stead of test thing
        parsedWordsList.getItems().add("Test item");
        parsedWordsList.getItems().add("Test item 2");

        Button nextButton = new Button("Nice!");

        internalContainer.getChildren().add(stepTwoLabel);
        internalContainer.getChildren().add(stepTwoSubLabel);
        internalContainer.getChildren().add(parsedWordsList);
        internalContainer.getChildren().add(nextButton);

        container.setContent(internalContainer);

        nextButton.setOnAction(e -> {
            // TODO: Button functionality of second step...
            // add here.

            // Move to next view
            Region nextContainer = step3(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving to step 3...");
        });

        return container;
    }

    private VBox step1(HBox root) {
        VBox container = new VBox();

        // TODO: set bold style and font
        Label stepOneLabel = new Label("Step 1");
        Label stepOneSubLabel = new Label("Get lyrics...");

        HBox urlBox = new HBox();
        CheckBox urlCheckbox = new CheckBox("...from url");
        TextField urlTextfield = new TextField("https://www.lyrical-nonsense.com/lyrics/kenshi-yonezu/lemon/");
        urlBox.getChildren().add(urlCheckbox);
        urlBox.getChildren().add(urlTextfield);

        HBox localFileBox = new HBox();
        CheckBox localFileCheckbox = new CheckBox("...from local file");
        TextField localFileTextfield = new TextField("/path/to/local/file.txt");
        localFileBox.getChildren().add(localFileCheckbox);
        localFileBox.getChildren().add(localFileTextfield);

        CheckBox lyricsCheckbox = new CheckBox("...from pasted lyrics (default)");

        // Set default checkboxes
        urlCheckbox.setSelected(false);
        localFileCheckbox.setSelected(false);
        lyricsCheckbox.setSelected(true);

        TextArea lyrics = new TextArea("Paste lyrics in this text area.");

        Button nextButton = new Button("NEXT");

        container.getChildren().add(stepOneLabel);
        container.getChildren().add(stepOneSubLabel);
        container.getChildren().add(urlBox);
        container.getChildren().add(localFileBox);
        container.getChildren().add(lyricsCheckbox);
        container.getChildren().add(lyrics);
        container.getChildren().add(nextButton);

        nextButton.setOnAction(e -> {
            // TODO: Button functionality of first step...
            // add here.

            // Move to next view
            Region nextContainer = step2(root);
            root.getChildren().setAll(nextContainer);
            logger.info("Moving to step 2...");
        });

        return container;
    }

    /**
     * Helper method to build the GUI.
     */
    private void createGUI(Stage stage) {
        logger.info("Build application GUI...");

        // Create root element
        HBox root = new HBox();

        // Start with step 1.
        VBox container = step1(root);
        root.getChildren().setAll(container);
        logger.info("Moving to step 1...");

        // Add root to the scene, and set the scene
        Scene scene = new Scene(root, Settings.prefWidth, Settings.prefHeight);
        stage.setMinWidth(Settings.minWidth);
        stage.setMinHeight(Settings.minHeight);
        stage.setScene(scene);
        stage.setTitle(Settings.applicationTitle);
        stage.show();
    }

    // === Main Methods === //

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
