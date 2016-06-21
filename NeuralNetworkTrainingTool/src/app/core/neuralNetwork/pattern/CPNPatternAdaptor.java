package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.CPNPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The CPN pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class CPNPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The CPN pattern description.
     */
    private final String DESCRIPTION = "CPN Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "CPN Pattern";
    
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
        return new CPNPattern();
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
        return NeuralNetworkPatternKey.CPNPattern;
    }
}
