package app.view.widget;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creating one single entry for all alerts to be in style.
 * 
 * @author Vasco
 *
 */
public abstract class Message {

	/**
	 * Adds to the JFrame a new pane asking the user the given message,
	 * and returning an integer as the reply.
	 * 
	 * @param frame JFrame
	 * @param message String
	 * @return int
	 */
	public static int createWarningMessageThreeOptionalYNC(JFrame frame, String message) {
		return JOptionPane.showConfirmDialog(frame, message);
	}

}