package app.view.dialog.fxml;

import app.controller.FXMLController;
import app.view.ApplicationDialogResults;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The Dialog Yes/No/Cancel Controller, asking the user for an answer
 * given these three options.
 * 
 * @author Vasco
 *
 */
public class DialogYesNoCancelFXMLController implements FXMLController {

    /**
     * The Yes button.
     */
    @FXML
    private Button yesId;
    
    /**
     * The No button.
     */
    @FXML
    private Button noId;
    
    /**
     * The Cancel button.
     */
    @FXML
    private Button cancelId;
    
    /**
     * The Message label.
     */
    @FXML
    private Label messageId;
    
    /**
     * The application dialog.
     */
    private final ApplicationDialogResults applicationDialog;
    
    /**
     * The message.
     */
    private final String message;
    
    /**
     * Initialising the widget.
     */
    public void initialize() {
    	messageId.setText(message);
    }
    
    /**
     * Constructing with the Application dialog to enable a response.
     * 
     * @param applicationDialog ApplicationDialog
     * @param message String
     */
	public DialogYesNoCancelFXMLController(ApplicationDialogResults applicationDialog, String message) {
		this.applicationDialog = applicationDialog;
		this.message = message;
	}

    /**
     * The Yes button pressed.
     */
    @FXML
    public void yesAction() {
    	applicationDialog.setResult(ApplicationDialogResults.YES);
    	closeAll();
    }

    /**
     * The No button pressed.
     */
    @FXML
    public void noAction() {
    	applicationDialog.setResult(ApplicationDialogResults.NO);
    	closeAll();
    }

    /**
     * The Cancel button pressed.
     */
    @FXML
    public void cancelAction() {
    	applicationDialog.setResult(ApplicationDialogResults.CANCEL);
    	closeAll();
    }

    /**
     * Closing the window and any other dependencies.
     */
    private void closeAll() {
    	Stage stage = (Stage) yesId.getScene().getWindow();
    	stage.close();
    }
}
