package app.model.dataSet;

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
	private final Double maxValue;
	
	/**
	 * The minimum input allowed.
	 */
	private final Double minValue;
	
	/**
	 * Constructor requiring the minimum and the maximum 
	 * input range allowed.
	 * 
	 * @param minValue
	 * @param maxValue
	 */
	public Normalize(double minValue, double maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
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
		if ( value < minValue | value > maxValue ) 
			throw new IllegalArgumentException("Supplied value '"
		      + value + "' is out of range ["
			  + minValue + ", " + maxValue + "]");
		return ( value - minValue ) / ( maxValue - minValue );
	}
}
