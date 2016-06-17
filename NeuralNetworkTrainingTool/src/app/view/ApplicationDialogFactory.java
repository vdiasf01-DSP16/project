package app.view;

import app.view.dialog.DialogSayController;
import app.view.dialog.DialogYesNoCancelController;
import app.view.dialog.DialogYesNoController;

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
}
