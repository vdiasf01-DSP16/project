package app.core.dataSet.mathOperator;

import java.math.BigDecimal;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorKey;

/**
 * The Div math operator to divide all data set or neural network output 
 * values later supplied by the initial value supplied, which cannot be zero.
 * 
 * @author Vasco
 *
 */
public class DivMathOperator implements MathOperatorCore<Double> {
	
	/**
	 * Illegal division by zero exception.
	 */
	private final String ILLEGAL_DIVISION_BY_ZERO = "Cannot set zero as dominator.";
	
    /**
     * The operation description.
     */
    public final String DESCRIPTION = "Dividing all future received values by the initally supplied.";
    
    /**
     * The operation user friendly name.
     */
    public final String NAME = "Division";
    
    /**
     * The operation key id.
     */
    public final MathOperatorKey ID = MathOperatorKey.DIV;

    /**
     * The biasValue to be used with the operation to transform
     * the apply given value.
     */
    private final BigDecimal biasValue;
    
    /**
     * If no operation is supplied at the start, then no transformation 
     * will be required, and the input value will be returned.
     */
    public DivMathOperator() {
        this.biasValue = new BigDecimal(0d);
    }

    /**
     * Constructor requiring an initial double value to which the input
     * will changed according to this operation.<p>
     * e.g.: apply(value) will return value + operation + biasValue.
     * 
     * @param biasValue double
     */
    public DivMathOperator(double biasValue) {
    	if ( biasValue == 0 ) throw new ArithmeticException(ILLEGAL_DIVISION_BY_ZERO);
        this.biasValue = BigDecimal.valueOf(biasValue);
    }

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
    public String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MathOperatorKey getId() {
        return ID;
    }

    /**
     * Take the bias value, and operate on this double to return the result.
     * 
     * @param value Double
     * @return Double
     */
    @Override
    public Double apply(Double value) {
        BigDecimal bigDecimalValue = BigDecimal.valueOf(value);
        return bigDecimalValue.doubleValue() / biasValue.doubleValue();
    }
}
