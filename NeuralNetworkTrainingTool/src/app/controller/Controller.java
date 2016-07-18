package app.controller;

import java.io.File;
import java.util.List;

import app.core.neuralNetwork.NeuralNetworkPatternKey;
import app.model.serializable.ProjectData;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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
	public TableColumn<ObservableList, ?> getDataSetTableColumnHeader();

	/**
	 * The DataSet data rows matching the column header.
	 * 
	 * @return List of ObservableList
	 */
	@SuppressWarnings("rawtypes")
	public List<ObservableList> getDataSetTableRows(int dataRows);

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

	/**
	 * The header line or first data line if no header was set.
	 * 
	 * @return List String
	 */
	public List<String> getHeaderColumns();

	/**
	 * The currently set Data Set map selection to be used from the
	 * data set input columns into the neural network input.
	 * 
	 * List index + 1 returns true for respective column in data set, 
	 * if to be used, and false if not.
	 * 
	 * @return List of Boolean
	 */
	public List<Boolean> getInputMapDataSetSelection();

	/**
	 * Setting the index value of the list with the new selection.
	 * If the selection made is bigger or smaller than the allowed,
	 * the warning label will be changed accordingly.
	 * 
	 * @param index int
	 * @param newValue Boolean
	 * @param warning Label
	 */
	public void setInputMapDataSetSelection(int index, Boolean newValue, Label warning);

	/**
	 * Setting the input layer size number of neurons required.
	 * 
	 * @param inputLayerSize int
	 */
	public void setInputLayerSize(int inputLayerSize);

	/**
	 * Setting the output layer size number of neurons required.
	 * 
	 * @param outputLayerSize int
	 */
	public void setOutputLayerSize(int outputLayerSize);

	/**
	 * The output layer number of neurons.
	 * 
	 * @return int
	 */
	public int getOutputLayerSize();

	/**
	 * Setting the hidden layer number and amount of neurons required.
	 * 
	 * @param hiddenLayerSizes
	 */
	public void setHiddenLayerSizes(List<Integer> hiddenLayerSizes);

	/**
	 * Resets all data possibly set for the neural network configuration.
	 */
	public void resetNeuralNetworkConfiguration();

	/**
	 * The operators list for the output functions that can be used and applied
	 * to the data set inputs, to match found output neural network layer data 
	 * only if supervised.
	 * 
	 * @return String List
	 */
	public List<String> getOperatorsList();

	/**
	 * Resets all data possibly set for the Mapping configuration.
	 */
	public void resetMappingSelections();
}
