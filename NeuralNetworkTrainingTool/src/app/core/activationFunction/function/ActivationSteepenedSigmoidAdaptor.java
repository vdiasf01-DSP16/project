package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationSteepenedSigmoid;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The SteepenedSigmoid activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationSteepenedSigmoidAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The SteepenedSigmoid activation function description.
     */
    private final String DESCRIPTION = "SteepenedSigmoid Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "SteepenedSigmoid";
    
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
        return new ActivationSteepenedSigmoid();
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
        return ActivationFunctionKey.ActivationSteepenedSigmoid;
    }
}
