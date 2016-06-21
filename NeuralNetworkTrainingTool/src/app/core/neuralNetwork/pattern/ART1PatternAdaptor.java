package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.ART1Pattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The ATR1 pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ART1PatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The ATR1 pattern description.
     */
    private final String DESCRIPTION = "ATR1 Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "ATR1 Pattern";
    
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
        return new ART1Pattern();
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
        return NeuralNetworkPatternKey.ART1Pattern;
    }
}
