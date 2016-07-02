package app.core.activationFunction;

/**
 * The Activation Function available with associated descriptions.
 * 
 * @author Vasco
 *
 */
public interface ActivationFunctionCore<T> {

    /**
     * The Activation Function description.
     * 
     * @return String
     */
    public String getDescription();
    
    /**
     * The short name for the Activation Function.
     * 
     * @return String
     */
    public String getName();
    
    /**
     * The unique id to identity the pattern.
     * 
     * @return ActivationFunctionKey
     */
    public ActivationFunctionKey getId();
    
    /**
     * The generated Activation Function.
     * 
     * @return T
     */
    public T getActivationFunction();
}
