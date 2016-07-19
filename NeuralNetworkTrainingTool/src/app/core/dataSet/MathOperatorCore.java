package app.core.dataSet;

import java.util.function.Function;

public interface MathOperatorCore<T> extends Function<Double, Double>{

	/**
     * The MathOperator description.
     * 
     * @return String
     */
    public String getDescription();
    
    /**
     * The short name for the MathOperator.
     * 
     * @return String
     */
    public String getName();
    
    /**
     * The unique id to identity the MathTransform.
     * 
     * @return MathOperatorKey
     */
    public MathOperatorKey getId();
    
    /**
     * The initial value to be used on the operation.
     * 
     * @param bias double
     */
    public void setBiasValue(double bias);
}
