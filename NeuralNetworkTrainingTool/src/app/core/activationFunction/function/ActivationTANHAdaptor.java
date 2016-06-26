package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The TANH activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationTANHAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The TANH activation function description.
     */
    private final String DESCRIPTION = "TANH Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "TANH";
    
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
        return new ActivationTANH();
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
        return ActivationFunctionKey.ActivationTANH;
    }
}
