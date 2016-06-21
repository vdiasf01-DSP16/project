package app.view.menu.neuralNetwork.fxml;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.controller.FXMLController;
import app.view.ApplicationDialogFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private final String FIRST_SELECTION_DESCRIPTION = "Please select option";

    /**
     * Initialising the various details and selections.
     */
    public void initialize() {
        // Initialise the Neural Network pattern lists available to use.
        neuralNetworkTopologyId.getItems().addAll(mainController.getNeuralNetworkPatternList());
        neuralNetworkTopologyId.setValue(FIRST_SELECTION_DESCRIPTION);
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
    
    @FXML public void dataSetTabAction() {
    }

    @FXML public void neuralNetworkTabAction() {
    }
    
    @FXML public void mappingTabAction() {
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
        System.out.println("Set");
    }
    
    /**
     * When the selection is made, update the description panel.
     * 
     * @param event
     */
    @FXML public void neuralNetworkTopologySelectedAction() {
        neuralNetworkTopologyLabelId.setText(
                mainController.getNeuralNetworkPatternDescription(
                        neuralNetworkTopologyId.getSelectionModel().getSelectedIndex()));
    }
    
    
    
    /**
     * Closing the window and lose any changes made without warning.
     */
    private void closeWindow() {
        Stage stage = (Stage) tabPaneId.getScene().getWindow();
        stage.close();
    }
}
