package test.app.model.dataSet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.model.dataSet.MapTransform;
import app.model.dataSet.MathOperator;
import app.model.dataSet.VectorMap;

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
	 * The target index when value is to be assigned to.
	 */
	private final int TARGET_INDEX = 1;

	/**
	 * The source vector.
	 */
	private final double[] SOURCE = {0.0, 0.1, 0.2, 0.3 };
	
	/**
	 * The transformation required.
	 */
	private MapTransform mapTransform;

	/**
	 * Initialise tests.
	 */
	@Before
	public void before() {
		mapTransform = new MapTransform(MathOperator.ADD, 2.1);
		vectorMap = new VectorMap(SOURCE_INDEX, TARGET_INDEX, mapTransform);
	}

	/**
	 * Check if source index is same as specified.
	 */
	@Test
	public void testSourceIndex() {
		assertEquals(SOURCE_INDEX, vectorMap.getSourceIndex());
	}

	/**
	 * Check if target index is same as specified.
	 */
	@Test
	public void testTargetIndex() {
		assertEquals(TARGET_INDEX, vectorMap.getTargetIndex());
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
		vectorMap = new VectorMap(SOURCE_INDEX, TARGET_INDEX, null);
		vectorMap.setSource(SOURCE);
		assertTrue(SOURCE[SOURCE_INDEX] == vectorMap.getTargetValue());
	}
}
