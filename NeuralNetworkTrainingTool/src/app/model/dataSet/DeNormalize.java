package app.model.dataSet;

import java.util.function.Function;

/**
 * The De-Normalisation of given double into the range set.
 * 
 * @author Vasco
 *
 */
public class DeNormalize implements Function<Double, Double> {

	/**
	 * The maximum normalised range.
	 */
	private final Double maxNorm;
	
	/**
	 * The minimum normalised range.
	 */
	private final Double minNorm;
	
	/**
	 * Constructor requiring the min and the max range to be set.
	 * 
	 * @param mathExpression String
	 */
	public DeNormalize(double minNorm, double maxNorm) {
		this.minNorm = minNorm;
		this.maxNorm = maxNorm;
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
		return minNorm + value * ( maxNorm - minNorm );
	}
}
