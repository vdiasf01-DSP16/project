package app.controller;

import app.model.project.ProjectData;

/**
 * Main application controller between the view the the model.
 * 
 * @author Vasco
 *
 */
public interface Controller {
	
	/**
	 * Saves received project details.
	 * 
	 * @param projectDetails
	 */
	public void saveProject(ProjectData projectDetails);

}
