package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.JordanPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The Jordan pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class JordanPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The Jordan pattern description.
     */
    private final String DESCRIPTION = "Jordan Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Jordan Pattern";
    
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
        return new JordanPattern();
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
        return NeuralNetworkPatternKey.JordanPattern;
    }
}
