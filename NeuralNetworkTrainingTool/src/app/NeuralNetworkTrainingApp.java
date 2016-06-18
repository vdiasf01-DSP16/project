package app;

import java.io.IOException;
import java.net.URL;

import app.controller.ApplicationControllerFactory;
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
    private final String FXML_RESOURCE = "fxml/NeuralNetworkTrainingApp.fxml";

    /**
     * The Style sheet file name.
     */
    private final String STYLE_SHEET_FILENAME = "css/application.css";

    /**
     * Starting the GUI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Loading the fxml and initialise all required classes
        URL startupLocation = getClass().getResource(FXML_RESOURCE);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        fxmlLoader.setController(ApplicationControllerFactory.getNeuralNetworkTrainingAppFXMLController());

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setting up the Scene size
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        // Loading the application style sheet
        scene.getStylesheets().add(getClass().getResource(STYLE_SHEET_FILENAME).toExternalForm());
        
        // Adding the scene to the application. 
        primaryStage.setScene(scene);
        primaryStage.setTitle("Neural Network Toolkit");
        
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
