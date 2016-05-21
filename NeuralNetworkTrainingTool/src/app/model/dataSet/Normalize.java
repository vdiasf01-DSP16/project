package app.model.dataSet;

import java.util.function.Function;

import scala.math.BigDecimal;

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
        this.minValue = BigDecimal.decimal(minValue);
        this.maxValue = BigDecimal.decimal(maxValue);
        if ( this.maxValue.toDouble() == this.minValue.toDouble() )
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
        if ( value < minValue.toDouble() | value > maxValue.toDouble() ) 
            throw new IllegalArgumentException("Supplied value '"
              + value + "' is out of range ["
              + minValue.toDouble() + ", " + maxValue.toDouble() + "]");

        return ( value - minValue.toDouble() ) / ( maxValue.toDouble() - minValue.toDouble() );
    }
}
