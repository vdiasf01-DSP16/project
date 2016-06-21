package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.NeuralNetworkPattern;
import org.encog.neural.pattern.RadialBasisPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The Radial Basis pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class RadialBasisPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The Radial Basis pattern description.
     */
    private final String DESCRIPTION = "Radial Basis Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Radial Basis Pattern";
    
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
        return new RadialBasisPattern();
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
        return NeuralNetworkPatternKey.RadialBasisPattern;
    }
}
