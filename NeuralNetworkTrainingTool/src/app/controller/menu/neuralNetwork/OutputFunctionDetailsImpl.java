package app.controller.menu.neuralNetwork;

/**
 * The Output Function Details Implementation.
 * 
 * @author Vasco
 *
 */
public class OutputFunctionDetailsImpl implements OutputFunctionDetails {

	/**
	 * The data set input id from which the transformation is applied 
	 * and then linked to the given respective output id.
	 */
	private int inputId;
	
	/**
	 * The Neuron id that this information is for.
	 */
	private int outputId;
	
	/**
	 * The Math Operatiln key to be used.
	 */
	private String mathOperatorKey;
	
	/**
	 * The initial value to be used on the given function.
	 */
	private double functionValue;
	
	/**
	 * {@inheritDoc}
	 */
	@Override public void setOutputFunction(int inputId, int outputId, String mathOperatorKey, double functionValue) {
		this.inputId = inputId;
		this.outputId = outputId;
		this.mathOperatorKey = mathOperatorKey;
		this.functionValue = functionValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int getOutputId() {
		return outputId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int getInputId() {
		return inputId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMathOperatorKey() {
		return mathOperatorKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getFunctionValue() {
		return functionValue;
	}
}
