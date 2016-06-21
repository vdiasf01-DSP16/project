package app.core.neuralNetwork;

import org.encog.neural.pattern.NeuralNetworkPattern;
import org.encog.neural.pattern.SOMPattern;

/**
 * The SOM pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class SomPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

	/**
	 * The SOM pattern description.
	 */
	private final String DESCRIPTION = "SOM  Pattern - Description to be confirmed";
	
	/**
	 * The user friendly name.
	 */
	private final String PATTERN_NAME = "SOM  Pattern";
	
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
		return new SOMPattern();
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
		return NeuralNetworkPatternKey.SOMPattern;
	}
}
