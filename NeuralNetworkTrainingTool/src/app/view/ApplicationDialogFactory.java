package app.view;

import java.io.File;
import java.util.List;
import java.util.Map;

import app.controller.Controller;
import app.view.dialog.DialogSayController;
import app.view.dialog.DialogYesNoCancelController;
import app.view.dialog.DialogYesNoController;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Simple dialog widgets to either get a quick answer from user 
 * or inform about some issues.
 * 
 * @author Vasco
 *
 */
public abstract class ApplicationDialogFactory extends ApplicationDialogResults {

	/**
	 * Simple form of dialog requesting the user to confirm to go for with 
	 * the message with a yes, not going with the message by pressing no, 
	 * or cancelling the previous action altogether.
	 * 
     * @param message String
     * @return String
     */
	public static String askUserYesNoCancel(String message) {
		ApplicationDialogResults applicationDialog = new DialogYesNoCancelController(message);
		return applicationDialog.getResult();
	}

	/**
	 * After the message, user is prompt between a yes or a no.
	 * 
	 * @param message String
	 * @return String
	 */
	public static String askUserYesNo(String message) {
		ApplicationDialogResults applicationDialog = new DialogYesNoController(message);
		return applicationDialog.getResult();
	}

	/**
	 * Inform the user about some message.
	 * 
	 * @param message String
	 */
	public static void say(String message) {
		new DialogSayController(message);
	}
	
	/**
	 * Get a file from the user that belongs to the expected extension list.
	 * 
	 * @param mainController Controller
	 * @param fileChooserTitle FileChooser
	 * @param extensionList List Map String,String
	 * @return File
	 */
	public static File openFile(Controller mainController, String fileChooserTitle, List<Map<String,String>> extensionList) {
    	FileChooser fileChooser = new FileChooser();

    	// Derive the previous path from the old project file.
    	File oldProjectFile = mainController.getProjectFile();
    	
    	// If file, pick up the old path and start from there.
    	if ( oldProjectFile != null ) fileChooser.setInitialDirectory(new File(oldProjectFile.getParent()));

    	// Set the title
    	fileChooser.setTitle(fileChooserTitle);

    	// Load the possible extensions to save with.
    	for( Map<String,String> extension : extensionList ) {
        	fileChooser.getExtensionFilters().add( 
        			new ExtensionFilter(extension.keySet().iterator().next(), extension.values().iterator().next()) );
    	}
    	// Ask user to supply a file when this project is meant to be in.
    	File file = fileChooser.showOpenDialog(new Stage());
    	
    	// Return the file regardless if null.
    	return file;

	}
	/**
	 * Saves a file name selected by the user with the selected extension from given list.
	 * 
	 * @param mainController Controller
	 * @param fileChooserTitle FileChooser
	 * @param extensionList List Map String,String
	 * @return File
	 */
	public static File saveFile(Controller mainController, String fileChooserTitle, List<Map<String,String>> extensionList) {
    	FileChooser fileChooser = new FileChooser();

    	// Derive the previous path from the old project file.
    	File oldProjectFile = mainController.getProjectFile();
    	
    	// If file, pick up the old path and start from there.
    	if ( oldProjectFile != null ) fileChooser.setInitialDirectory(new File(oldProjectFile.getParent()));

    	// Set the title
    	fileChooser.setTitle(fileChooserTitle);

    	// Load the possible extensions to save with.
    	for( Map<String,String> extension : extensionList ) {
        	fileChooser.getExtensionFilters().add( 
        			new ExtensionFilter(extension.keySet().iterator().next(), extension.values().iterator().next()) );
    	}
    	// Ask user to supply a file when this project is meant to be in.
    	File file = fileChooser.showSaveDialog(new Stage());
    	
    	// Return the file regardless if null.
    	return file;
	}
}
