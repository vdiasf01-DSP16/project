package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationBiPolar;
import org.encog.engine.network.activation.ActivationFunction;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The ActivationCompetitive activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationCompetitiveAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The ActivationCompetitive activation function description.
     */
    private final String DESCRIPTION = "ActivationCompetitive Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "ActivationCompetitive";
    
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
    public ActivationFunction getActivationFunction() {
        return new ActivationBiPolar();
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
    public ActivationFunctionKey getId() {
        return ActivationFunctionKey.ActivationCompetitive;
    }
}
