package app.model.serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * The Neural Network Configuration implementation for the serial
 * model to be saved on disk.
 * 
 * @author Vasco
 * 
 */
public class NeuralNetworkConfigImpl implements NeuralNetworkConfig {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4230281309585152408L;

	/**
	 * The input layer neuron size.
	 */
	private int inputLayerSize;

	/**
	 * The hidden layer neuron sizes.
	 */
	private Map<Integer,Integer> hiddenLayerSizes = new HashMap<>();

	/**
	 * The output layer neuron size.
	 */
	private int outputLayerSize;

	/**
	 * {@inheritDoc}
	 */
	@Override public int getInputLayerSize() {
		return inputLayerSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setInputLayerSize(int inputLayerSize) {
		this.inputLayerSize = inputLayerSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Map<Integer,Integer> getHiddenLayerSizes() {
		return hiddenLayerSizes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setHiddenLayerSizes(int layerId, int size) {
		hiddenLayerSizes.put(layerId, size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int getOutputLayerSize() {
		return outputLayerSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setOutputLayerSize(int outputLayerSize) {
		this.outputLayerSize = outputLayerSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int getHiddenLayerSize(int layerId) {
		if ( hiddenLayerSizes.containsKey(layerId) ) {
			return hiddenLayerSizes.get(layerId);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void resetHiddenLayer() {
		hiddenLayerSizes = new HashMap<>();
	}
}
