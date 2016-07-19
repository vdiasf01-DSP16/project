package app.core.dataSet.mathOperator;

import java.math.BigDecimal;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorKey;

/**
 * The Mul math operator to multiply the initially supplied double value
 * to all data set or neural network output values later supplied.
 * 
 * @author Vasco
 *
 */
public class MulMathOperator implements MathOperatorCore<Double> {
    /**
     * The operation description.
     */
    public final String DESCRIPTION = "Multiplies to received value.";
    
    /**
     * The operation user friendly name.
     */
    public final String NAME = "Multiplication";
    
    /**
     * The operation key id.
     */
    public final MathOperatorKey ID = MathOperatorKey.MUL;

    /**
     * The biasValue to be used with the operation to transform
     * the apply given value.
     */
    private BigDecimal biasValue;
    
    /**
     * If no operation is supplied at the start, then no transformation 
     * will be required, and the input value will be returned.
     */
    public MulMathOperator() {
        this.biasValue = new BigDecimal(0d);
    }

    /**
     * {@inheritDoc}
     */
	@Override public void setBiasValue(double bias) {
		this.biasValue = BigDecimal.valueOf(bias);
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
        return bigDecimalValue.multiply(biasValue).doubleValue();
    }
}
