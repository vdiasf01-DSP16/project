package app.core.neuralNetwork;

import org.encog.neural.pattern.HopfieldPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

/**
 * The Hopfield pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class HopfieldPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

	/**
	 * The Hopfield pattern description.
	 */
	private final String DESCRIPTION = "Hopfield Pattern - Description to be confirmed";
	
	/**
	 * The user friendly name.
	 */
	private final String PATTERN_NAME = "Hopfield Pattern";
	
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
		return new HopfieldPattern();
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
		return NeuralNetworkPatternKey.HopfieldPattern;
	}
}
