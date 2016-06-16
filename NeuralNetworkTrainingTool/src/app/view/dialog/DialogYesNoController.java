package app.view.dialog;

import java.awt.Toolkit;
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
 * The Yes/No dialog.
 * 
 * @author Vasco
 *
 */
public class DialogYesNoController extends ApplicationDialogResults {
    /**
     * The FXML resource filename.
     */
    private final String RESOURCE_FILENAME = "fxml/DialogYesNo.fxml";
    
    /**
     * CSS resource file name to be used.
     */
    private final String CSS_RESOURCE = "css/dialogYesNo.css";
    
    /**
     * The window width.
     */
    private final int WINDOW_WIDTH = 400;

    /**
     * The window height.
     */
    private final int WINDOW_HEIGH = 160;

    /**
     * The result.
     */
    private String result = "";
    
    /**
     * Initialising the File New Project receiving the controller.
     * 
     * @param message String
     */
    public DialogYesNoController(String message) {
        URL startupLocation = getClass().getResource(RESOURCE_FILENAME);

        FXMLLoader fxmlLoader = new FXMLLoader(startupLocation);

        // Sets FXML controller as instructed by Factory with this class 
        // to return the result.
        fxmlLoader.setController(ApplicationControllerFactory.getDialogYesNoFXMLController(this, message));
        
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
        stage.setResizable(false);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGH);
        double middleScreenX = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
        double middleScreenY = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
        double stageMiddleScreenX = stage.getWidth();
        double stageMiddleScreenY = stage.getHeight();
        System.out.println("Got st md X: "+stageMiddleScreenX);
//        stage.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - WINDOW_WIDTH/2);
//        stage.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - WINDOW_HEIGH/2);

        // Starting the show...
        stage.showAndWait();
    }

    /**
     * The result given by the user.
     */
    public String getResult() {
    	return result;
    }

    /**
     * Setting the result by the GUI.
     */
    public void setResult(String result) {
    	this.result = result;
    }
}
