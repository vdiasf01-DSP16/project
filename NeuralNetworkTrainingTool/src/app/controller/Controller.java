package app.controller;

import java.io.File;

import app.model.serializable.ProjectData;

/**
 * Main application controller between the view the the model.
 * 
 * @author Vasco
 *
 */
public interface Controller {

	/**
	 * Checks if nothing is pending and it is ok to quit the application
	 * without losing any important data.
	 * 
	 * @return true if ok.
	 */
	public boolean isAllSaved();

	/**
	 * Save all parts to disk.
	 */
	public void saveAll();

	/**
	 * The Project file.
	 * 
	 * @return File
	 */
	public File getProjectFile();

	/**
	 * Sets the filename to be used in saving all data whenever a request
	 * for saving is made.
	 * 
	 * @param file File
	 */
	public void setProjectFile(File file);

	/**
	 * Resets all internal data referred to a project, closes any open 
	 * windows and stops any processes still running.
	 */
	public void closeProject();

	/**
	 * Setting the project data with all the details.
	 * 
	 * @param projectData ProjectData
	 */
	public void setProjectData(ProjectData projectData);

	/**
	 * The project data.
	 * 
	 * @return ProjectData.
	 */
	public ProjectData getProjectData();

	/**
	 * Saves the project file with all project data.
	 */
	public void saveProject();

	/**
	 * When loading a new project file, the project data needs to be 
	 * loaded and updated across all controller attributes.
	 * 
	 * @param file File
	 */
	public void loadProjectFile(File file);

	/**
	 * Setting the data set file.
	 * 
	 * @param selectedFile File
	 */
	public void setDataSetFile(File selectedFile);

	/**
	 * Setting the data set attributes.
	 * 
	 * @param headerLines Integer
	 * @param footerLines Integer
	 * @param separator String
	 */
	public void setDataSetAttributes(Integer headerLines, Integer footerLines, String separator);

}
