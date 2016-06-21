package app.core.neuralNetwork;

import org.encog.neural.pattern.BoltzmannPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

/**
 * The Boltzmann pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class BoltzmannPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

	/**
	 * The Feed forward pattern description.
	 */
	private final String DESCRIPTION = "Boltzmann Pattern - Description to be confirmed";
	
	/**
	 * The user friendly name.
	 */
	private final String PATTERN_NAME = "Boltzmann Pattern";
	
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
		return new BoltzmannPattern();
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
		return NeuralNetworkPatternKey.BoltzmannPattern;
	}
}
