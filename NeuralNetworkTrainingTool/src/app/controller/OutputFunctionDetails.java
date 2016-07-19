package app.controller;

/**
 * The Mapping of the function and initial value to an output neuron.
 * 
 * @author Vasco
 *
 */
public interface OutputFunctionDetails {
	
	/**
	 * Setting the output function attributes.
	 * 
	 * @param outputId int
	 * @param mathOperatorKey String
	 * @param functionValue double
	 */
	public void setOutputFunction(int outputId, String mathOperatorKey, double functionValue);

	/**
	 * The output neuron id.
	 * 
	 * @return int
	 */
	public int getOutputId();
	
	/**
	 * The Math Operator key.
	 * 
	 * @return Sring
	 */
	public String getMathOperatorKey();
	
	/**
	 * The function value as text.
	 * 
	 * @return String
	 */
	public double getFunctionValue();
}
