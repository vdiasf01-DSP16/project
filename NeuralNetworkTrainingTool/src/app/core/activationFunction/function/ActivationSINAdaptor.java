package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSIN;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The SIN activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationSINAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The SIN activation function description.
     */
    private final String DESCRIPTION = "SIN Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "SIN";
    
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
        return new ActivationSIN();
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
        return ActivationFunctionKey.ActivationSIN;
    }
}
