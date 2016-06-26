package app.core.activationFunction.function;

import org.encog.engine.network.activation.ActivationElliottSymmetric;
import org.encog.engine.network.activation.ActivationFunction;

import app.core.activationFunction.ActivationFunctionCore;
import app.core.activationFunction.ActivationFunctionKey;

/**
 * The ElliottSymmetric activation function adapted to the Encog API.
 * 
 * @author Vasco
 *
 */
public class ActivationElliottSymmetricAdaptor implements ActivationFunctionCore<ActivationFunction> {

    /**
     * The ElliottSymmetric activation function description.
     */
    private final String DESCRIPTION = "ElliottSymmetric Activation Function - Description to be confirmed";
    
    /**
     * The user friendly name.
     */
    private final String PATTERN_NAME = "ElliottSymmetric";
    
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
        return new ActivationElliottSymmetric();
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
        return ActivationFunctionKey.ActivationElliottSymmetric;
    }
}
