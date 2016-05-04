package test.app.model.dataSet;

import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import org.junit.Test;

import app.model.dataSet.MapTransform;

/**
 * Testing the MapTransform implementation.
 * 
 * @author Vasco
 *
 */
public class TestMapTransform {

	/**
	 * Testing that with no given expression, same value is returned.
	 */
	@Test
	public void testNoMathExpression() {
		// Instantiate new object with no math expression
		Function<Double,Double> mapTransform = new MapTransform();
		// Check if expected value matches returned one.
		double expectedValue = 12.34;
		double foundValue = mapTransform.apply(expectedValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing that with no given expression, same small double value is returned.
	 */
	@Test
	public void testNoMathExpressionSmallDouble() {
		// Instantiate new object with no math expression
		Function<Double,Double> mapTransform = new MapTransform();
		// Check if expected value matches returned one.
		double expectedValue = 12.34E-90;
		double foundValue = mapTransform.apply(expectedValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing that with no given expression, same big double value is returned.
	 */
	@Test
	public void testNoMathExpressionBigDouble() {
		// Instantiate new object with no math expression
		Function<Double,Double> mapTransform = new MapTransform();
		// Check if expected value matches returned one.
		double expectedValue = 12.34E90;
		double foundValue = mapTransform.apply(expectedValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with integers.
	 */
	@Test
	public void testMathExpressionWithIntegers() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2+3-%s+2");

		// Check if expected value matches returned one.
		double givenValue = 7;
		double expectedValue = 0;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with integers.
	 */
	@Test
	public void testMathExpressionWithIntegers2() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2/3*%s+2");

		// Check if expected value matches returned one.
		double givenValue = 7;
		double expectedValue = 6.666666666666666;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with double.
	 */
	@Test
	public void testMathExpressionWithDouble() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2.2+3.1-%s+2.5");

		// Check if expected value matches returned one.
		double givenValue = 7.2;
		double expectedValue = 0.6000000000000005;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with double.
	 */
	@Test
	public void testMathExpressionWithDouble2() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2.2/3.1*%s+2.5");

		// Check if expected value matches returned one.
		double givenValue = 7.2;
		double expectedValue = 7.609677419354839;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with small doubles.
	 */
	@Test
	public void testMathExpressionWithSmallDouble() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2.2E-10/3.1E-11*%s+2.5E-11");

		// Check if expected value matches returned one.
		double givenValue = 7.2E-10;
		double expectedValue = 5.134677419354838E-9;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}

	/**
	 * Testing math equations with big doubles.
	 */
	@Test
	public void testMathExpressionWithBigDouble() {
		// Instantiate new object with math expression
		Function<Double,Double> mapTransform = new MapTransform("2.2E10/3.1E11*%s+2.5E11");

		// Check if expected value matches returned one.
		double givenValue = 7.2E10;
		double expectedValue = 2.5510967741935483E11;
		double foundValue = mapTransform.apply(givenValue);
		assertTrue(expectedValue == foundValue);
	}
}
