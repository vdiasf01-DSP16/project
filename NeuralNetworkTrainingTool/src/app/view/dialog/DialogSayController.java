package app.view.dialog;

import java.io.IOException;
import java.net.URL;

import app.controller.ApplicationControllerFactory;
import app.view.ApplicationDialogResults;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Informative Dialog to say something to the user.
 * 
 * @author Vasco
 *
 */
public class DialogSayController extends ApplicationDialogResults {

    /**
     * The FXML resource filename.
     */
    private final String RESOURCE_FILENAME = "fxml/DialogSay.fxml";
    
    /**
     * CSS resource file name to be used.
     */
    private final String CSS_RESOURCE = "../../css/application.css";
    
    /**
     * Initialising the Dialog.
     * 
     * @param message String
     */
    public DialogSayController(String message) {
        URL startupLocation = getClass().getResource(RESOURCE_FILENAME);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        // Sets FXML controller as instructed by Factory with this class 
        // to return the result.
        fxmlLoader.setController(ApplicationControllerFactory.getDialogSayFXMLController(message));
        
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setting up the Scene size
        Scene scene = new Scene(root);
        
        // Loading the application style sheet
        scene.getStylesheets().add(getClass().getResource(CSS_RESOURCE).toExternalForm());

        // Create a new Stage.
        Stage stage = new Stage();

        // Adding the scene to the application. 
        stage.setScene(scene);
        
        // Make sure the user responds to this before anything else.
        stage.initModality(Modality.APPLICATION_MODAL);

        // Starting the show...
        stage.showAndWait();
    }
}
