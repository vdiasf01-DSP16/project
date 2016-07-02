package app.core.dataSet.mathOperator;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorKey;

/**
 * The Bin operator to set to 1 any non-zero values
 * 
 * @author Vasco
 *
 */
public class BinMathOperator implements MathOperatorCore<Double> {
    /**
     * The operation description.
     */
    public final String DESCRIPTION = "Setting all non-zero values to 1";
    
    /**
     * The operation user friendly name.
     */
    public final String NAME = "Binary";
    
    /**
     * The operation key id.
     */
    public final MathOperatorKey ID = MathOperatorKey.BIN;

    /**
     * Simple object instantiation.
     */
    public BinMathOperator() {
    }

    /**
     * Constructor accepting a double value only for compatibility.
     * 
     * @param biasValue double
     */
    public BinMathOperator(double biasValue) {
    	// No initial value required. Kept only compatibility.
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
    	if ( value == 0 ) return 0.0;
    	return 1.0;
    }
}
