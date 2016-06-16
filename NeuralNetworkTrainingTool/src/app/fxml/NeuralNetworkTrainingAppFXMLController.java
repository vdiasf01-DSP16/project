package app.fxml;

import app.controller.Controller;
import app.controller.ControllerImpl;
import app.controller.FXMLController;
import app.view.ApplicationDialog;
import app.view.ApplicationDialogFactory;
import app.view.ApplicationViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private final String QUIT_APPLICATION_TEXT = "Do you really want to quit the application"
    		+ "?";
    
    /*************************************************************************
     *                          FXML ATTRIBUTES                              *
     *************************************************************************/

    @FXML private MenuItem fileNewProjectId;
    @FXML private MenuItem fileEditProjectId;
    @FXML private MenuItem fileLoadProjectId;
    @FXML private MenuItem fileSaveProjectId;
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

    @FXML private Label testLabelId;


    /**
     * Initial settings.
     */
    public void initialize() {
//        fileNewProjectId.setDisable(true);
        fileEditProjectId.setDisable(true);
        fileLoadProjectId.setDisable(true);
        fileSaveProjectId.setDisable(true);
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
        ApplicationViewFactory.startFileNewProjectController(mainController);
    }

    /**
     * The File Edit Project Action.
     */
    @FXML
    public void fileEditProjectAction() {
        ApplicationViewFactory.startFileEditProjectController(mainController);
    }

    /**
     * The File Load Project Action.
     */
    @FXML
    public void fileLoadProjectAction() {
        ApplicationViewFactory.startFileLoadProjectController(mainController);
    }

    /**
     * The File Save Project Action.
     */
    @FXML
    public void fileSaveProjectAction() {
        ApplicationViewFactory.startFileSaveProjectController(mainController);
    }

    /**
     * The File Close Project Action.
     */
    @FXML
    public void fileCloseProjectAction() {
      ApplicationViewFactory.startFileCloseProjectController(mainController);
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
     */
    @FXML
    public void fileCloseAction() {
    	if ( mainController.isOkToQuit() ) { 
    		String answer = ApplicationDialogFactory.askUserYesNo(QUIT_APPLICATION_TEXT);
    		if ( answer.equals(ApplicationDialog.YES) ) {
    			closeApp();
    		}
    	}
    	else {
    		// Ask if user wants to save data changes or close without save
    		String answer = ApplicationDialogFactory.askUserYesNoCancel(SAVE_CHANGES_TEXT);

    		if ( answer.equals(ApplicationDialog.YES) ) {
    			mainController.saveAll();
    			closeApp();
    		}
    		else if ( answer.equals(ApplicationDialog.NO) ) {
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
}
