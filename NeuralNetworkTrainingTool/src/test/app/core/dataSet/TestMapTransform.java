package test.app.core.dataSet;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.Test;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.dataSet.mathOperator.AddMathOperator;
import app.core.dataSet.mathOperator.SubMathOperator;

/**
 * Testing the MapTransform implementation.
 * 
 * MapTransform, when initialised, takes the input index.
 * the output index, and the transformation operation to 
 * happen between the two. The value of the first will be
 * transformed and stored into the second.
 * 
 * @author Vasco
 *
 */
public class TestMapTransform {

    /**
     * The first value.
     */
    private final BigDecimal GIVEN_VALUE = BigDecimal.valueOf(1.25d);

    /**
     * The second value.
     */
    private final BigDecimal INITIAL_VALUE = BigDecimal.valueOf(-2.12345d);

    /**
     * Testing that with no given expression, 
     * same value is returned.
     */
    @Test
    public void testNoMathExpression() {
        // Instantiate new object with no math operation
        Function<Double,Double> mapTransform = new AddMathOperator();
        // Check if expected value matches returned one.
        double expectedValue = 12.34;
        double foundValue = mapTransform.apply(expectedValue);
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing that with no given expression, 
     * same small double value is returned.
     */
    @Test
    public void testNoMathExpressionSmallDouble() {
        // Instantiate new object with no math operation
        Function<Double,Double> mapTransform = new SubMathOperator();
        // Check if expected value matches returned one.
        double expectedValue = 12.34E-90;
        double foundValue = mapTransform.apply(expectedValue);
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing that with no given expression, 
     * same big double value is returned.
     */
    @Test
    public void testNoMathExpressionBigDouble() {
        // Instantiate new object with no math operation
        Function<Double,Double> mapTransform = new SubMathOperator();
        // Check if expected value matches returned one.
        double expectedValue = 12.34E90;
        double foundValue = mapTransform.apply(expectedValue);
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing that with no given expression, 
     * same very small double value is returned.
     */
    @Test
    public void testNoMathExpressioVerySmallDouble() {
        // Instantiate new object with no math operation
        Function<Double,Double> mapTransform = new AddMathOperator();
        // Check if expected value matches returned one.
        double expectedValue = 12.34E-90;
        double foundValue = mapTransform.apply(expectedValue);
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing addition.
     */
    @Test
    public void testMathAddition() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.ADD);
    	mapTransform.setBiasValue(INITIAL_VALUE.doubleValue());

        // Check if expected value matches returned one.
        double expectedValue = GIVEN_VALUE.add(INITIAL_VALUE).doubleValue();
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing subtraction.
     */
    @Test
    public void testMathSubtraction() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.SUB);
    	mapTransform.setBiasValue(INITIAL_VALUE.doubleValue());

        // Check if expected value matches returned one.
        double expectedValue = GIVEN_VALUE.subtract(INITIAL_VALUE).doubleValue();
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing multiplication.
     */
    @Test
    public void testMathMultiplication() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.MUL);
    	mapTransform.setBiasValue(INITIAL_VALUE.doubleValue());

        // Check if expected value matches returned one.
        double expectedValue = GIVEN_VALUE.multiply(INITIAL_VALUE).doubleValue();
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing division.
     */
    @Test
    public void testMathDivision() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.DIV);
    	mapTransform.setBiasValue(INITIAL_VALUE.doubleValue());

        // Check if expected value matches returned one.
        double expectedValue = GIVEN_VALUE.doubleValue() / INITIAL_VALUE.doubleValue();
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing division by zero.
     */
    @Test(expected=ArithmeticException.class)
    public void testMathDivisionByZero() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.DIV);
    	mapTransform.setBiasValue(0.0);
        mapTransform.apply(GIVEN_VALUE.doubleValue());
    }

    /**
     * Testing inversion.
     */
    @Test
    public void testMathINV() {
        // Instantiate new object with math operation
    	MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.INV);
        double expectedValue = GIVEN_VALUE.doubleValue() > 0 ? 0 : 1;
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }

    /**
     * Testing BIN conversion to 1 or 0.
     */
    @Test
    public void testMathBIN() {
        // Instantiate new object with math operation
        MathOperatorCore<?> mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.BIN);
        double expectedValue = GIVEN_VALUE.doubleValue() > 0 ? 1 : 0;
        double foundValue = mapTransform.apply(GIVEN_VALUE.doubleValue());
        assertTrue(expectedValue == foundValue);
    }
}
