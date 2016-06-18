package app.view.menu.neuralNetwork.fxml;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.controller.FXMLController;
import app.view.ApplicationDialogFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    @FXML private TabPane tabPaneId;
    @FXML private Tab dataSetTabId;
    @FXML private Tab neuralNetworkTabId;
    @FXML private Tab mappingTabId;

    @FXML private Button dataSetSelectedId;
    @FXML private Label dataSetFileId;
    @FXML private TextField headerLinesId;
    @FXML private TextField footerLinesId;
    @FXML private TextField separatorId;

    /**
     * Data set file extension list accepted.
     */
    private final List<Map<String,String>> EXTENSION_LIST;

    /**
     * The main controller.
     */
    private final Controller mainController;

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
    @FXML public void dataSetAttributesAction() {
    	Integer headerLines = Integer.parseInt(headerLinesId.getText());
    	Integer footerLines = Integer.parseInt(footerLinesId.getText());
    	String separator = separatorId.getText();
    	mainController.setDataSetAttributes(headerLines, footerLines, separator);
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
     * Closing the window and lose any changes made without warning.
     */
    private void closeWindow() {
    	Stage stage = (Stage) tabPaneId.getScene().getWindow();
        stage.close();
    }
}
