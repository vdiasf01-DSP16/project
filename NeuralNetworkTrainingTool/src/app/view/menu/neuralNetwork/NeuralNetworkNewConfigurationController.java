package app.view.menu.neuralNetwork;

import java.io.IOException;
import java.net.URL;

import app.controller.ApplicationControllerFactory;
import app.controller.Controller;
import app.view.menu.ApplicationMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Neural Network New Configuration view.
 * 
 * @author Vasco
 *
 */
public class NeuralNetworkNewConfigurationController  implements ApplicationMenu {

    /**
     * The FXML resource filename.
     */
    private final String RESOURCE_FILENAME = "fxml/NeuralNetworkConfiguration.fxml";
    
    /**
     * The Style sheet filename.
     */
    private final String STYLE_SHEET_FILENAME = "css/menuNeuralNetworkConfiguration.css";
    
    /**
     * The Stage title for File Edit.
     */
    private final String STAGE_TITLE = "New Neural Network Configuration";
    		
    /**
     * Initialising the Neural Network Configuration controller.
     * 
     * @param mainController
     */
    public NeuralNetworkNewConfigurationController(Controller mainController) {
        URL startupLocation = getClass().getResource(RESOURCE_FILENAME);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        // Sets FXML controller as instructed by Factory
        fxmlLoader.setController(ApplicationControllerFactory.getNeuralNetworkNewConfigurationFXMLController(mainController));
        
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setting up the Scene size
        Scene scene = new Scene(root);
        
        // Loading the application style sheet
        scene.getStylesheets().add(getClass().getResource(STYLE_SHEET_FILENAME).toExternalForm());

        Stage stage = new Stage();

        // Adding the scene to the application. 
        stage.setScene(scene);
        
        stage.setTitle(STAGE_TITLE);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Starting the show...
        stage.showAndWait();
    }
}
