package app.view.menu.file;

import java.io.File;

import app.controller.Controller;
import app.view.menu.ApplicationMenu;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileSaveProjectAsController implements ApplicationMenu {

    /**
     * The File Chooser title for loading a new project.
     */
    private final String FILE_CHOOSER_TITLE = "Load Project";

    /**
     * Initialising the File Save as. Project receiving the controller.
     * 
     * @param mainController
     */
    public FileSaveProjectAsController(Controller mainController) {
        
        FileChooser fileChooser = new FileChooser();

        // Derive the previous path from the old project file.
        File oldProjectFile = mainController.getProjectFile();

        // If file, pick up the old path and start from there.
        if ( oldProjectFile != null ) fileChooser.setInitialDirectory(new File(oldProjectFile.getParent()));

        // Set the title
        fileChooser.setTitle(FILE_CHOOSER_TITLE);

        fileChooser.getExtensionFilters().add( 
                new ExtensionFilter("Project Files", "*.prj") 
        );

        // Ask user to supply a file when this project is meant to be in.
        File file = fileChooser.showSaveDialog(new Stage());

        // Setting the new project file.
        mainController.setProjectFile(file);
        mainController.saveProject();
    }
}
