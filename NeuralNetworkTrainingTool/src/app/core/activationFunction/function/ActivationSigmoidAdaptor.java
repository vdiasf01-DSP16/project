package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSigmoid;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The Sigmoid activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationSigmoidAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The Sigmoid activation function description.
     */
    private final String DESCRIPTION = "Sigmoid Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Sigmoid";
    
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
        return new ActivationSigmoid();
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
        return ActivationFunctionKey.ActivationSigmoid;
    }
}
