package app.core.neuralNetwork;

/**
 * The Neural Network patterns available with associated descriptions.
 * 
 * @author Vasco
 *
 */
public interface NeuralNetworkPatternCore <T> {

	/**
	 * The Neural Network Pattern description.
	 * 
	 * @return String
	 */
	public String getDescription();
	
	/**
	 * The short name for the pattern.'
	 * 
	 * @return String
	 */
	public String getName();
	
	/**
	 * The unique id to identity the pattern.
	 * 
	 * @return NeuralNetworkPatternKey
	 */
	public NeuralNetworkPatternKey getId();
	
	/**
	 * The generated pattern.
	 * 
	 * @return T
	 */
	public T getPattern();
}
