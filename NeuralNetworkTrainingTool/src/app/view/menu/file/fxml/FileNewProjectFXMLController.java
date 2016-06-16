package app.view.menu.file.fxml;

import java.io.File;

import app.controller.Controller;
import app.controller.FXMLController;
import app.model.project.ProjectData;
import app.model.project.ProjectDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The Menu File New Project Controller, received data from the user
 * and sends it down through the controller.
 * 
 * @author Vasco
 *
 */
public class FileNewProjectFXMLController implements FXMLController {

    /**
     * The Cancel button.
     */
    @FXML
    private Button projectCancelId;
    
    /**
     * The Create button.
     */
    @FXML
    private Button projectCreateId;
    
    /**
     * The Project name.
     */
    @FXML
    private TextField projectNameId;
    
    /**
     * The Project abstract.
     */
    @FXML
    private TextField projectAbstractId;
    
    /**
     * The Project authors.
     */
    @FXML
    private TextField projectAuthorsId;
    
    /**
     * The Project summary.
     */
    @FXML
    private TextArea projectSummaryId;
    
    /**
     * The Project description.
     */
    @FXML
    private TextArea projectDescriptionId;

    /**
     * The main controller.
     */
    private final Controller mainController;

    /**
     * Constructor to instantiate the required main controller.
     * 
     * @param mainController
     */
    public FileNewProjectFXMLController(Controller mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Discarding all data and closing the window.
     */
    @FXML
    private void projectCancelAction() {
        Stage stage = (Stage) projectCancelId.getScene().getWindow();
        stage.close();
    }

    /**
     * Create a new Project with data given by the user.
     */
    @FXML
    private void projectCreateAction() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("New Project");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Text Files", "*.txt", "*.csv"),
    			new ExtensionFilter("All Files", "*.*")
    			);
    	File file = fileChooser.showOpenDialog((Stage) projectCancelId.getScene().getWindow());
    	System.out.println(file);
        ProjectData projectData = new ProjectDetails();
        projectData.setProjectName(projectNameId.getText());
        projectData.setProjectAbstract(projectAbstractId.getText());
        projectData.setProjectAuthors(projectAuthorsId.getText());
        projectData.setProjectDescription(projectDescriptionId.getText());
        projectData.setProjectSummary(projectSummaryId.getText());
        mainController.saveProject(projectData);
        Stage stage = (Stage) projectNameId.getScene().getWindow();
        stage.close();
    }
}
