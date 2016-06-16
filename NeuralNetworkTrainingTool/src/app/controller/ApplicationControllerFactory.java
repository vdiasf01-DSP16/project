package app.controller;

import app.fxml.NeuralNetworkTrainingAppFXMLController;
import app.view.ApplicationDialog;
import app.view.dialog.fxml.DialogYesNoCancelFXMLController;
import app.view.dialog.fxml.DialogYesNoFXMLController;
import app.view.menu.file.fxml.FileNewProjectFXMLController;

/**
 * The application controller factory for the FXML controllers.
 * 
 * @author Vasco
 *
 */
public abstract class ApplicationControllerFactory {

	/**
	 * The Main application FXML controller to be used.
	 * 
	 * @return FXMLController
	 */
	public static FXMLController getNeuralNetworkTrainingAppFXMLController() {
		return new NeuralNetworkTrainingAppFXMLController();
	}
	
	/**
	 * The File New Project FXML controller to be used.
	 * 
	 * @param mainController Controller
	 * @return FXMLController
	 */
	public static FXMLController getFileNewProjectFXMLController(Controller mainController) {
		return new FileNewProjectFXMLController(mainController);
	}

	/**
	 * The Dialog Yes No Cancel FXML controller to be used.
	 * 
	 * @param mainController ApplicationDialog
	 * @param message String
	 * @return FXMLController
	 */
	public static FXMLController getDialogYesNoCancelFXMLController(ApplicationDialog mainController, String message) {
		return new DialogYesNoCancelFXMLController(mainController, message);
	}

	/**
	 * The Dialog Yes No FXML controller to be used.
	 * 
	 * @param mainController ApplicationDialog
	 * @param message String
	 * @return FXMLController
	 */
	public static Object getDialogYesNoFXMLController(ApplicationDialog mainController, String message) {
		return new DialogYesNoFXMLController(mainController, message);
	}
}
