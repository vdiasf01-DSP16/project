package app.view.dialog.fxml;

import app.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The FXML controller to send the user a message to which the user must
 * say OK.
 * 
 * @author Vasco
 *
 */
public class DialogSayFXMLController implements FXMLController {

    /**
     * The Ok button.
     */
    @FXML
    private Button okId;
    
    /**
     * The Message label.
     */
    @FXML
    private Label messageId;

    /**
     * The message to display.
     */
    private final String message;

    /**
     * Setting the widget with supplied message for user.
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
	public DialogSayFXMLController(String message) {
		this.message = message;
	}

    /**
     * The OK button pressed.
     */
    @FXML
    public void okAction() {
    	Stage stage = (Stage) okId.getScene().getWindow();
    	stage.close();
    }
}
