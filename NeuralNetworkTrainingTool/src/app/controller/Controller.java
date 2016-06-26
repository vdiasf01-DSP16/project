package app.controller;

import java.io.File;
import java.util.List;

import app.core.neuralNetwork.NeuralNetworkPatternKey;
import app.model.serializable.ProjectData;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

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
	public boolean isProjectSaved();

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
	public void loadProject(File file);

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

	/**
	 * The DataSet column header names.
	 * 
	 * @return TableColumn
	 */
	@SuppressWarnings("rawtypes")
	public TableColumn<ObservableList, ?> getDataSetColumnHeader();

	/**
	 * The DataSet data rows matching the column header.
	 * 
	 * @return List of ObservableList
	 */
	@SuppressWarnings("rawtypes")
	public List<ObservableList> getDataSetDataRows();

	/**
	 * The List of all the neural network patterns available.
	 * 
	 * @return List of String
	 */
	public List<String> getNeuralNetworkPatternList();

	/**
	 * The description for the given neural network pattern.
	 * 
	 * @param neuralNetworkPatternKey String
	 * @return String
	 */
	public String getNeuralNetworkPatternDescription(NeuralNetworkPatternKey neuralNetworkPatternKey);

	/**
	 * The description for the given neural network pattern.
	 * 
	 * @param neuralNetworkId int index
	 * @return String
	 */
	public String getNeuralNetworkPatternDescription(int neuralNetworkId);

	/**
	 * The list of all the activation functions available.
	 * 
	 * @return List of String
	 */
	public List<String> getActivationFunctionList();

}
