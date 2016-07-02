package test.app.core.dataSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.core.dataSet.MathOperatorCore;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.dataSet.VectorMap;

/**
 * Unit tests for the Vector Map class.
 * 
 * @author Vasco
 *
 */
public class TestVectorMap {

	/**
	 * The Vector Map.
	 */
	private VectorMap vectorMap;
	
	/**
	 * The source index to copy from.
	 */
	private final int SOURCE_INDEX = 2;
	
	/**
	 * The source vector.
	 */
	private final double[] SOURCE = {0.0, 0.1, 0.2, 0.3 };
	
	/**
	 * The transformation required.
	 */
	private MathOperatorCore<?> mapTransform;

	/**
	 * Initialise tests.
	 */
	@Before
	public void before() {
		mapTransform = MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 2.1);
		vectorMap = new VectorMap(SOURCE_INDEX, mapTransform);
	}

	/**
	 * Check if source index is same as specified.
	 */
	@Test
	public void testSourceIndex() {
		assertEquals(SOURCE_INDEX, vectorMap.getSourceIndex());
	}

	/**
	 * Source values must be set before checking.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTargetValueException() {
		assertTrue(SOURCE[SOURCE_INDEX]+2.1 == vectorMap.getTargetValue());
	}

	/**
	 * Source values must be set before checking.
	 */
	@Test
	public void testTargetValue() {
		vectorMap.setSource(SOURCE);
		assertTrue(2.3 == vectorMap.getTargetValue());
	}

	/**
	 * Check null map transform applies no change to source.
	 */
	@Test
	public void testTargetValueNoTransform() {
		vectorMap = new VectorMap(SOURCE_INDEX, null);
		vectorMap.setSource(SOURCE);
		assertTrue(SOURCE[SOURCE_INDEX] == vectorMap.getTargetValue());
	}
}
