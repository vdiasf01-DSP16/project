package app.fxml;

import app.controller.Controller;
import app.controller.ControllerImpl;
import app.controller.FXMLController;
import app.view.ApplicationDialogFactory;
import app.view.ApplicationDialogResults;
import app.view.ApplicationViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Neural Network Training Application FXML controller.
 * 
 * @author Vasco
 *
 */
public class NeuralNetworkTrainingAppFXMLController implements FXMLController {
    //TODO: Move the implementation to GUICE
    /**
     * The Main Controller.
     */
    private Controller mainController = new ControllerImpl();

    /**
     * Prompts this text when there changes not saved 
     * and the user wants to quit the application.
     */
    private final String SAVE_CHANGES_TEXT = "Save unsaved changes?";
    
    /**
     * Prompts this text when user wants to close a project which is has 
     * pending changes to be saved.
     */
    private final String CLOSE_PROJECT_TEXT = "Save unsaved changes before closing the project?";
    
    /*************************************************************************
     *                          FXML ATTRIBUTES                              *
     *************************************************************************/

    @FXML private MenuItem fileNewProjectId;
    @FXML private MenuItem fileEditProjectId;
    @FXML private MenuItem fileLoadProjectId;
    @FXML private MenuItem fileSaveProjectId;
    @FXML private MenuItem fileSaveProjectAsId;
    @FXML private MenuItem fileCloseProjectId;
    @FXML private MenuItem fileImportNeuralNetworkConfigId;
    @FXML private MenuItem fileExportNeuralNetworkConfigId;
    @FXML private MenuItem fileExportEncogId;
    @FXML private MenuItem fileExportReportId;
    @FXML private MenuItem filePreferencesId;
    @FXML private MenuItem fileCloseId;
    @FXML private MenuItem neuralNetworkNewConfigId;
    @FXML private MenuItem neuralNetworkEditConfigId;
    @FXML private MenuItem neuralNetworkCloseConfigId;
    @FXML private MenuItem runTrainingId;
    @FXML private MenuItem runTestingId;
    @FXML private MenuItem runResetTrainingId;
    @FXML private MenuItem windowShowTrainingReportId;
    @FXML private MenuItem windowShowTestingReportId;    
    @FXML private MenuItem windowShowPlayerId;
    @FXML private MenuItem windowShowPlotId;
    @FXML private MenuItem windowShowProgressId;
    @FXML private MenuItem windowShowLogId;
    @FXML private MenuItem helpContentsId;
    @FXML private MenuItem helpAboutId;
    @FXML private Pane backgroundPaneId;

    /**
     * Initial settings.
     */
    public void initialize() {
        // Disabled until new project is loaded or created.
        fileEditProjectId.setDisable(true);
        fileSaveProjectId.setDisable(true);
        fileSaveProjectAsId.setDisable(true);
        fileCloseProjectId.setDisable(true);

        fileImportNeuralNetworkConfigId.setDisable(true);
        fileExportNeuralNetworkConfigId.setDisable(true);
        fileExportEncogId.setDisable(true);
        fileExportReportId.setDisable(true);
        filePreferencesId.setDisable(true);
//        fileCloseId.setDisable(true);
        neuralNetworkNewConfigId.setDisable(true);
        neuralNetworkEditConfigId.setDisable(true);
        neuralNetworkCloseConfigId.setDisable(true);
        runTrainingId.setDisable(true);
        runTestingId.setDisable(true);
        runResetTrainingId.setDisable(true);
        windowShowTrainingReportId.setDisable(true);
        windowShowTestingReportId.setDisable(true);    
        windowShowPlayerId.setDisable(true);
        windowShowPlotId.setDisable(true);
        windowShowProgressId.setDisable(true);
        windowShowLogId.setDisable(true);
        helpContentsId.setDisable(true);
        helpAboutId.setDisable(true);
    }
    
    /*************************************************************************
     *                                MENU FILE                              *
     *************************************************************************/

    /**
     * The File New Project Action.
     */
    @FXML
    public void fileNewProjectAction() {
        if ( mainController.isAllSaved() ) { 
            ApplicationViewFactory.startFileNewProjectController(mainController);
        }
        else {
            // Ask if user wants to save data changes or close without save
            String answer = ApplicationDialogFactory.askUserYesNoCancel(SAVE_CHANGES_TEXT);

            if ( answer.equals(ApplicationDialogResults.YES) ) {
                mainController.saveAll();
                mainController.closeProject();
                ApplicationViewFactory.startFileNewProjectController(mainController);
            }
            else if ( answer.equals(ApplicationDialogResults.NO) ) {
                mainController.closeProject();
                ApplicationViewFactory.startFileNewProjectController(mainController);
            }
        }
        updateProjectLoaded();
    }
 
    /**
     * The File Edit Project Action.
     */
    @FXML
    public void fileEditProjectAction() {
        ApplicationViewFactory.startFileEditProjectController(mainController);
        updateProjectLoaded();
    }

    /**
     * The File Load Project Action.
     */
    @FXML
    public void fileLoadProjectAction() {
        ApplicationViewFactory.startFileLoadProjectController(mainController);
        updateProjectLoaded();
    }

    /**
     * The File Save Project Action.
     */
    @FXML
    public void fileSaveProjectAction() {
        if ( ! mainController.isAllSaved() ) { 
            mainController.saveAll();
        }
        updateProjectLoaded();
    }

    /**
     * The File Save Project As.. Action.
     */
    @FXML
    public void fileSaveProjectAsAction() {
        ApplicationViewFactory.startFileSaveAsProjectController(mainController);
        updateProjectLoaded();
    }

    /**
     * The File Close Project Action.
     */
    @FXML
    public void fileCloseProjectAction() {
        if ( mainController.isAllSaved() ) {
            mainController.closeProject();
        } 
        else {
            String answer = ApplicationDialogFactory.askUserYesNoCancel(CLOSE_PROJECT_TEXT);
            if ( answer.equals(ApplicationDialogResults.YES) ) {
                mainController.saveAll();
                mainController.closeProject();
            }
            else if ( answer.equals(ApplicationDialogResults.NO) ) {
                mainController.closeProject();
            }
        }
        updateProjectLoaded();
    }

    /**
     * The File Import Neural Network Config Action.
     */
    @FXML
    public void fileImportNeuralNetworkConfigAction() {
        ApplicationViewFactory.startFileImportNeuralNetworkConfigController(mainController);
    }

    /**
     * The File Export Neural Network Config Action.
     */
    @FXML
    public void fileExportNeuralNetworkConfigAction() {
        ApplicationViewFactory.startFileExportNeuralNetworkConfigController(mainController);
    }

    /**
     * The File Export Encog Action.
     */
    @FXML
    public void fileExportEncogAction() {
        ApplicationViewFactory.startFileExportEncogController(mainController);
    }

    /**
     * The File Export Report Action.
     */
    @FXML
    public void fileExportReportAction() {
        ApplicationViewFactory.startFileExportReportController(mainController);
    }

    /**
     * The File Preferences Action.
     */
    @FXML
    public void filePreferencesAction() {
        ApplicationViewFactory.startFilePreferencesController(mainController);
    }

    /**
     * The File Close Action.
     * Asks the controller if it is safe to quit, closing the application 
     * immediately if true, otherwise, asks the user if any of the loss of
     * closing the application now is acceptable or if work should be saved.
     */
    @FXML
    public void fileCloseAction() {
        if ( mainController.isAllSaved() ) { 
            closeApp();
        }
        else {
            // Ask if user wants to save data changes or close without save
            String answer = ApplicationDialogFactory.askUserYesNoCancel(SAVE_CHANGES_TEXT);

            if ( answer.equals(ApplicationDialogResults.YES) ) {
                mainController.saveAll();
                closeApp();
            }
            else if ( answer.equals(ApplicationDialogResults.NO) ) {
                closeApp();
            }
        }
    }


    /*************************************************************************
     *                           MENU NEURAL NETWORK                         *
     *************************************************************************/

    /**
     * The Neural Network New Config Action.
     */
    @FXML
    public void neuralNetworkNewConfigAction() {
        //TODO
        System.out.println("neuralNetworkNewConfigAction not implemented");
    }

    /**
     * The Neural Network Edit Config Action.
     */
    @FXML
    public void neuralNetworkEditConfigAction() {
        //TODO
        System.out.println("neuralNetworkEditConfigAction not implemented");
    }

    /**
     * The Neural Network Close Config Action.
     */
    @FXML
    public void neuralNetworkCloseConfigAction() {
        //TODO
        System.out.println("neuralNetworkCloseConfigAction not implemented");
    }


    /*************************************************************************
     *                                MENU RUN                               *
     *************************************************************************/

    /**
     * The Run Training Action.
     */
    @FXML
    public void runTrainingAction() {
        //TODO
        System.out.println("runTrainingAction not implemented");
    }

    /**
     * The Run Testing Action.
     */
    @FXML
    public void runTestingAction() {
        //TODO
        System.out.println("runTestingAction not implemented");
    }

    /**
     * The Run Reset Training Action.
     */
    @FXML
    public void runResetTrainingAction() {
        //TODO
        System.out.println("runResetTrainingAction not implemented");
    }


    /*************************************************************************
     *                             MENU WINDOW                               *
     *************************************************************************/

    /**
     * The Window Show Training Report Action.
     */
    @FXML
    public void windowShowTrainingReportAction() {
        //TODO
        System.out.println("windowShowTrainingReportAction not implemented");
    }

    /**
     * The Window Show Testing Report Action.
     */
    @FXML
    public void windowShowTestingReportAction() {
        //TODO
        System.out.println("windowShowTestingReportAction not implemented");
    }

    /**
     * The Window Show Player Action.
     */
    @FXML
    public void windowShowPlayerAction() {
        //TODO
        System.out.println("windowShowPlayerAction not implemented");
    }

    /**
     * The Window Show Plot Action.
     */
    @FXML
    public void windowShowPlotAction() {
        //TODO
        System.out.println("windowShowPlotAction not implemented");
    }

    /**
     * The Window Show Progress Action.
     */
    @FXML
    public void windowShowProgressAction() {
        //TODO
        System.out.println("windowShowProgressAction not implemented");
    }

    /**
     * The Window Show Log Action.
     */
    @FXML
    public void windowShowLogAction() {
        //TODO
        System.out.println("windowShowLogAction not implemented");
    }


    /*************************************************************************
     *                               MENU HELP                               *
     *************************************************************************/

    /**
     * The Help Contents Action.
     */
    @FXML
    public void helpContentsAction() {
        //TODO
        System.out.println("helpContentsAction not implemented");
    }

    /**
     * The Help About Action.
     */
    @FXML
    public void helpAboutAction() {
        //TODO
        System.out.println("helpAboutAction not implemented");
    }
    
    
    /*************************************************************************
     *                          LITTLE HELPERS                               *
     *************************************************************************/

    /**
     * Close the application.
     */
    private void closeApp() {
        Scene scene = backgroundPaneId.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    /**
     * Check if a project is loaded or not and change menus accordingly.
     * 
     * @param status true if loaded
     */
    private void updateProjectLoaded() {
    	if ( mainController.getProjectData() != null ) { 
            fileEditProjectId.setDisable(false);
            fileSaveProjectId.setDisable(false);
            fileSaveProjectAsId.setDisable(false);
            fileCloseProjectId.setDisable(false);
    	}
    	else {
            fileEditProjectId.setDisable(true);
            fileSaveProjectId.setDisable(true);
            fileSaveProjectAsId.setDisable(true);
            fileCloseProjectId.setDisable(true);
        }
    }
}
