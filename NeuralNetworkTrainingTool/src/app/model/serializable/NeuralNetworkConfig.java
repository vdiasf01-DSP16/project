package app.model.serializable;

import java.io.Serializable;
import java.util.Map;

import app.core.neuralNetwork.NeuralNetworkPatternCore;

/**
 * The serializable version of the neural network configuration.
 * 
 * @author Vasco
 *
 */
public interface NeuralNetworkConfig<T> extends Serializable {

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
	 * @return Map Integer,Integer
	 */
	public Map<Integer,Integer> getHiddenLayerSizes();

	/**
	 * Setting the number of neurons per hidden layer.
	 * 
	 * @param layerId int
	 * @param size int
	 */
	public void setHiddenLayerSizes(int layerId, int size);
	
	/**
	 * Returns the set size of the given layer id or zero.
	 * 
	 * @param layerId int
	 * @return int
	 */
	public int getHiddenLayerSize(int layerId);

	/**
	 * Resetting the hidden layers to empty.
	 */
	public void resetHiddenLayer();

	/**
	 * The type of Neural Network chosen.
	 * 
	 * @return NeuralNetworkPatternCore
	 */
	public NeuralNetworkPatternCore<T> getTopology();

	/**
	 * Setting the type of Neural Network chosen.
	 */
	public void setTopology(NeuralNetworkPatternCore<T> neuralNetworkTopology);
}
