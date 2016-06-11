package app.controller;

import app.view.menu.file.FileNewProjectFXMLController;

/**
 * The application controller factory for the FXML controllers.
 * 
 * @author Vasco
 *
 */
public abstract class ApplicationControllerFactory {

	/**
	 * The File New Project FXML controller to be used.
	 * 
	 * @param mainController Controller
	 * @return FXMLController
	 */
	public static FXMLController getFileNewProjectFXMLController(Controller mainController) {
		return new FileNewProjectFXMLController(mainController);
	}
}
