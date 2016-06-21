package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.BAMPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The BAM pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class BAMPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The BAM pattern description.
     */
    private final String DESCRIPTION = "BAM Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "BAM Pattern";
    
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
        return new BAMPattern();
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
        return NeuralNetworkPatternKey.BAMPattern;
    }
}
