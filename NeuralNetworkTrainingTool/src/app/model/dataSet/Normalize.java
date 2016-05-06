package app.model.dataSet;

import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * The Normalising given double into the range set.
 * 
 * @author Vasco
 *
 */
public class Normalize implements Function<Double, Double> {

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
	public Normalize(double minNorm, double maxNorm) {
		this.minNorm = minNorm;
		this.maxNorm = maxNorm;
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
		return ( value - minNorm ) / ( maxNorm - minNorm );
	}
}
