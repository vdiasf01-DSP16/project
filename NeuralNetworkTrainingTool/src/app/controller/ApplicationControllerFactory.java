package app.controller;

import app.fxml.NeuralNetworkTrainingAppFXMLController;
import app.view.ApplicationDialogResults;
import app.view.dialog.fxml.DialogSayFXMLController;
import app.view.dialog.fxml.DialogYesNoCancelFXMLController;
import app.view.dialog.fxml.DialogYesNoFXMLController;
import app.view.menu.file.fxml.FileEditProjectFXMLController;
import app.view.menu.file.fxml.FileNewProjectFXMLController;
import app.view.menu.neuralNetwork.fxml.NeuralNetworkConfigurationFXMLController;

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
	 * The File Edit Project FXML controller to be used.
	 * 
	 * @param mainController Controller
	 * @return FXMLController
	 */
	public static FXMLController getFileEditProjectFXMLController(Controller mainController) {
		return new FileEditProjectFXMLController(mainController);
	}

	/**
	 * The Neural Network FXML controller to be used.
	 * 
	 * @param mainController Controller
	 * @return FXMLController
	 */
	public static FXMLController getNeuralNetworkNewConfigurationFXMLController(Controller mainController) {
		return new NeuralNetworkConfigurationFXMLController(mainController);
	}


	/**
	 * The Dialog Yes No Cancel FXML controller to be used.
	 * 
	 * @param mainController ApplicationDialog
	 * @param message String
	 * @return FXMLController
	 */
	public static FXMLController getDialogYesNoCancelFXMLController(ApplicationDialogResults mainController, String message) {
		return new DialogYesNoCancelFXMLController(mainController, message);
	}

	/**
	 * The Dialog Yes No FXML controller to be used.
	 * 
	 * @param mainController ApplicationDialog
	 * @param message String
	 * @return FXMLController
	 */
	public static Object getDialogYesNoFXMLController(ApplicationDialogResults mainController, String message) {
		return new DialogYesNoFXMLController(mainController, message);
	}

	/**
	 * The Dialog to give the user some information.
	 * 
	 * @param message String
	 * @return FXMLController
	 */
	public static FXMLController getDialogSayFXMLController(String message) {
		return new DialogSayFXMLController(message);
	}
}
