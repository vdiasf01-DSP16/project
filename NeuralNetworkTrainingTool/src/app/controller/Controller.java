package app.controller;

import app.model.project.ProjectData;
import javafx.stage.Stage;

/**
 * Main application controller between the view the the model.
 * 
 * @author Vasco
 *
 */
public interface Controller {

	/**
	 * The application Stage.
	 * 
	 * @return Stage
	 */
	public Stage getStage();

	/**
	 * Setting the application Stage.
	 * 
	 * @return Stage
	 */
	public void setStage(Stage stage);

	/**
	 * Saves received project details.
	 * 
	 * @param projectDetails
	 */
	public void saveProject(ProjectData projectDetails);

	/**
	 * Checks if nothing is pending and it is ok to quit the application
	 * without losing any important data.
	 * 
	 * @return true if ok.
	 */
	public boolean isOkToQuit();

	/**
	 * Save all parts to disk.
	 */
	public void saveAll();

}
