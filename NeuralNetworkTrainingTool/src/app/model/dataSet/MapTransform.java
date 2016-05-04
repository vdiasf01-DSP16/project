package app.model.dataSet;

import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * The Map Transform for the ideal columns from the source columns,
 * mapping between indexes and making any required mathematical operation.
 * 
 * @author Vasco
 *
 */
public class MapTransform implements Function<Double, Double> {

	/**
	 * The mathematical expression to be applied to the given double.
	 */
	private String mathExpression;
	
	/**
	 * Constructor for when no mathematical expression required.
	 * 
	 * @param mathExpression String
	 */
	public MapTransform() {
		this.mathExpression = null;
	}

	/**
	 * Constructor requiring a mathematical expression to be kept.
	 * 
	 * @param mathExpression String
	 */
	public MapTransform(String mathExpression) {
		if ( mathExpression != null ) {
			if ( !mathExpression.contains("%") ) {
				throw new IllegalArgumentException("No placeholder found in: "+mathExpression);
			}
		}
		this.mathExpression = mathExpression;
	}

	/**
	 * Applying the supplied mathematical expression to the supplied double,
	 * and returning the result. The mathematical expression must contain placeholder
	 * e.g.: 2+4*%d+3 where %d is the placeholder where the given double will
	 * be placed into for calculation.
	 * 
	 * @param value Double
	 * @return Double
	 */
	@Override
	public Double apply(Double value) {
		// If no mathematical calculations are to be made, return given value.
		if ( mathExpression == null ) return value;

		// Use the given mathematical equation to calculate on with given double
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
		
		// Add the value to the mathematical expression.
        String expression = String.format(mathExpression, ""+value);

		// Return the evaluated transformed double or alert user about the mistake.
		try {
			return (Double) scriptEngine.eval(expression);
		} catch (ScriptException e) {
			throw new IllegalStateException("Don't know how to evaluate: "+expression);
		}
	}
}
