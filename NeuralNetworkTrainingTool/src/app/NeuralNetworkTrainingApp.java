package app;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Application starter.
 * 
 * @author Vasco
 *
 */
public class NeuralNetworkTrainingApp extends Application {

    /**
     * Application width.
     */
    private final int WIDTH = 1280;
    
    /**
     * Application height.
     */
    private final int HEIGHT = 800;

    /**
     * The FXML file to be used as the main application view.
     */
    private final String FXML_RESOURCE = "NeuralNetworkTrainingApp.fxml";

    /**
     * The global css style sheet to be used.
     */
    private final String CSS_STYLESHEET = "css/application.css";

    /**
     * Starting the GUI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Loading the fxml and initialise all required classes
        URL startupLocation = getClass().getResource(FXML_RESOURCE);
        Parent root = FXMLLoader.load(startupLocation);

        // Setting up the Scene size
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        // Loading the application style sheet
        scene.getStylesheets().add(getClass().getResource(CSS_STYLESHEET).toExternalForm());
        
        // Adding the scene to the application. 
        primaryStage.setScene(scene);
        
        // Starting the show...
        primaryStage.show();
    }

    /**
     * Launching the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
