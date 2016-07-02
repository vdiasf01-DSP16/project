package app.core.dataSet.mathOperator;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorKey;

/**
 * The Inv operator to invert any supplied value to 0 or 1 where
 * any non-zero value becomes zero and all zero values become 1.
 * 
 * @author Vasco
 *
 */
public class InvMathOperator implements MathOperatorCore<Double> {
    /**
     * The operation description.
     */
    public final String DESCRIPTION = "Inverting all future received values to 1 if zero and 0 otherwise.";
    
    /**
     * The operation user friendly name.
     */
    public final String NAME = "Inversion";
    
    /**
     * The operation key id.
     */
    public final MathOperatorKey ID = MathOperatorKey.INV;

    /**
     * Simple object instantiation.
     */
    public InvMathOperator() {
    }

    /**
     * Constructor accepting an initial double value, but it 
     * won't be used on any calculation for this operation.<p>
     * 
     * @param biasValue double
     */
    public InvMathOperator(double biasValue) {
    	// Value ignored for this operation
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
    	if ( value == 0 ) return 1.0;
        return 0.0;
    }
}
