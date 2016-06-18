package app.view.menu.file;

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
 * The Edit File Project view.
 * 
 * @author Vasco
 *
 */
public class FileEditProjectController implements ApplicationMenu {

    /**
     * The FXML resource filename.
     */
    private final String RESOURCE_FILENAME = "fxml/FileProject.fxml";
    
    /**
     * The Style sheet filename.
     */
    private final String STYLE_SHEET_FILENAME = "css/menuFileProject.css";
    
    /**
     * The Stage title for File Edit.
     */
    private final String STAGE_TITLE = "Edit Project";
    		
    /**
     * Initialising the File Edit Project receiving the controller.
     * 
     * @param mainController
     */
    public FileEditProjectController(Controller mainController) {
        URL startupLocation = getClass().getResource(RESOURCE_FILENAME);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        // Sets FXML controller as instructed by Factory
        fxmlLoader.setController(ApplicationControllerFactory.getFileEditProjectFXMLController(mainController));
        
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
