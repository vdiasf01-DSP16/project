package app.core.dataSet;

import java.util.function.Function;

/**
 * The DeNormalisation of given double between zero and one
 * into the minimum and maximum final expected value range set.
 * 
 * @author Vasco
 *
 */
public class DeNormalize implements Function<Double, Double> {

    /**
     * The maximum output value de-normalised.
     */
    private final Double maxDeNorm;
    
    /**
     * The minimum output value de-normalised.
     */
    private final Double minDeNorm;
    
    /**
     * Constructor requiring the minimum and the maximum final ranges.
     * 
     * @param minDeNorm
     * @param maxDeNorm
     */
    public DeNormalize(double minDeNorm, double maxDeNorm) {
        this.minDeNorm = minDeNorm;
        this.maxDeNorm = maxDeNorm;
    }

    /**
     * De-Normalising the supplied double value into initial range 
     * given minimum and maximum normalisation values.
     * 
     * @param value Double
     * @return Double
     */
    @Override
    public Double apply(Double value) {
        if ( value < 0.0 | value > 1.0 ) 
            throw new IllegalArgumentException("Supplied value '" + value + "' is out of range [0.0, 1.0]");

        return minDeNorm + value * ( maxDeNorm - minDeNorm );
    }
}
