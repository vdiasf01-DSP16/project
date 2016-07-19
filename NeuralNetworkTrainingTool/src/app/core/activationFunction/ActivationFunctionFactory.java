package app.core.activationFunction;

import app.core.activationFunction.function.ActivationBiPolarAdaptor;
import app.core.activationFunction.function.ActivationBiPolarSteepenedSigmoidAdaptor;
import app.core.activationFunction.function.ActivationClippedLinearAdaptor;
import app.core.activationFunction.function.ActivationCompetitiveAdaptor;
import app.core.activationFunction.function.ActivationElliottAdaptor;
import app.core.activationFunction.function.ActivationElliottSymmetricAdaptor;
import app.core.activationFunction.function.ActivationGaussianAdaptor;
import app.core.activationFunction.function.ActivationLOGAdaptor;
import app.core.activationFunction.function.ActivationLinearAdaptor;
import app.core.activationFunction.function.ActivationRampAdaptor;
import app.core.activationFunction.function.ActivationSINAdaptor;
import app.core.activationFunction.function.ActivationSigmoidAdaptor;
import app.core.activationFunction.function.ActivationSoftMaxAdaptor;
import app.core.activationFunction.function.ActivationSteepenedSigmoidAdaptor;
import app.core.activationFunction.function.ActivationStepAdaptor;
import app.core.activationFunction.function.ActivationTANHAdaptor;

/**
 * The Activation Function abstract class for all the 
 * known implemented Activation Functions available.
 * 
 * @author Vasco
 *
 */
public abstract class ActivationFunctionFactory {
    
    /**
     * Get the matching Activation Function available.
     * 
     * @param activationFunction ActivationFunctionKey
     * @return ActivationFunctionCore
     */
    public static ActivationFunctionCore<?> getActivationFunction(ActivationFunctionKey activationFunction) {
        if ( ActivationFunctionKey.ActivationBiPolar.equals(activationFunction) ) 
            return new ActivationBiPolarAdaptor();
        if ( ActivationFunctionKey.ActivationBipolarSteepenedSigmoid.equals(activationFunction) ) 
            return new ActivationBiPolarSteepenedSigmoidAdaptor();
        if ( ActivationFunctionKey.ActivationClippedLinear.equals(activationFunction) ) 
            return new ActivationClippedLinearAdaptor();
        if ( ActivationFunctionKey.ActivationCompetitive.equals(activationFunction) ) 
            return new ActivationCompetitiveAdaptor();
        if ( ActivationFunctionKey.ActivationElliott.equals(activationFunction) ) 
            return new ActivationElliottAdaptor();
        if ( ActivationFunctionKey.ActivationElliottSymmetric.equals(activationFunction) ) 
            return new ActivationElliottSymmetricAdaptor();
        if ( ActivationFunctionKey.ActivationGaussian.equals(activationFunction) ) 
            return new ActivationGaussianAdaptor();
        if ( ActivationFunctionKey.ActivationLinear.equals(activationFunction) ) 
            return new ActivationLinearAdaptor();
        if ( ActivationFunctionKey.ActivationLOG.equals(activationFunction) ) 
            return new ActivationLOGAdaptor();
        if ( ActivationFunctionKey.ActivationRamp.equals(activationFunction) ) 
            return new ActivationRampAdaptor();
        if ( ActivationFunctionKey.ActivationSigmoid.equals(activationFunction) ) 
            return new ActivationSigmoidAdaptor();
        if ( ActivationFunctionKey.ActivationSIN.equals(activationFunction) ) 
            return new ActivationSINAdaptor();
        if ( ActivationFunctionKey.ActivationSoftMax.equals(activationFunction) ) 
            return new ActivationSoftMaxAdaptor();
        if ( ActivationFunctionKey.ActivationSteepenedSigmoid.equals(activationFunction) ) 
            return new ActivationSteepenedSigmoidAdaptor();
        if ( ActivationFunctionKey.ActivationStep.equals(activationFunction) ) 
            return new ActivationStepAdaptor();
        if ( ActivationFunctionKey.ActivationTANH.equals(activationFunction) ) 
            return new ActivationTANHAdaptor();
        return null;
    }

    /**
     * Get the matching Activation Function available by name.
     * 
     * @param activationFunctionName ActivationFunctionKey
     * @return ActivationFunctionCore
     */
    public static ActivationFunctionCore<?> getActivationFunction(String activationFunctionName) {
        if ( getName(ActivationFunctionKey.ActivationBiPolar).equals(activationFunctionName) ) 
            return new ActivationBiPolarAdaptor();
        if ( getName(ActivationFunctionKey.ActivationBipolarSteepenedSigmoid).equals(activationFunctionName) ) 
            return new ActivationBiPolarSteepenedSigmoidAdaptor();
        if ( getName(ActivationFunctionKey.ActivationClippedLinear).equals(activationFunctionName) ) 
            return new ActivationClippedLinearAdaptor();
        if ( getName(ActivationFunctionKey.ActivationCompetitive).equals(activationFunctionName) ) 
            return new ActivationCompetitiveAdaptor();
        if ( getName(ActivationFunctionKey.ActivationElliott).equals(activationFunctionName) ) 
            return new ActivationElliottAdaptor();
        if ( getName(ActivationFunctionKey.ActivationElliottSymmetric).equals(activationFunctionName) ) 
            return new ActivationElliottSymmetricAdaptor();
        if ( getName(ActivationFunctionKey.ActivationGaussian).equals(activationFunctionName) ) 
            return new ActivationGaussianAdaptor();
        if ( getName(ActivationFunctionKey.ActivationLinear).equals(activationFunctionName) ) 
            return new ActivationLinearAdaptor();
        if ( getName(ActivationFunctionKey.ActivationLOG).equals(activationFunctionName) ) 
            return new ActivationLOGAdaptor();
        if ( getName(ActivationFunctionKey.ActivationRamp).equals(activationFunctionName) ) 
            return new ActivationRampAdaptor();
        if ( getName(ActivationFunctionKey.ActivationSigmoid).equals(activationFunctionName) ) 
            return new ActivationSigmoidAdaptor();
        if ( getName(ActivationFunctionKey.ActivationSIN).equals(activationFunctionName) ) 
            return new ActivationSINAdaptor();
        if ( getName(ActivationFunctionKey.ActivationSoftMax).equals(activationFunctionName) ) 
            return new ActivationSoftMaxAdaptor();
        if ( getName(ActivationFunctionKey.ActivationSteepenedSigmoid).equals(activationFunctionName) ) 
            return new ActivationSteepenedSigmoidAdaptor();
        if ( getName(ActivationFunctionKey.ActivationStep).equals(activationFunctionName) ) 
            return new ActivationStepAdaptor();
        if ( getName(ActivationFunctionKey.ActivationTANH).equals(activationFunctionName) ) 
            return new ActivationTANHAdaptor();
        return null;
    }

    /**
     * The Description associated with the given activation function.
     * 
     * @param activationFunction
     * @return String
     */
    public static String getDescription(ActivationFunctionKey activationFunction) {
        return getActivationFunction(activationFunction).getDescription();
    }
    
    /**
     * Returning the name of the activation function given.
     * 
     * @param activationFunction
     * @return String
     */
    public static String getName(ActivationFunctionKey activationFunction) {
        return getActivationFunction(activationFunction).getName();
    }
}
