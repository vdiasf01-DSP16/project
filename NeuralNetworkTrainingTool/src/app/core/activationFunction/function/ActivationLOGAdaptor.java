package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLOG;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The LOG activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationLOGAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The LOG activation function description.
     */
    private final String DESCRIPTION = "LOG Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "LOG";
    
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
        return new ActivationLOG();
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
        return ActivationFunctionKey.ActivationLOG;
    }
}
