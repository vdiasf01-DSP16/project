package app.core.neuralNetwork.pattern;

import org.encog.neural.pattern.ADALINEPattern;
import org.encog.neural.pattern.NeuralNetworkPattern;

import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternKey;

/**
 * The ADALINE pattern adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ADALINEPatternAdaptor implements NeuralNetworkPatternCore<NeuralNetworkPattern> {

    /**
     * The ADALINE pattern description.
     */
    private final String DESCRIPTION = "ADALENE Pattern - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "ADALINE Pattern";
    
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
        return new ADALINEPattern();
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
        return NeuralNetworkPatternKey.ADALINEPattern;
    }
}
