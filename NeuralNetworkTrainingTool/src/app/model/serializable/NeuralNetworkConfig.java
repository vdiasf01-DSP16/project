package app.model.serializable;

import java.io.Serializable;
import java.util.List;

/**
 * The serializable version of the neural network configuration.
 * 
 * @author Vasco
 *
 */
public interface NeuralNetworkConfig extends Serializable {

	/**
	 * The number of neurons on the output layer.
	 * 
	 * @return int
	 */
	public int getOutputLayerSize();

	/**
	 * Setting the number of output layers.
	 * 
	 * @param outputLayerSize int
	 */
	void setOutputLayerSize(int outputLayerSize);

	/**
	 * The number of neurons on the input layer.
	 * 
	 * @return int
	 */
	public int getInputLayerSize();

	/**
	 * Setting the number of neurons for the input layer.
	 * 
	 * @param inputLayerSize int
	 */
	public void setInputLayerSize(int inputLayerSize);

	/**
	 * The number of neurons per hidden layer.
	 * 
	 * @return List Integer
	 */
	public List<Integer> getHiddenLayerSizes();

	/**
	 * Setting the number of neurons per hidden layer.
	 * 
	 * @param hiddenLayerSizes List Integer
	 */
	public void setHiddenLayerSizes(List<Integer> hiddenLayerSizes);

}
