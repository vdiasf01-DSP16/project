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
import javafx.stage.Stage;

/**
 * The Menu File Edit Project Controller, populates already stored data
 * and allows the user to make updates.
 * 
 * @author Vasco
 *
 */
public class FileEditProjectFXMLController implements FXMLController {
    /**
     * The Cancel button.
     */
    @FXML
    private Button projectCancelId;
    
    /**
     * The Update button.
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
    public FileEditProjectFXMLController(Controller mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Make change to the interface before it is displayed.
     */
    public void initialize() {
        ProjectData projectData = mainController.getProjectData();
        String projectName = projectData.getProjectName();
        String projectAbstract = projectData.getProjectAbstract();
        String projectSummary = projectData.getProjectSummary();
        String projectAuthors = projectData.getProjectAuthors();
        String projectDescription = projectData.getProjectDescription();
        
        projectCreateId.setText("Update");
        projectAbstractId.setText(projectAbstract);
        projectAuthorsId.setText(projectAuthors);
        projectDescriptionId.setText(projectDescription);
        projectNameId.setText(projectName);
        projectSummaryId.setText(projectSummary);
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
        // Retrieves data supplied from the user.
        ProjectData projectData = getProjectData();

        // Retrieves previously saved project file.
        File file = mainController.getProjectFile();

        // If no file returned, the user has cancelled the operation.
        if ( file != null ) {

            // Set the project data details
            mainController.setProjectData(projectData);

            // Save the file
            mainController.saveProject();

            // Close the widget.
            Stage stage = (Stage) projectNameId.getScene().getWindow();
            stage.close();
        }
    }
    
    /**
     * Retrieves the data from the GUI into a structured
     * object ProjectData which is then returned
     * 
     * @return ProjectData
     */
    private ProjectData getProjectData() {
        // TODO: Validate input data
        ProjectData projectData = new ProjectDetails();
        projectData.setProjectName(projectNameId.getText());
        projectData.setProjectAbstract(projectAbstractId.getText());
        projectData.setProjectAuthors(projectAuthorsId.getText());
        projectData.setProjectDescription(projectDescriptionId.getText());
        projectData.setProjectSummary(projectSummaryId.getText());
        return projectData;
    }
}
