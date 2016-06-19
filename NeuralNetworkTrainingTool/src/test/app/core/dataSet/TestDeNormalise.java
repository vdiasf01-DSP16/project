package test.app.core.dataSet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.core.dataSet.DeNormalize;

/**
 * Testing the DeNormalise class implementation.
 * 
 * The DeNormalise is expected to return a range of values between 
 * set minimum and maximum values, corresponding to the originally
 * Normalised values. Input values are expected to range between 
 * zero and one.
 * 
 * @author Vasco
 *
 */
public class TestDeNormalise {

    /**
     * The maximum output value allowed.
     */
    private final Double MAX_DENORM = 125.0;

    /**
     * The minimum input allowed.
     */
    private final Double MIN_DENORM = -10.0;
    
    /**
     * The DeNormalise handler
     */
    private final DeNormalize deNormalise = new DeNormalize(MIN_DENORM, MAX_DENORM);
    
    /**
     * Testing middle value for de-normalised 0.5.
     */
    @Test
    public void testDeNormalisedZeroPointFive() {
        Double value = 0.5;
        Double expected = ( ( MAX_DENORM - MIN_DENORM ) / 2 ) + MIN_DENORM;
        Double found = deNormalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing DeNormalisation of MIN_NORM
     */
    @Test
    public void testDeNormalisingMIN_NORM() {
        Double expected = MIN_DENORM;
        Double found = deNormalise.apply(0.0);
        assertEquals(expected, found);
    }

    /**
     * Testing DeNormalisation of MAX_NORM
     */
    @Test
    public void testDeNormalisingMAX_NORM() {
        Double expected = MAX_DENORM;
        Double found = deNormalise.apply(1.0);
        assertEquals(expected, found);
    }

    /**
     * Testing middle value for normalised 0.75.
     */
    @Test
    public void testDeNormalisedZeroPointSeventyFive() {
        Double expected = ( 3 * ( MAX_DENORM - MIN_DENORM ) / 4 ) + MIN_DENORM;
        Double value = 0.75;
        Double found = deNormalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing middle value for normalised 0.2.
     */
    @Test
    public void testDeNormalisedZeroPointTwelve() {
        // 1/2 point given by 1/2 of the length plus min_norm
        Double expected = ( ( MAX_DENORM - MIN_DENORM ) / 2 ) + MIN_DENORM;
        Double value = 1.0 / 2.0;
        Double found = deNormalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing out of range value exception upper bound.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDeNormalisedOutOfRangeExceptionUpperBound() {
        deNormalise.apply(1.1);
    }

    /**
     * Testing out of range value exception lower bound.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDeNormalisedOutOfRangeExceptionLowerBound() {
        deNormalise.apply(-0.1);
    }
}
