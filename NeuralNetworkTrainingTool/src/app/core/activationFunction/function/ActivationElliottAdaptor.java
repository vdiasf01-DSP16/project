package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationElliott;
import org.encog.engine.network.activation.ActivationFunction;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The Elliott activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationElliottAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The Elliott activation function description.
     */
    private final String DESCRIPTION = "Elliott Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "Elliott";
    
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
        return new ActivationElliott();
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
        return ActivationFunctionKey.ActivationElliott;
    }
}
