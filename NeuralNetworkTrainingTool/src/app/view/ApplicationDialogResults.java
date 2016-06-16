package app.view;

public abstract class ApplicationDialogResults {

	/**
	 * The Application Dialog expected for when Yes is pressed.
	 */
	public final static String YES = "YES";

	/**
	 * The Application Dialog expected for when No is pressed.
	 */
	public final static String NO = "NO";
	
	/**
	 * The Application Dialog expected for when Cancel is pressed.
	 */
	public final static String CANCEL = "CANCEL";

	/**
	 * The result of the dialog.
	 * 
	 * @return String
	 */
	public String getResult() { return null; };

	/**
	 * Setting the result.
	 * 
	 * @param result
	 */
	public void setResult(String result) { };
}
