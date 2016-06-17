package app.view.menu.file;

import java.io.File;

import app.controller.Controller;
import app.view.menu.ApplicationMenu;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The Load File Project view.
 * 
 * @author Vasco
 *
 */
public class FileLoadProjectController implements ApplicationMenu {

	/**
	 * The File Chooser title for loading a new project.
	 */
	private final String FILE_CHOOSER_TITLE = "Load Project";

    /**
     * Initialising the File New Project receiving the controller.
     * 
     * @param mainController
     */
    public FileLoadProjectController(Controller mainController) {
    	
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
    	File file = fileChooser.showOpenDialog(new Stage());
    	// Setting the new project file.
    	mainController.loadProjectFile(file);
    }
}
