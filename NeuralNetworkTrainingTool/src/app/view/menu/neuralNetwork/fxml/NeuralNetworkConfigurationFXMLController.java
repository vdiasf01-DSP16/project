package app.view.menu.neuralNetwork.fxml;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.controller.FXMLController;
import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.view.ApplicationDialogFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Neural Network Config Controller, to configure a new / edit already set 
 * Neural Network configuration, containing three tabs: 
 *   - Data Set: to manage the source of the data
 *   - Neural Network: to manage the Neural Network topology
 *   - Mapping: to link the two.
 * 
 * @author Vasco
 *
 */
public class NeuralNetworkConfigurationFXMLController implements FXMLController {
    /**
     * Overall widget group.
     */
    @FXML private TabPane tabPaneId;
    @FXML private Tab dataSetTabId;
    @FXML private Tab neuralNetworkTabId;
    @FXML private Tab mappingTabId;

    /**
     * Data set tab group
     */
    @SuppressWarnings("rawtypes")
    @FXML private TableView<ObservableList> tableId;
    @FXML private Button dataSetSelectedId;
    @FXML private Label dataSetFileId;
    @FXML private TextField headerLinesId;
    @FXML private TextField footerLinesId;
    @FXML private TextField separatorId;
    @FXML private Button dataSetNextId;
    @FXML private Button dataSetAttributesId;

    /**
     * Neural Networks tab group
     */
    @FXML private ComboBox<String> neuralNetworkTopologyId;
    @FXML private Button neuralNetworkTolopogySelectId;
    @FXML private Label neuralNetworkTopologyLabelId;
    @FXML private TextField neuronInputLayerAmountId;
    @FXML private TextField neuronHiddenLayerNumberId;
    @FXML private TextField neuronOutputLayerAmountId;
    @FXML private ComboBox<String> activationFunctionSelectionId;
    @FXML private Pane topologySetupId;
    @FXML private Separator hiddenLayerSeparatorId;
    @FXML private Pane hiddenLayerSetupPaneId;
    @FXML private Button neuralNetworkNextId;

    /**
     * Defining the change listener to activation function changes,
     * to update on change the ability to press the button to mapping.
     */
    private ChangeListener<String> activationFunctionChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            mainController.setActivationFunction(newValue);
            checkAllNeuralNetworkConfigFilled(null);
        }
    };

    /**
     * Mapping tab group
     */
    @FXML private ComboBox<String> mappingSelectorInOutId;
    @FXML private Pane mappingPaneInputId;
    @FXML private Pane mappingPaneOutputId;
    @FXML private Label mappingSelectorLabelId;
    @FXML private CheckBox mappingSelectAllCheckboxId;
    @FXML private CheckBox mappingSupervisedCheckboxId;
    @FXML private Button mappingApplyId;
    @FXML private VBox mappingOutVBoxId;
    @FXML private ScrollPane mappingOutSrollId;
    private List<Boolean> inputMapDataSetSelection;
    
    /**
     * The main controller.
     */
    private final Controller mainController;

    /**
     * Data set file extension list accepted.
     */
    private final List<Map<String,String>> EXTENSION_LIST;

    /**
     * Default value selection.
     */
    private final String FIRST_SELECTION_DESCRIPTION = "Please Select";

    /**
     * Limiting the maximum amount of hidden layers for a neural network.
     */
    private final int MAX_HIDDEN_LAYERS = 30;
    
    /**
     * The String for the input mapping selector.
     */
    private final String MAPPING_SELECTOR_INPUT = "Input Mapping";

    /**
     * The String for the output mapping selector to be used for training.
     */
    private final String MAPPING_SELECTOR_OUTPUT = "Output Mapping";

    /**
     * Window width size.
     */
    private final int WINDOW_WIDTH = 600;

    /**
     * Number of maximum columns on the mapping input selection.
     */
    private final int MAX_INPUT_COLUMNS = 15;

    /**
     * Default value selection for no function to be applied.
     */
    private final String NO_FUNCTION_DESCRIPTION = "No Function";

    /**
     * Only showing these first number of rows on a selected file.
     */
    private final int MAX_DATASET_ROWS = 10;
    
    /**
     * The number of columns for the check boxes under mapping.
     */
    private int TOTAL_COLUMNS_MAPPING_OUTPUT;

    /**
     * The mapping top text to indicate that everything is ready for submission.
     */
    private final String INFO_SELECTION_REQUIREMENTS_MET = "Ready";

    /**
     * Initialising the various details and selections.
     */
    public void initialize() {
        dataSetTabId.setDisable(false);
        dataSetNextId.setDisable(true);
        dataSetAttributesId.setDisable(true);
        
        headerLinesId.setText("");
        footerLinesId.setText("");
        separatorId.setText("");
        tableId.getColumns().removeAll(tableId.getColumns());

        mainController.resetNeuralNetworkConfiguration();

        neuralNetworkTolopogySelectId.setDisable(false);
        neuralNetworkTabId.setDisable(true);
        neuralNetworkTolopogySelectId.setDisable(true);
        neuralNetworkNextId.setDisable(true);

        // Initialise the Neural Network pattern lists available to use.
        neuralNetworkTopologyId.getItems().clear();
        neuralNetworkTopologyId.getItems().addAll(mainController.getNeuralNetworkPatternList());
        neuralNetworkTopologyId.setValue(FIRST_SELECTION_DESCRIPTION);

        // Initialise the Activation Functions available to use.
        activationFunctionSelectionId.valueProperty().removeListener(activationFunctionChangeListener);
        activationFunctionSelectionId.getItems().clear();
        activationFunctionSelectionId.getItems().addAll(mainController.getActivationFunctionList());
        activationFunctionSelectionId.setValue(FIRST_SELECTION_DESCRIPTION);
        activationFunctionSelectionId.valueProperty().addListener(activationFunctionChangeListener);

        // Initial Neural Network state.
        neuralNetworkTopologyLabelId.setVisible(true);
        topologySetupId.setVisible(false);
        hiddenLayerSetupPaneId.setVisible(false);

        neuralNetworkTopologyLabelId.setText("");

        neuronHiddenLayerNumberId.clear();
        neuronOutputLayerAmountId.clear();
        neuronInputLayerAmountId.clear();
        neuronHiddenLayerNumberId.addEventFilter(KeyEvent.KEY_RELEASED, this::checkAllNeuralNetworkConfigFilled);
        neuronHiddenLayerNumberId.addEventFilter(KeyEvent.KEY_RELEASED, this::neuralNetworkApplyAction);
        neuronInputLayerAmountId.addEventFilter(KeyEvent.KEY_RELEASED, this::checkAllNeuralNetworkConfigFilled);
        neuronOutputLayerAmountId.addEventFilter(KeyEvent.KEY_RELEASED, this::checkAllNeuralNetworkConfigFilled);
        
        mappingTabId.setDisable(true);
        mappingApplyId.setDisable(true);

        // Initialise the Mapping combo selections.
        mappingSelectorInOutId.getItems().addAll(MAPPING_SELECTOR_INPUT);
        mappingSelectorLabelId.setAlignment(Pos.CENTER);
    }

    /**
     * Constructor to instantiate the required main controller.
     * 
     * @param mainController
     */
    public NeuralNetworkConfigurationFXMLController(Controller mainController) {
        this.mainController = mainController;
        EXTENSION_LIST = new LinkedList<>();
        Map<String,String> extension = new HashMap<>();
        extension.put("Data Set Files", "*.*");
        EXTENSION_LIST.add(extension);
    }

    /**
     * Moving on to next tab from dataSet to NeuralNetwork config tab.
     */
    @FXML public void dataSetNextAction() {
        resetNetwork();
        neuralNetworkTabId.setDisable(false);
        neuralNetworkTabId.getTabPane().getSelectionModel().select(neuralNetworkTabId);

        resetMapping();
        mappingTabId.setDisable(true);
    }

    /**
     * Moving on to next stage: Mapping.
     */
    @FXML public void neuralNetworkNextAction() {
        resetMapping();
        mappingTabId.setDisable(false);
        mappingTabId.getTabPane().getSelectionModel().select(mappingTabId);

        // Selecting the next tab, authorising it automatically.
    	mappingSelectorInOutId.valueProperty().setValue(FIRST_SELECTION_DESCRIPTION);
    	mappingSupervisedCheckboxId.setDisable(true);
    	mappingSelectAllCheckboxId.setDisable(true);
    	
    	saveNeuralNetworkConfiguration();
    }

    /**
     * Final button push to finish all this process.
     */
    @FXML public void mappingApplyAction() {
        saveNeuralNetworkConfiguration();
        saveMappingData();
        mainController.applyNeuralNetworkConfig();
        closeWindow();
    }

    /**
     * Reset all data set to their defaults.
     */
    private void resetDataSet() { 
        dataSetTabId.setDisable(false);
        dataSetNextId.setDisable(true);
        dataSetAttributesId.setDisable(true);
        
        headerLinesId.setText("");
        footerLinesId.setText("");
        separatorId.setText("");
        tableId.getColumns().removeAll(tableId.getColumns());
    }

    /**
     * Reset all network configuration to their defaults.
     */
    private void resetNetwork() { 
        mainController.resetNeuralNetworkConfiguration();
        mainController.resetMappingSelections();

        neuralNetworkTopologyId.setValue(FIRST_SELECTION_DESCRIPTION);
        neuralNetworkTopologyLabelId.setText("");
        neuronInputLayerAmountId.setText("");
        neuronHiddenLayerNumberId.setText("");
        neuronOutputLayerAmountId.setText("");

        activationFunctionSelectionId.valueProperty().removeListener(activationFunctionChangeListener);
        activationFunctionSelectionId.getItems().clear();
        activationFunctionSelectionId.getItems().addAll(mainController.getActivationFunctionList());
        activationFunctionSelectionId.setValue(FIRST_SELECTION_DESCRIPTION);
        activationFunctionSelectionId.valueProperty().addListener(activationFunctionChangeListener);
    }
    
    /**
     * Reset all mapping to their defaults.
     */
    private void resetMapping() { 
        mainController.resetMappingSelections();

        mappingApplyId.setDisable(true);

        // Initialise the Mapping combo selections.
        mappingSelectorInOutId.getItems().clear();
        mappingSelectorInOutId.getItems().addAll(MAPPING_SELECTOR_INPUT);
        mappingSelectorInOutId.getSelectionModel().select(FIRST_SELECTION_DESCRIPTION);
        mappingSelectorLabelId.setAlignment(Pos.CENTER);
        mappingOutVBoxId.setVisible(false);

        // Reset all mapping values to their initial defaults.
        mappingSelectAllCheckboxId.selectedProperty().set(false);
        mappingSupervisedCheckboxId.selectedProperty().set(false);
    }


    /**
     * Validate input to only numbers.
     * 
     * @param keyEvent
     */
    @FXML public void validateIntegerAction(KeyEvent keyEvent) {
        // Only digits and not decimals are accepted, all the rest is consumed.
        if ( ! "0123456789".contains(keyEvent.getCharacter()) ) keyEvent.consume();
    }
    
    /**
     * Validate input to only numbers.
     * 
     * @param keyEvent
     */
    public void validateDoubleAction(KeyEvent keyEvent) {
        // Only digits and decimals are accepted, all the rest is consumed.
        if ( ! "0123456789.".contains(keyEvent.getCharacter()) ) keyEvent.consume();
    }
    
    /**
     * Applying the set data set attributes.
     */
    @SuppressWarnings("unchecked")
    @FXML public void dataSetAttributesAction() {
        Integer headerLines = 0;
        Integer footerLines = 0;
        String separator = "";
        if ( headerLinesId.getText().length() > 0 ) headerLines = Integer.parseInt(headerLinesId.getText());
        if ( footerLinesId.getText().length() > 0 ) footerLines = Integer.parseInt(footerLinesId.getText());
        if ( separatorId.getText().length() > 0 ) separator = separatorId.getText();
        mainController.setDataSetAttributes(headerLines, footerLines, separator);

        // Clean table and add newly processed columns
        tableId.getColumns().removeAll(tableId.getColumns());
        tableId.getItems().removeAll(tableId.getItems());
        tableId.getColumns().addAll(mainController.getDataSetTableColumnHeader());
        for( ObservableList<String> row : mainController.getDataSetTableRows(MAX_DATASET_ROWS) ) {
            tableId.getItems().addAll(row);
        }
        
        dataSetNextId.setDisable(false);
    }
    
    /**
     * Selecting a new data set file.
     */
    @FXML public void selectDataSetAction() {
        File selectedFile = ApplicationDialogFactory.openFile(mainController, "Data Set", EXTENSION_LIST);
        if ( selectedFile != null ) {
        	resetDataSet();
        	resetNetwork();
        	neuralNetworkTabId.setDisable(true);
        	resetMapping();
        	mappingTabId.setDisable(true);
            mainController.setDataSetFile(selectedFile);
            dataSetFileId.setText(selectedFile.getName());
            dataSetAttributesId.setDisable(false);
        } 
    }
    
    /**
     * If escape is pressed, close the window.
     * 
     * @param keyEvent
     */
    @FXML public void keyTypedAction(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            closeWindow();
        }
    }

    /**
     * When the select button is pressed.
     */
    @FXML public void neuralNetworkTopologySelectionAction() {
        activationFunctionSelectionId.valueProperty().removeListener(activationFunctionChangeListener);
        activationFunctionSelectionId.getItems().clear();
        activationFunctionSelectionId.getItems().addAll(mainController.getActivationFunctionList());
        activationFunctionSelectionId.setValue(FIRST_SELECTION_DESCRIPTION);
        activationFunctionSelectionId.valueProperty().addListener(activationFunctionChangeListener);
        
        neuronInputLayerAmountId.clear();
        neuronHiddenLayerNumberId.clear();
        neuronOutputLayerAmountId.clear();
        neuralNetworkTopologyLabelId.setVisible(false); // Hide description
        topologySetupId.setVisible(true); // Show setup group
        hiddenLayerSetupPaneId.setVisible(false); // Set hidden layer invisible
        neuronInputLayerAmountId.setText(mainController.getHeaderColumns().size()+"");
        if ( isNeuralNetworkBasicConditionMet() ) {
            neuralNetworkNextId.setDisable(false);
        }
        else {
            neuralNetworkNextId.setDisable(true);
        }
    }
    
    /**
     * When the selection is made, update the description panel.
     */
    @FXML public void neuralNetworkTopologySelectedAction() {
        // The neural network topology description.
        neuralNetworkTopologyLabelId.setText(
                mainController.getNeuralNetworkPatternDescription(
                        neuralNetworkTopologyId.getSelectionModel().getSelectedIndex()));

        // If nothing is selected, ensure user selects something first before allowing
        // to progress setting up inputs and other attributes without a network topology.
        if ( neuralNetworkTopologyId.getSelectionModel().getSelectedIndex() >= 0 ) 
            neuralNetworkTolopogySelectId.setDisable(false);

        neuralNetworkTopologyLabelId.setVisible(true);
        topologySetupId.setVisible(false);
        hiddenLayerSetupPaneId.setVisible(false);
        mainController.setNetworkTopology(neuralNetworkTopologyId.getSelectionModel().getSelectedItem());
    }

    /**
     * Calling the Neural Network Apply action.
     * 
     * @param event
     */
    private void neuralNetworkApplyAction(Event event) {
        neuralNetworkApplyAction();
    }
    
    /**
     * The apply button which will check if all details have been filled.
     */
    @FXML public void neuralNetworkApplyAction() {
        // Get the current text.
        String text = neuronHiddenLayerNumberId.getText();
        
        if ( text == null ) return;
        if ( text.length() == 0 ) return;

        // The current value.
        int value = 0;
        
        // Only parse if anything at all to parse.
        if ( text.length() > 0 ) value = Integer.parseInt(text);

        if ( value >= 0 ) {
            // Cap the value if it is higher than maximum allowed
            int numberOfLayers = value > MAX_HIDDEN_LAYERS ? MAX_HIDDEN_LAYERS : value;
            if ( value > MAX_HIDDEN_LAYERS ) neuronHiddenLayerNumberId.setText(numberOfLayers+"");
            
            // Update the widget to show the hidden layer details.
            neuralNetworkHiddenLayerDetails();
        }
        else {
            // Hiding from the user all hidden layer detail questions.
            neuralNetworkTopologyLabelId.setVisible(false);
            topologySetupId.setVisible(true);
            hiddenLayerSetupPaneId.setVisible(false);
            neuronInputLayerAmountId.setText(mainController.getHeaderColumns().size()+"");
            neuronInputLayerAmountId.setEditable(true);
       }

        if ( isNeuralNetworkBasicConditionMet() ) {
            neuralNetworkNextId.setDisable(false);
        }
        else {
            neuralNetworkNextId.setDisable(true);
        }
    }
    
    /**
     * If any of the required conditions are not met, this
     * will return false. Only when all are met, this returns false.
     * 
     * @return true if conditions are all met
     */
    private boolean isNeuralNetworkBasicConditionMet(){
        // Input neurons not set
        if ( neuronInputLayerAmountId.getText().length() == 0 ) return false;
        // Hidden layers not set
        if ( neuronHiddenLayerNumberId.getText().length() == 0 ) return false;
        // Output neurons not set
        if ( neuronOutputLayerAmountId.getText().length() == 0 ) return false;
        // Output neurons cannot be zero
        if ( Integer.parseInt(neuronOutputLayerAmountId.getText()) == 0 ) return false;
        
        // Input neurons selected zero, set to 1 and carry on.
        if ( Integer.parseInt(neuronInputLayerAmountId.getText()) == 0 )
            neuronInputLayerAmountId.setText("1");
        // Input neurons selected more than allowed, set to max and carry on.
        if ( Integer.parseInt(neuronInputLayerAmountId.getText()) > mainController.getHeaderColumns().size() )
            neuronInputLayerAmountId.setText(mainController.getHeaderColumns().size()+"");
        
        // Activation Function not set
        if ( activationFunctionSelectionId.getSelectionModel().getSelectedItem()
                .equals(FIRST_SELECTION_DESCRIPTION) ) return false;

        if ( Integer.parseInt(neuronHiddenLayerNumberId.getText()) > 0 ) {
        	int requestedHiddenLayers = Integer.parseInt(neuronHiddenLayerNumberId.getText());
        	int populatedHiddenLayers = mainController.getCountNonZeroHiddenLayers();
        	if ( requestedHiddenLayers != populatedHiddenLayers ) return false;
        }

        return true;
    }

    /**
     * The mapping selection for input, output, supervised and unsupervised 
     * selections, after which, the appropriate list of neural network inputs 
     * or outputs to map will be displayed with allowed actions.
     */
    @FXML public void mappingSelectedAction() {
        // with any previously set data. This keeps the front end updated with
        // all data the user may have inserted already for inputs and or outputs.
        refreshMappingTopVisuals();

        mappingSupervisedCheckboxId.setDisable(false);
        mappingSelectAllCheckboxId.setDisable(false);
        mappingOutVBoxId.setVisible(true);

        if ( mappingSelectorInOutId.getValue() != null && 
                mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT) ) {
            // Update the visuals for the input sub-options
            showMappingInputOptions();
        }
        else if ( mappingSelectorInOutId.getValue() != null &&
                mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_OUTPUT) ) {
            // Update the visuals for the output sub-options
            showMappingOutputOptions();
        }
    }

    /**
     * The select all check box action to apply the effect on all required views.
     */
    @FXML public void mappingSelectAllCheckboxAction() {
        boolean inputSettingValue = false;

        // Ensure all inputs are selected and then refresh the input options.
        if ( mappingSelectAllCheckboxId.isSelected() ) inputSettingValue = true;

        for( int index = 0 ; index < mainController.getHeaderColumns().size(); index++ ) 
               mainController.setInputMapDataSetSelection(index, inputSettingValue, mappingSelectorLabelId);
        
        showMappingInputOptions();
    }
    
    /**
     * The supervised check box action to apply the effect on
     * all required views.
     */
    @FXML public void mappingSupervisedCheckboxAction() {
        if ( mappingSupervisedCheckboxId.isSelected() ) {
            // Supervised: Show both input and output
            String currentValue = mappingSelectorInOutId.getValue();
            if ( currentValue == null ) currentValue = MAPPING_SELECTOR_INPUT;
            if ( currentValue == FIRST_SELECTION_DESCRIPTION ) currentValue = MAPPING_SELECTOR_INPUT;
            mappingSelectorInOutId.getItems().clear();
            mappingSelectorInOutId.getItems().addAll(MAPPING_SELECTOR_INPUT, MAPPING_SELECTOR_OUTPUT);
            mappingSelectorInOutId.getSelectionModel().select(currentValue);
        }
        else {
            // Unsupervised: Lock selector to Input only
            mappingSelectorInOutId.getItems().clear();
            mappingSelectorInOutId.getItems().addAll(MAPPING_SELECTOR_INPUT);
        }
        mappingSelectorLabelId.setAlignment(Pos.CENTER);
    }

    /**
     * Setting the top mapping controls to be seen, hidden, or altered, 
     * depending on the selections made by user. 
     */
    private void refreshMappingTopVisuals() {
        // Select all check box to be shown only for the input
        if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT) ) {
            mappingSelectAllCheckboxId.setVisible(true);
        }
        else {
            mappingSelectAllCheckboxId.setVisible(false);
        }
        
        // Sub window setup depending on the selection made.
        if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT) )
            showMappingInputOptions();
        if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_OUTPUT) )
            showMappingOutputOptions();
        if (mappingSelectorLabelId.getText().equals(INFO_SELECTION_REQUIREMENTS_MET))
        	mappingApplyId.setDisable(false);
    }

    /**
     * The input subset of options.
     */
    private void showMappingInputOptions() {
        mappingPaneInputId.setVisible(true);
        mappingPaneOutputId.setVisible(false);
        mappingPaneInputId.getChildren().clear();
        
        // Building the view.
        GridPane gridpane = new GridPane();
        gridpane.setVgap(5);
        gridpane.setPrefWidth(WINDOW_WIDTH-50);
        gridpane.setPrefHeight(320);
        gridpane.setAlignment(Pos.CENTER);

        // Get number of total header columns existing as options to pick from.
        int numberOfDataSetColumns = mainController.getHeaderColumns().size();
        // Total number of cells to display.
        int maxCells = numberOfDataSetColumns;
        // Starting data set column.
        int indexId = 0;

        inputMapDataSetSelection = mainController.getInputMapDataSetSelection();
        // Plotting the list of check boxes with listeners, one per data set available column.
        for ( int row = 0; row <= numberOfDataSetColumns/MAX_INPUT_COLUMNS ; row++ ) {
            for ( int column = 0; column < MAX_INPUT_COLUMNS; column++ ) {
                CheckBox cb = new CheckBox(""+(indexId+1));
                cb.setId(""+indexId);
                cb.setSelected(inputMapDataSetSelection.get(indexId));
                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> observableValue, 
                            Boolean oldValue, Boolean newValue) {
                        mainController.setInputMapDataSetSelection(
                        		Integer.parseInt(cb.getId()), 
                        		newValue, mappingSelectorLabelId);
                    }
                });

                gridpane.add( cb, column, row );
                indexId++;
                maxCells--;
                if ( maxCells <= 0 ) break;
            }
            if ( maxCells <= 0 ) break;
        }
        VBox vBox = new VBox();
        vBox.setPrefWidth(WINDOW_WIDTH-50);
        vBox.setPrefHeight(320);
        vBox.getChildren().add(gridpane);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        mappingPaneInputId.getChildren().add(vBox);
    }


    /**
     * The output mapping between the data set and the neural network outputs.
     * User is able to select any of the left data set columns not yet linked
     * to any input, and link it to a neural network output after applying an
     * optional function to match expectations.<p>
     * 
     * This link will only be used for supervised training and testing. For 
     * unsupervised neural network configurations, this output mapping will 
     * not be activated. 
     */
    private void showMappingOutputOptions() {
        // Switch pane views.
        mappingPaneInputId.setVisible(false);
        mappingPaneOutputId.setVisible(true);

        // Reset VBox with a new GridPane
        GridPane gridpane = new GridPane();
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        gridpane.setPrefWidth(WINDOW_WIDTH-75);
        gridpane.setPrefHeight(300);
        gridpane.setAlignment(Pos.TOP_LEFT);
        mappingOutVBoxId.getChildren().clear();
        mappingOutVBoxId.setPrefWidth(WINDOW_WIDTH-50);
        mappingOutVBoxId.setPrefHeight(300);
        mappingOutVBoxId.setAlignment(Pos.CENTER_RIGHT);
        mappingOutVBoxId.setPadding(new Insets(5, 5, 5, 5));
        mappingOutVBoxId.getChildren().add(gridpane);

        int outputLayerSize = Integer.parseInt(neuronOutputLayerAmountId.getText());
        List<String> outputLayerIds = new LinkedList<>();
        for ( int outputId = 1; outputId <= outputLayerSize; outputId++ ) outputLayerIds.add(""+outputId);

        // 1 - Show all neural network output ids that may require a function.
        // [Neuron Output label] [Data set input] [Function] [optional value]
        for( int outputId = 1; outputId <= outputLayerSize ; outputId++ ) {
            // Data Set input drop down
            ComboBox<String> inputIdDropDown = new ComboBox<>();
            inputIdDropDown.getItems().addAll(outputLayerIds);
            inputIdDropDown.setValue(""+outputId);
            inputIdDropDown.setId(""+outputId);

            // Output Neuron label
            Label outputIdLabel = new Label("->"+(outputId));
            outputIdLabel.setId("L"+outputId);

            // Function options drop down
            ComboBox<String> functionDropDown = new ComboBox<>();
            functionDropDown.setPrefSize(150, 25);
            functionDropDown.getItems().add(NO_FUNCTION_DESCRIPTION);
            for ( String activationFunctionName : mainController.getOperatorsList() ) {
                functionDropDown.getItems().add(activationFunctionName);
            }

            if ( mainController.getOutputFunction(outputId) != null ) {
                functionDropDown.setValue(mainController.getOutputFunction(outputId).getMathOperatorKey());
            }
            else {
                functionDropDown.setValue(NO_FUNCTION_DESCRIPTION);
            }
            functionDropDown.setId(""+outputId);

            // Adding listeners to function selection changes to let 
            // the controller know about these.
            functionDropDown.valueProperty().addListener(new ChangeListener<String>() {
                @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    applyExtraFunctionOptions(functionDropDown, newValue);
                }
            });

            Label functionDescriptionLabel = new Label();
            functionDescriptionLabel.setMaxWidth(350);
            functionDescriptionLabel.setId("F"+outputId);

            TextField initialValue = new TextField();
            initialValue.setVisible(false); // Not showing initially
            initialValue.setMaxWidth(50);
            initialValue.addEventHandler(KeyEvent.KEY_TYPED, this::validateDoubleAction);
            initialValue.setId(""+outputId);

            if ( mainController.getOutputFunction(outputId) != null ) {
                // The two functions that do not expect user input.
            	String mathOperatorKey = mainController.getOutputFunction(outputId).getMathOperatorKey();
                if ( !mathOperatorKey.equals(MathOperatorFactory.getName(MathOperatorKey.INV)) & 
                     !mathOperatorKey.equals(MathOperatorFactory.getName(MathOperatorKey.BIN)) &
                     !mathOperatorKey.equals(NO_FUNCTION_DESCRIPTION)) {
                	initialValue.setVisible(true); // Showing if function requires it.
                }

                initialValue.setText(mainController.getOutputFunctionSelectionValue(outputId)+"");

                if ( ! mathOperatorKey.equals(NO_FUNCTION_DESCRIPTION) ) {
                    MathOperatorCore<?> mathOperator = MathOperatorFactory.getMathOperation(mathOperatorKey);
                    functionDescriptionLabel.setText(mathOperator.getDescription());
                }
            }

            gridpane.add(inputIdDropDown,          0, outputId); // Data Set column
            gridpane.add(outputIdLabel,            1, outputId); // Neural network output neuron
            gridpane.add(functionDropDown,         2, outputId); // Optional function
            gridpane.add(initialValue,             3, outputId); // Initial bias value
            gridpane.add(functionDescriptionLabel, 4, outputId); // Short function description

            // Keep this close to the above gridPane, to set the total number
            // of columns we have in in.
            TOTAL_COLUMNS_MAPPING_OUTPUT = 5;
        }
    }


    /**
     * Mapping Output extra function options hidden / shown where applicable.
     * 
     * @param functionDropDown ComboBox String
     * @param mathOperatorKey String
     */
    private void applyExtraFunctionOptions(ComboBox<String> functionDropDown, String mathOperatorKey) {
        // The input text box
        GridPane gridPane = (GridPane) functionDropDown.getParent();
        int outputId = Integer.parseInt(functionDropDown.getId());
        int rowId = outputId - 1;

        @SuppressWarnings("unchecked")
		ComboBox<String> dropBoxInput = (ComboBox<String>) gridPane.getChildren().get(rowId*TOTAL_COLUMNS_MAPPING_OUTPUT+0);
        int inputId = Integer.parseInt(dropBoxInput.getSelectionModel().getSelectedItem());
        TextField functionValue = (TextField) gridPane.getChildren().get(rowId*TOTAL_COLUMNS_MAPPING_OUTPUT+3);
        Label description = (Label) gridPane.getChildren().get(rowId*TOTAL_COLUMNS_MAPPING_OUTPUT+4);
        description.setText("");
        functionValue.clear();
        
        // The two functions that do not expect user input.
        if ( mathOperatorKey.equals(MathOperatorFactory.getName(MathOperatorKey.INV)) | 
             mathOperatorKey.equals(MathOperatorFactory.getName(MathOperatorKey.BIN)) |
             mathOperatorKey.equals(NO_FUNCTION_DESCRIPTION)) {

            functionValue.setVisible(false);
        }
        else {
            functionValue.setText("0.0");

            if ( mathOperatorKey.equals(MathOperatorFactory.getName(MathOperatorKey.DIV))) {
                functionValue.setText("1.0");
            }

            functionValue.setVisible(true);
        }
        
        if ( ! mathOperatorKey.equals(NO_FUNCTION_DESCRIPTION) ) {
            MathOperatorCore<?> mathOperator = MathOperatorFactory.getMathOperation(mathOperatorKey);
            description.setText(mathOperator.getDescription());
        }

        functionValue.removeEventFilter(KeyEvent.KEY_RELEASED, this::functionTextChanged);
        functionValue.addEventHandler(KeyEvent.KEY_RELEASED, this::functionTextChanged);

        mainController.setOutputFunction(inputId, outputId, mathOperatorKey, functionValue.getText());
    }
    
    /**
     * Function initial value changed.
     * 
     * @param keyEvent
     */
    @FXML public void functionTextChanged(KeyEvent keyEvent) {
    	TextField textField = (TextField) keyEvent.getSource();
 
    	String outputText = textField.getId();
    	String text = textField.getText();
    	
    	double initialValue = 0.0;
    	if ( text.length() > 0 ) initialValue = Double.parseDouble(text);

    	int outputId = 0;
    	if ( outputText.length() > 0 ) outputId = Integer.parseInt(outputText);
    	
        mainController.setOutputFunction(outputId, initialValue);
    }
    
    /**
     * Asking user for further details on the hidden layers for the 
     * network topology selected.
     */
    private void neuralNetworkHiddenLayerDetails() {
        neuralNetworkTopologyLabelId.setVisible(false);
        topologySetupId.setVisible(true);
        hiddenLayerSetupPaneId.getChildren().clear();

        mainController.resetNeuralNetworkConfiguration();
        
        int column = 0;
        int row = 0;
        int numberOfHiddenLayers = Integer.parseInt(neuronHiddenLayerNumberId.getText());
        GridPane gridpane = new GridPane();
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        gridpane.setPrefWidth(WINDOW_WIDTH-40);
        gridpane.setPrefHeight(195);
        gridpane.setAlignment(Pos.TOP_CENTER);

        for( int hiddenLayerId = 1; hiddenLayerId <= numberOfHiddenLayers; hiddenLayerId++) {
        
            String id = hiddenLayerId+"";
            
            Label newLabel = new Label("L"+hiddenLayerId+": ");
            newLabel.setId(id);
            newLabel.setMaxWidth(75);
            
            TextField newText = new TextField();
            newText.setId(id);
            newText.setMaxWidth(55);

            // Validate input
            newText.addEventHandler(KeyEvent.KEY_TYPED, this::validateIntegerAction);

            // Check if all is now filled to show the next button
            newText.addEventHandler(KeyEvent.KEY_RELEASED, this::checkAllNeuralNetworkConfigFilled);

            // Add value to controller
            newText.addEventHandler(KeyEvent.KEY_RELEASED, this::addHiddenLayerInfoToController);

            gridpane.add(newLabel, column, row);  // Neural network hidden layer #
            gridpane.add(newText, column+1, row); // Requested neuron amount for layer

            column+=2;
            if ( hiddenLayerId%5 == 0 ) {
                column = 0;
                row++;
            }
        }

        VBox vBox = new VBox();
        vBox.setPrefWidth(WINDOW_WIDTH-40);
        vBox.setPrefHeight(195);
        vBox.getChildren().add(gridpane);
        vBox.setAlignment(Pos.TOP_CENTER);
        hiddenLayerSetupPaneId.getChildren().add(vBox);
        hiddenLayerSetupPaneId.setVisible(true);
        hiddenLayerSeparatorId.setVisible(false);
    }
    
    /**
     * Setting up the hidden layers with the controller.
     * 
     * @param event KeyEvent
     */
    public void addHiddenLayerInfoToController(KeyEvent event) {
    	TextField data = (TextField) event.getSource();
    	int layerId = Integer.parseInt(data.getId());
    	int size = 0;
    	if ( data.getText().length() > 0 ) {
    		size = Integer.parseInt(data.getText());
    	}
        mainController.setHiddenLayerSizes(layerId, size);
    }

    /**
     * Checking if all hidden neuron layers have been filled with an amount
     * and updating the final list to be submitted.
     * 
     * @param event KEY_RELEASED event
     */
    private void checkAllNeuralNetworkConfigFilled(Event event) {
        if ( isNeuralNetworkBasicConditionMet() ) {
            // Allowing user to progress to mapping if all is ok
            neuralNetworkNextId.setDisable(false);
        }
        else {
            // Ensuring next button is disabled if ok status changes to not ok.
            neuralNetworkNextId.setDisable(true);
        }
    }


    /**
     * Giving back to the controller Neural Network configurations supplied.
     */
    private void saveNeuralNetworkConfiguration() {
// TODO: Give main controller the Network Topology selected

        // Supply the input neuron amount to the controller.
        mainController.setInputLayerSize(Integer.parseInt(neuronInputLayerAmountId.getText()));

        // Supply the output neuron amount to the controller.
        mainController.setOutputLayerSize(Integer.parseInt(neuronOutputLayerAmountId.getText()));
        
        // Activation Function already saved at the selection point.
        // Hidden layers supplied on the fly to the controller.
    }

    /**
     * Giving back to the controller mapping data supplied.
     */
    private void saveMappingData() {
    	System.out.println(mainController.toString());
// TODO: Give main controller the dataset columns for NN input.
        // if supervised:
// TODO: Give main controller the dataset columns for NN output.
// TODO: Give main controller the function selection per column.
    }
    
    /**
     * Closing the window and lose any changes made without warning.
     */
    private void closeWindow() {
        Stage stage = (Stage) tabPaneId.getScene().getWindow();
        stage.close();
    }
}
