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
    private final String RESOURCE_FILENAME = "fxml/FileNewProject.fxml";
    
    /**
     * The Style sheet filename.
     */
    private final String STYLE_SHEET_FILENAME = "css/menuFileNewProject.css";
    
    /**
     * The Stage title for File New Project.
     */
    private final String STAGE_TITLE = "New Project";
    		
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
        scene.getStylesheets().add(getClass().getResource(STYLE_SHEET_FILENAME).toExternalForm());

        Stage stage = new Stage();

        // Adding the scene to the application. 
        stage.setScene(scene);

        // The Stage title.
        stage.setTitle(STAGE_TITLE);

        // Starting the show...
        stage.showAndWait();
    }
}
