package app.core.neuralNetwork;

import org.encog.neural.pattern.NeuralNetworkPattern;
import org.encog.neural.pattern.SVMPattern;

/**
 * The SVM pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class SVMPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

	/**
	 * The SVM pattern description.
	 */
	private final String DESCRIPTION = "SVM Pattern - Description to be confirmed";
	
	/**
	 * The user friendly name.
	 */
	private final String PATTERN_NAME = "SVM Pattern";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NeuralNetworkPattern getPattern() {
		return new SVMPattern();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return PATTERN_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NeuralNetworkPatternKey getId() {
		return NeuralNetworkPatternKey.SVMPattern;
	}
}
