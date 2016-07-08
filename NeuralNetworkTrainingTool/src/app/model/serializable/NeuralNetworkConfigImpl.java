package app.model.serializable;

import java.util.List;

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
	private List<Integer> hiddenLayerSizes;

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
	@Override public List<Integer> getHiddenLayerSizes() {
		return hiddenLayerSizes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setHiddenLayerSizes(List<Integer> hiddenLayerSizes) {
		this.hiddenLayerSizes = hiddenLayerSizes;
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

}
