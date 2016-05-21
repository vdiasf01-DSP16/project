package test.app.model.dataSet;

import static org.junit.Assert.*;

import org.junit.Test;

import app.model.dataSet.Normalize;

/**
 * Testing the Normalise class implementation.
 * The Normalise is expected to return a range of values
 * between zero and one of any input within the minimum
 * and maximum range set.
 * 
 * @author Vasco
 *
 */
public class TestNormalise {

    /**
     * The maximum input value allowed.
     */
    private final Double MAX_NORM = 125.5;

    /**
     * The minimum input allowed.
     */
    private final Double MIN_NORM = -25.12;
    
    /**
     * The Normalise handler
     */
    private final Normalize normalise = new Normalize(MIN_NORM, MAX_NORM);
    
    /**
     * Testing middle value for normalised 0.5.
     */
    @Test
    public void testNormalisedZeroPointFive() {
        // Middle point given by half length plus min_norm
        Double value = ( ( MAX_NORM - MIN_NORM ) / 2 ) + MIN_NORM;
        Double expected = 0.5;
        Double found = normalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing Normalisation of MIN_NORM
     */
    @Test
    public void testNormalisingMIN_NORM() {
        Double expected = 0.0;
        Double found = normalise.apply(MIN_NORM);
        assertEquals(expected, found);
    }

    /**
     * Testing Normalisation of MAX_NORM
     */
    @Test
    public void testNormalisingMAX_NORM() {
        Double expected = 1.0;
        Double found = normalise.apply(MAX_NORM);
        assertEquals(expected, found);
    }

    /**
     * Testing middle value for normalised 0.75.
     */
    @Test
    public void testNormalisedZeroPointSeventyFive() {
        // 3/4 point given by 3 quarters of the length plus min_norm
        Double value = ( 3 * ( MAX_NORM - MIN_NORM ) / 4 ) + MIN_NORM;
        Double expected = 0.75;
        Double found = normalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing middle value for normalised 0.2.
     */
    @Test
    public void testNormalisedZeroPointTwelve() {
        // 1/2 point given by 1/2 of the length plus min_norm
        Double value = ( ( MAX_NORM - MIN_NORM ) / 2 ) + MIN_NORM;
        Double expected = 1.0 / 2.0;
        Double found = normalise.apply(value);
        assertEquals(expected, found);
    }

    /**
     * Testing out of range value exception upper bound.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNormalisedOutOfRangeExceptionUpperBound() {
        normalise.apply(MAX_NORM + 1);
    }

    /**
     * Testing out of range value exception lower bound.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNormalisedOutOfRangeExceptionLowerBound() {
        normalise.apply(MIN_NORM - 1);
    }

    /**
     * Testing normalisation on same min and max.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNormalisedOnSameMinAndMax() {
    	Normalize testingNormalise = new Normalize(10, 10);
        testingNormalise.apply(10.0);
    }

}
