package app.view.menu.neuralNetwork.fxml;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.controller.FXMLController;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.view.ApplicationDialogFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    @FXML private Button dataSetSelectedId;
    @FXML private Label dataSetFileId;
    @FXML private TextField headerLinesId;
    @FXML private TextField footerLinesId;
    @FXML private TextField separatorId;
    @SuppressWarnings("rawtypes")
    @FXML private TableView<ObservableList> tableId;

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
    @FXML private VBox hiddenLayerSetupVBoxId;
    @FXML private Button neuralNetworkApplyId;
    
    /**
     * Mapping tab group
     */
    @FXML private ComboBox<String> mappingSelectorInOutId;
    @FXML private Pane mappingPaneInputId;
    @FXML private Pane mappingPaneOutputId;
    @FXML private Label mappingSelectorLabelId;
    @FXML private CheckBox mappingSelectAllCheckboxId;
    @FXML private CheckBox mappingSupervisedCheckboxId;
    
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
    private final int MAX_HIDDEN_LAYERS = 256;
    
    /**
     * The String for the input mapping selector.
     */
    private final String MAPPING_SELECTOR_INPUT = "Input Mapping";

    /**
     * The String for the output mapping selector to be used for training.
     */
    private final String MAPPING_SELECTOR_OUTPUT = "Output Mapping";
    
    /**
     * The text for the input mapping.
     */
    private final String MAPPING_TEXT_INPUT = 
    		"Please select data set columns to feed neural network inputs.";
    
    /**
     * The text for the output mapping.
     */
    private final String MAPPING_TEXT_OUTPUT = 
    		"Please select data set columns to supervise neural network outputs.";
    
    /**
     * Initialising the various details and selections.
     */
    public void initialize() {
        // Initialise the Neural Network pattern lists available to use.
        neuralNetworkTopologyId.getItems().addAll(mainController.getNeuralNetworkPatternList());
        neuralNetworkTopologyId.setValue(FIRST_SELECTION_DESCRIPTION);

        // Initialise the Activation Functions available to use.
        activationFunctionSelectionId.getItems().addAll(mainController.getActivationFunctionList());
        activationFunctionSelectionId.setValue(FIRST_SELECTION_DESCRIPTION);

        // Initialise the Mapping combo selections.
        mappingSelectorInOutId.getItems().addAll(MAPPING_SELECTOR_INPUT, MAPPING_SELECTOR_OUTPUT);
        mappingSelectorInOutId.setValue(FIRST_SELECTION_DESCRIPTION);
		mappingSelectorLabelId.setAlignment(Pos.CENTER);
        
        setNeuralNetworkStateOne();
    }
    
    
    /**
     * Constructor to instantiate the required main controller.
     * 
     * @param mainController
     */
    public NeuralNetworkConfigurationFXMLController(Controller mainController) {
        this.mainController = mainController;
        this.EXTENSION_LIST = new LinkedList<>();
        Map<String,String> extension = new HashMap<>();
        extension.put("Data Set Files", "*.*");
        EXTENSION_LIST.add(extension);
    }
        
    @FXML public void dataSetNextAction() {
        neuralNetworkTabId.getTabPane().getSelectionModel().select(neuralNetworkTabId);
    }
    
    @FXML public void neuralNetworkPreviousAction() {
        dataSetTabId.getTabPane().getSelectionModel().select(dataSetTabId);
    }
    
    @FXML public void neuralNetworkNextAction() {
        mappingTabId.getTabPane().getSelectionModel().select(mappingTabId);
    }
    
    @FXML public void mappingPreviousAction() {
        mappingTabId.getTabPane().getSelectionModel().select(neuralNetworkTabId);
    }
    
    @FXML public void mappingApplyAction() {
        closeWindow();
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
        tableId.getColumns().addAll(mainController.getDataSetColumnHeader());
        for( ObservableList<String> row : mainController.getDataSetDataRows() ) {
            tableId.getItems().addAll(row);
        }
    }
    
    /**
     * Selecting a new data set file.
     */
    @FXML public void selectDataSetAction() {
        File selectedFile = ApplicationDialogFactory.openFile(mainController, "Data Set", EXTENSION_LIST);
        if ( selectedFile != null ) {
            mainController.setDataSetFile(selectedFile);
            dataSetFileId.setText(selectedFile.getName());
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
    	neuronInputLayerAmountId.clear();
    	neuronHiddenLayerNumberId.clear();
    	neuronOutputLayerAmountId.clear();
    	activationFunctionSelectionId.setValue(FIRST_SELECTION_DESCRIPTION);
    	setNeuralNetworkStateTwo();
    }
    
    /**
     * When the selection is made, update the description panel.
     */
    @FXML public void neuralNetworkTopologySelectedAction() {
        neuralNetworkTopologyLabelId.setText(
                mainController.getNeuralNetworkPatternDescription(
                        neuralNetworkTopologyId.getSelectionModel().getSelectedIndex()));
        setNeuralNetworkStateOne();
    }

    /**
     * The mapping selection for input, output, supervised and unsupervised 
     * selections, after which, the appropriate list of neural network inputs 
     * or outputs to map will be displayed with allowed actions.
     */
    @FXML public void mappingSelectedAction() {
    	if ( mappingSelectorInOutId.getValue() != null && 
    			mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT) ) {
    		mappingSelectorLabelId.setText(MAPPING_TEXT_INPUT);
    		showMappingInputWidget();
    	}
    	else if ( mappingSelectorInOutId.getValue() != null &&
    			mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_OUTPUT) ) {
    		mappingSelectorLabelId.setText(MAPPING_TEXT_OUTPUT);
    		showMappingOutputWidget();
    	}
    }
    
    /**
     * The apply button which will check if all details have been filled.
     */
    @FXML public void neuralNetworkApplyAction() {
    	// Get the current text.
        String text = neuronHiddenLayerNumberId.getText();

        // The current value.
        int value = 0;
        
        // Only parse if anything at all to parse.
        if ( text.length() > 0 ) value = Integer.parseInt(text);
    	
        if ( value > 0 ) {
        	// Cap the value if it is higher than maximum allowed
    		int numberOfLayers = value > MAX_HIDDEN_LAYERS ? MAX_HIDDEN_LAYERS : value;
    		if ( value > MAX_HIDDEN_LAYERS ) neuronHiddenLayerNumberId.setText(numberOfLayers+"");
    		
    		// Update the widget to show the hidden layer details.
    		setNeuralNetworkStateThree(numberOfLayers);
    	}
    	else {
    		// Hiding from the user all hidden layer detail questions.
    		setNeuralNetworkStateTwo();
    	}
    }

    /**
     * The select all check box action to apply the effect on 
     * all required views.
     */
    @FXML public void mappingSelectAllCheckboxAction() {
    	// TODO - For now if anything changes, just call the show business.
    	if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT)) {
        	showMappingInputWidget();
    	}
    	else if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_OUTPUT)) {
        	showMappingOutputWidget();
    	}
    	else {
        	// Else ignore
    	}
    }
    
    /**
     * The supervised check box action to apply the effect on
     * all required views.
     */
    @FXML public void mappingSupervisedCheckboxAction() {
    	// TODO - For now if anything changes, just call the show business.
    	if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_INPUT)) {
        	showMappingInputWidget();
    	}
    	else if ( mappingSelectorInOutId.getValue().equals(MAPPING_SELECTOR_OUTPUT)) {
        	showMappingOutputWidget();
    	}
    	else {
        	// Else ignore
    	}
    }
    
    
    
    

    private void showMappingInputWidget() {
    	mappingPaneInputId.setVisible(true);
    	mappingPaneOutputId.setVisible(false);
    	mappingPaneInputId.getChildren().clear();

        GridPane gridpane = new GridPane();
        gridpane.setHgap(5);
        gridpane.setVgap(5);
    	gridpane.setPrefWidth(472);
    	gridpane.setPrefHeight(320);
//        gridpane.setMaxWidth(GridPane.USE_PREF_SIZE);
//        gridpane.setMaxHeight(GridPane.USE_PREF_SIZE);
        gridpane.setAlignment(Pos.CENTER);
//        gridpane.set`
//        for (int i = 0; i < 10; i++) {
//            ColumnConstraints column = new ColumnConstraints(25);
//            gridpane.getColumnConstraints().add(column);
//        }
        // TODO: Need to know how many columns data set will have.
        int dataSetColumns = mainController.getDataSetColumns().size();
        dataSetColumns = 128;
        int maxColumns = 10;
        int maxCells = dataSetColumns;
        int columnId = 1;
        for ( int row = 0; row <= dataSetColumns/maxColumns ; row++ ) {
        	for ( int column = 0; column < maxColumns; column++ ) {
        		CheckBox cb = new CheckBox(""+columnId);
        		cb.setSelected(mappingSelectAllCheckboxId.isSelected());
            	gridpane.add( cb, column, row);
            	columnId++;
            	maxCells--;
            	if ( maxCells <= 0 ) break;
        	}
        	if ( maxCells <= 0 ) break;
        }
        
       
    	VBox vBox = new VBox();
    	vBox.setPrefWidth(472);
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
     * This link will be used for supervised training and also for testing the
     * training outcome. For unsupervised neural network configurations, this 
     * output mapping will serve to apply selected optional functions to the 
     * outcome of the neural network outputs. 
     */
    private void showMappingOutputWidget() {
    	mappingPaneInputId.setVisible(false);
    	mappingPaneOutputId.setVisible(true);
    	
    	// TODO: Do not clear. Read what was already set, and show it to user.
    	mappingPaneOutputId.getChildren().clear();

    	if ( mappingSupervisedCheckboxId.isSelected() ) {
        	// 1 - Pick up the data set columns that were not selected for input
        	//     and display these on each drop down.
        	// 2 - On the right of each drop down, show the neural network output 
        	//     id that will be linked to.
        	// 3 - On the right of the neural network output id, show a drop down
        	//     with all the available functions that can be applied.
            GridPane gridpane = new GridPane();
            gridpane.setHgap(5);
            gridpane.setVgap(5);
        	gridpane.setPrefWidth(472);
        	gridpane.setPrefHeight(320);
            gridpane.setAlignment(Pos.CENTER);

            // TODO: Need to know how many columns data set will have.
            int dataSetColumns = mainController.getDataSetColumns().size();
            dataSetColumns = 128;
            int maxColumns = 10;
            int maxCells = dataSetColumns;
            int columnId = 1;
            for ( int row = 0; row <= dataSetColumns/maxColumns ; row++ ) {
            	for ( int column = 0; column < maxColumns; column++ ) {
            		CheckBox cb = new CheckBox(""+columnId);
            		cb.setSelected(mappingSelectAllCheckboxId.isSelected());
                	gridpane.add( cb, column, row);
                	columnId++;
                	maxCells--;
                	if ( maxCells <= 0 ) break;
            	}
            	if ( maxCells <= 0 ) break;
            }
        	VBox vBox = new VBox();
        	vBox.setPrefWidth(472);
        	vBox.setPrefHeight(320);
        	vBox.getChildren().add(gridpane);
        	vBox.setAlignment(Pos.CENTER_RIGHT);
        	mappingPaneOutputId.getChildren().add(vBox);
    	}
    	else {
        	// 1 - Show the neural network output id that may require a function.
        	// 2 - On the right of the neural network output id, show a drop down
        	//     with all the available functions that can be applied.
    		ComboBox<String> functionDropDown = new ComboBox<>();
    		functionDropDown.getItems().addAll(
    				MathOperatorFactory.getName(MathOperatorKey.ADD),
    				MathOperatorFactory.getName(MathOperatorKey.BIN),
    				MathOperatorFactory.getName(MathOperatorKey.DIV),
    				MathOperatorFactory.getName(MathOperatorKey.INV),
    				MathOperatorFactory.getName(MathOperatorKey.MUL),
    				MathOperatorFactory.getName(MathOperatorKey.SUB)
    				);

    		GridPane gridpane = new GridPane();
            gridpane.setHgap(5);
            gridpane.setVgap(5);
        	gridpane.setPrefWidth(472);
        	gridpane.setPrefHeight(320);
            gridpane.setAlignment(Pos.CENTER);
            gridpane.add(functionDropDown, 0, 0);

        	VBox vBox = new VBox();
        	vBox.setPrefWidth(472);
        	vBox.setPrefHeight(320);
        	vBox.getChildren().add(gridpane);
        	vBox.setAlignment(Pos.CENTER_RIGHT);
        	mappingPaneOutputId.getChildren().add(vBox);
    		
    	}
    }
    
    /**
     * Hiding details of the network topology to be selected.
     */
    private void setNeuralNetworkStateOne() {
        neuralNetworkTopologyLabelId.setVisible(true);
        topologySetupId.setVisible(false);
        hiddenLayerSetupVBoxId.setVisible(false);
    	showMappingTabIfOk();
    }

    /**
     * Asking user for the details of the network topology selected.
     */
    private void setNeuralNetworkStateTwo() {
        neuralNetworkTopologyLabelId.setVisible(false);
        topologySetupId.setVisible(true);
        hiddenLayerSetupVBoxId.setVisible(false);
//        if ( neuronHiddenLayerNumberId.getText() != null )
//        setStateAction(null);
//        if ( neuronHiddenLayerNumberId.getText() != null )
//        	setNeuralNetworkStateThree(neuronHiddenLayerNumberId.getText());
    	showMappingTabIfOk();
    }

    /**
     * Asking user for the details of the hidden network topology selected.
     * 
     * @param numberOfHddenLayers int
     */
    private void setNeuralNetworkStateThree(int numberOfHddenLayers) {
        neuralNetworkTopologyLabelId.setVisible(false);
        topologySetupId.setVisible(true);
    	hiddenLayerSetupVBoxId.getChildren().clear();
    	
    	for( int hiddenLayerId = 1; hiddenLayerId <= numberOfHddenLayers; hiddenLayerId++) {
        	String id = hiddenLayerId+"";
        	Label newLabel = new Label("H.Layer #"+hiddenLayerId+": ");
        	TextField newText = new TextField();
        	newLabel.setId(id);
        	newText.setId(id);
        	newText.setMaxWidth(45);
        	newText.addEventHandler(KeyEvent.KEY_TYPED, this::validateIntegerAction);
        	HBox hBox = new HBox(8);
        	hBox.getChildren().addAll(newLabel,newText);
        	hiddenLayerSetupVBoxId.getChildren().add(hBox);
        }

    	hiddenLayerSetupVBoxId.setVisible(true);
    	showMappingTabIfOk();
    }

    /**
     * Set to show the Mapping tab at any point when part of the data
     * to be used to calculate the mapping tab is changed. If all conditions
     * are met, checked by the showMappingIfOk, the map tab will be enabled,
     * or disabled otherwise.
     */
    private void showMappingTabIfOk() {
        if ( neuronInputLayerAmountId.getText().length() > 0 ) {
        	mappingTabId.setDisable(false);
        }
        else {
        	mappingTabId.setDisable(true);
        }
    }
    
    /**
     * Closing the window and lose any changes made without warning.
     */
    private void closeWindow() {
        Stage stage = (Stage) tabPaneId.getScene().getWindow();
        stage.close();
    }
}
