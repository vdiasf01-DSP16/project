package app.model.dataSet;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * The Normalising given double into the range set.
 * 
 * @author Vasco
 *
 */
public class Normalize implements Function<Double, Double> {

    /**
     * The maximum input allowed.
     */
    private final BigDecimal maxValue;
    
    /**
     * The minimum input allowed.
     */
    private final BigDecimal minValue;
    
    /**
     * Constructor requiring the minimum and the maximum input value range.
     * 
     * @param minValue
     * @param maxValue
     */
    public Normalize(double minValue, double maxValue) {
        this.minValue = BigDecimal.valueOf(minValue);
        this.maxValue = BigDecimal.valueOf(maxValue);
        if ( this.maxValue.doubleValue() == this.minValue.doubleValue() )
            throw new IllegalArgumentException("Min and Max cannot be of the exact same value.");
    }

    /**
     * Normalising the supplied double value into a range between
     * the previously given minimum and maximum normalisation values.
     * 
     * @param value Double
     * @return Double
     */
    @Override
    public Double apply(Double value) {
        if ( value < minValue.doubleValue() | value > maxValue.doubleValue() ) 
            throw new IllegalArgumentException("Supplied value '"
              + value + "' is out of range ["
              + minValue.doubleValue() + ", " + maxValue.doubleValue() + "]");

        return ( value - minValue.doubleValue() ) / ( maxValue.doubleValue() - minValue.doubleValue() );
    }
}
