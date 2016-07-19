package app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import app.controller.menu.neuralNetwork.OutputFunctionDetails;
import app.controller.menu.neuralNetwork.OutputFunctionDetailsImpl;
import app.core.activationFunction.ActivationFunctionFactory;
import app.core.activationFunction.ActivationFunctionKey;
import app.core.dataSet.DataSet;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.neuralNetwork.NeuralNetworkPatternFactory;
import app.core.neuralNetwork.NeuralNetworkPatternKey;
import app.model.file.DataSetFileGUIReader;
import app.model.serializable.DataSetFileAttributes;
import app.model.serializable.FileAttributes;
import app.model.serializable.NeuralNetworkConfig;
import app.model.serializable.NeuralNetworkConfigImpl;
import app.model.serializable.ProjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * The Main Application Controller.
 * 
 * @author Vasco
 *
 */
public class ControllerImpl implements Controller {

    /**
     * The Project File where all data is stored.
     */
    private File projectFile;
    
    /**
     * The Project details to be stored in file.
     */
    private ProjectData projectData;
    
    /**
     * Flag to indicate if all changes have been saved.
     */
    private boolean projectIsSaved = true;

    /**
     * The selected data set file with all its attributes.
     */
    private FileAttributes dataSetFileAttributes;
    
    /**
     * The Neural Network configuration set by the user.
     */
    private NeuralNetworkConfig neuralNetworkConfig;

    /**
     * The selection from the user on which columns from the
     * data set should be included into the neural network
     * inputs.
     */
    private List<Boolean> inputMapDataSetSelection;

    /**
     * The list of known operators to be used on the output layer.
     */
    private final List<String> operatorsList;
    
    /**
     * The list of output functions and their adjacent values 
     * supplied by the user.
     */
    private final List<OutputFunctionDetails> mappingOutputFunction;

    /**
     * Messages to tell the uses when input data set selections have been made
     * not been made to match the neural network total number of inputs.
     */
    private final String WARNING_TOO_MANY_SELECTED = "Too many data set inputs selected";
    private final String WARNING_NOT_SELECTED_ENOUGH = "Not enough data set inputs selected";
    private final String INFO_SELECTION_REQUIREMENTS_MET = "Ready";

    /**
     * The data set to be used to train a network.
     */
    private DataSet dataset;
    
    /**
     * Controller Constructor to initialise required objects.
     */
    public ControllerImpl() {
        neuralNetworkConfig = new NeuralNetworkConfigImpl();
        operatorsList = new LinkedList<>();
        for( MathOperatorKey operator : MathOperatorKey.values() ) {
        	operatorsList.add(MathOperatorFactory.getName(operator));
        }
        mappingOutputFunction = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProjectSaved() {
        if ( projectIsSaved ) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getProjectFile() {
        return projectFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectFile(File file) {
        this.projectFile = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeProject() {
        this.projectData = null;
        this.projectFile = null;
        this.projectIsSaved = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveProject() {
        // TODO: Deal with exceptions
        if ( projectFile != null ) {
            if ( ! projectFile.exists() ) {
                try {
                    projectFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            if ( projectFile.canWrite() ) {
                FileOutputStream fout = null;
                ObjectOutputStream oos = null;
                try {
                    fout = new FileOutputStream(projectFile);
                    oos = new ObjectOutputStream(fout);
                    
                    // The list of objects that must be then read back 
                    // in the same order. Note: Changing this will result
                    // in older projects to not be readable anymore.
                    oos.writeObject(projectData);
                    oos.writeObject(dataSetFileAttributes);
                    oos.writeObject(neuralNetworkConfig);

                    projectIsSaved = true;

                } catch (IOException e) { 
                    e.printStackTrace();
                } finally {
                    try {
                        fout.close();
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectData getProjectData() {
        return projectData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadProject(File file) {
        if ( file != null ) {
            if ( file.exists() & file.canRead() ) {
                projectFile = file;
                FileInputStream fin = null;
                ObjectInputStream ois = null;
                try {
                    fin = new FileInputStream(file);
                    ois = new ObjectInputStream(fin);
                    try {

                        // The list of objects that must be then read back 
                        // in the same order in which they were saved. 
                        // Note: Changing this will result in older projects 
                        // to not be readable anymore.
                        projectData = (ProjectData) ois.readObject();
                        dataSetFileAttributes = (FileAttributes) ois.readObject();
                        neuralNetworkConfig = (NeuralNetworkConfig) ois.readObject();

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    projectIsSaved = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fin.close();
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setDataSetFile(File selectedFile) {
        this.dataSetFileAttributes = new DataSetFileAttributes();
        dataSetFileAttributes.setFilename(selectedFile.getAbsolutePath());
        inputMapDataSetSelection = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setDataSetAttributes(Integer headerLines, Integer footerLines, String separator) {
        if ( dataSetFileAttributes != null ) {
            dataSetFileAttributes.setHeaderRows(headerLines);
            dataSetFileAttributes.setFooterRows(footerLines);
            dataSetFileAttributes.setSeparator(separator);
        }
        inputMapDataSetSelection = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getNeuralNetworkPatternList() {
        List<String> list = new LinkedList<>();
        for( NeuralNetworkPatternKey key : NeuralNetworkPatternKey.values() ) {
            list.add(NeuralNetworkPatternFactory.getName(key));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getNeuralNetworkPatternDescription(NeuralNetworkPatternKey neuralNetworkPattern) {
        return NeuralNetworkPatternFactory.getDescription(neuralNetworkPattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getNeuralNetworkPatternDescription(int neuralNetworkId) {
        int index = 0;
        for( NeuralNetworkPatternKey key : NeuralNetworkPatternKey.values() ) {
            if ( neuralNetworkId == index ) return NeuralNetworkPatternFactory.getDescription(key);
            index++;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getActivationFunctionList() {
        List<String> list = new LinkedList<>();
        for( ActivationFunctionKey key : ActivationFunctionKey.values() ) {
            list.add(ActivationFunctionFactory.getName(key));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<String> getHeaderColumns() {
        // Cast to a super set for the non-serializable parsing capabilities.
        DataSetFileGUIReader dataSet = (DataSetFileGUIReader) dataSetFileAttributes;
        
        if ( dataSet == null ) return null;

        // Check if we have a header
        int headerRows = dataSet.getHeaderRows();
        
        // If we have header, return it.
        if ( headerRows > 0 ) {
            List<List<String>> headerColumns = dataSet.getHeaderDataRows();
            return headerColumns.get(headerRows-1);
        }
        // No header? Return first data row instead
        return dataSet.getFirstDataRows(1).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override public TableColumn<ObservableList, ?> getDataSetTableColumnHeader() {
        TableColumn<ObservableList, ?> allcols = new TableColumn<>();

        List<String> columnHeader = getHeaderColumns();
        if ( columnHeader != null ) {
            for(int i = 0 ; i < columnHeader.size(); i++) {
                int j = i;
                TableColumn<ObservableList,String> col = new TableColumn<>(columnHeader.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                   public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                       return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                allcols.getColumns().add(col);
            }
        }

        return allcols;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override public List<ObservableList> getDataSetTableRows(int dataRows) {
        // Cast to a super set for the non-serializable parsing capabilities.
        DataSetFileGUIReader dataSet = (DataSetFileGUIReader) dataSetFileAttributes;
        if ( dataSet == null ) return null;

        List<List<String>> firstDataRows = dataSet.getFirstDataRows(dataRows);
        List<ObservableList> rowList = new LinkedList<>();

        for( int i = 0; i < firstDataRows.size(); i++ ) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.addAll(firstDataRows.get(i));
            rowList.add(row);
        }
        
        return rowList;
    }

    /**
     * {@inheritDoc}
     */
    @Override public List<Boolean> getInputMapDataSetSelection() {
        // Instantiate the map list which is based on the data set column amount.
        if ( inputMapDataSetSelection == null ) {
            inputMapDataSetSelection = new LinkedList<>();
            for( int i = 0; i < getHeaderColumns().size(); i++ ) {
                inputMapDataSetSelection.add(false);
            }
        }
        return inputMapDataSetSelection;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setInputMapDataSetSelection(int index, Boolean newValue, Label warning) {
        inputMapDataSetSelection.set(index, newValue);

        int dataSetInputSelectionsMade = 0;
        for ( Boolean value : inputMapDataSetSelection ) if ( value ) dataSetInputSelectionsMade++;

        if ( dataSetInputSelectionsMade > neuralNetworkConfig.getInputLayerSize() ) {
        	warning.setText(WARNING_TOO_MANY_SELECTED);
        }
        else if ( dataSetInputSelectionsMade < neuralNetworkConfig.getInputLayerSize() ) {
        	warning.setText(WARNING_NOT_SELECTED_ENOUGH);
        }
        else {
        	warning.setText(INFO_SELECTION_REQUIREMENTS_MET);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setInputLayerSize(int inputLayerSize) {
        neuralNetworkConfig.setInputLayerSize(inputLayerSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setOutputLayerSize(int outputLayerSize) {
        neuralNetworkConfig.setOutputLayerSize(outputLayerSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setHiddenLayerSizes(List<Integer> hiddenLayerSizes) {
        neuralNetworkConfig.setHiddenLayerSizes(hiddenLayerSizes);
    }

	/**
     * {@inheritDoc}
     */
    @Override public int getOutputLayerSize() {
        return neuralNetworkConfig.getOutputLayerSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void resetNeuralNetworkConfiguration() {
        neuralNetworkConfig.setHiddenLayerSizes(null);
        neuralNetworkConfig.setInputLayerSize(0);
        neuralNetworkConfig.setOutputLayerSize(0);
        inputMapDataSetSelection = null;
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public List<String> getOperatorsList() {
		return operatorsList;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void resetMappingSelections() {
		inputMapDataSetSelection = null;
		mappingOutputFunction.clear();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void setOutputFunction(int outputId, String mathOperatorKey, String functionValue) {
		// Calculate the value of the initial function value supplied.
		double initialValue = 0.0;
		if ( functionValue.length() > 0 ) initialValue = Double.parseDouble(functionValue);

		if ( mappingOutputFunction.stream().anyMatch(p -> p.getOutputId() == outputId) ) {
			OutputFunctionDetails functionDetails = mappingOutputFunction.stream()
					.filter(p -> p.getOutputId() == outputId)
					.collect(Collectors.toList()).get(0);
			// Update the function.
			functionDetails.setOutputFunction(outputId, mathOperatorKey, initialValue);
		}
		else {
			// Create one.
			OutputFunctionDetails outputFunctionDetails = new OutputFunctionDetailsImpl();
			outputFunctionDetails.setOutputFunction(outputId, mathOperatorKey, initialValue);
			mappingOutputFunction.add(outputFunctionDetails);
		}
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public OutputFunctionDetails getOutputFunction(int outputId) {
		if ( mappingOutputFunction.size() == 0 ) return null; 
		List<OutputFunctionDetails> list = mappingOutputFunction.stream()
				.filter(p -> p.getOutputId() == outputId )
				.collect(Collectors.toList());
		
		if ( list != null & list.size() > 0 ) return list.get(0);
		return null;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public double getOutputFunctionSelectionValue(int outputId) {
		OutputFunctionDetails outputFunctionDetails = getOutputFunction(outputId);
		if ( outputFunctionDetails != null ) {
			return outputFunctionDetails.getFunctionValue();
		}
		return 0.0;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void setOutputFunction(int outputId, double initialValue) {
		OutputFunctionDetails outputFunctionDetails = getOutputFunction(outputId);
		if ( outputFunctionDetails != null ) {
			outputFunctionDetails.setOutputFunction(outputId, 
					outputFunctionDetails.getMathOperatorKey(), initialValue);
		}
		else {
			throw new IllegalArgumentException("Cannot set a value into a not yet created function.");
		}
	}
}
