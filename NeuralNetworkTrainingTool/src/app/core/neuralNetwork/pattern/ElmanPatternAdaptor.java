package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.ElmanPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The Elman pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ElmanPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The Elman pattern description.
     */
    private final String DESCRIPTION = "Elman Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Elman Pattern";
    
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
        return new ElmanPattern();
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
        return NeuralNetworkPatternKey.ElmanPattern;
    }
}
