package app.view.menu.file;

import java.io.IOException;
import java.net.URL;

import app.controller.ApplicationControllerFactory;
import app.controller.Controller;
import app.view.menu.ApplicationMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The File New Project view.
 * 
 * @author Vasco
 *
 */
public class FileNewProjectController implements ApplicationMenu {

    /**
     * The FXML resource filename.
     */
    private final String RESOURCE_FILENAME = "FileNewProject.fxml";
    
    /**
     * CSS resource file name to be used.
     */
    private final String CSS_RESOURCE = "../../../css/application.css";

    /**
     * Initialising the File New Project receiving the controller.
     * 
     * @param mainController
     */
    public FileNewProjectController(Controller mainController) {
        URL startupLocation = getClass().getResource(RESOURCE_FILENAME);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        // Sets FXML controller as instructed by Factory
        fxmlLoader.setController(ApplicationControllerFactory.getFileNewProjectFXMLController(mainController));
        
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

        Stage stage = new Stage();

        // Adding the scene to the application. 
        stage.setScene(scene);
            
        // Starting the show...
        stage.show();
    }
}
