package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationRamp;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The Ramp activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationRampAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The Ramp activation function description.
     */
    private final String DESCRIPTION = "Ramp Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Ramp";
    
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
        return new ActivationRamp();
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
        return ActivationFunctionKey.ActivationRamp;
    }
}
