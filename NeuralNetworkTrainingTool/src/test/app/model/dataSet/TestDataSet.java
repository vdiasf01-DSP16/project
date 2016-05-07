package test.app.model.dataSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import app.model.dataSet.DataSet;
import app.model.dataSet.MapTransform;
import app.model.dataSet.MathOperator;

/**
 * Testing the abstract class DataSet to ensure all exceptions are thrown.
 * 
 * @author Vasco
 *
 */
public class TestDataSet extends DataSet {

	/**********************************************************
	 *                   Testing exceptions                   *
	 **********************************************************/

	/**
	 * Testing exception with load() on DataSet that
	 * required to be implemented on the extending class.
	 */
	@Test(expected=IllegalStateException.class)
	public void testLoad() { 
		super.load(); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetInputRow() { 
		super.getInputRow(0); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetNumberOfInputColumns() { 
		super.getNumberOfInputColumns(); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetNumberOfOutputColumns() { 
		super.getNumberOfOutputColumns(); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetOutputRow() { 
		super.getOutputRow(0); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetTotalNumberOfSourceColumns() { 
		super.getTotalNumberOfSourceColumns(); 
	}

	/**
	 * Testing expected exception on a not yet loaded file.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetTotalNumberOfSourceRows() { 
		super.getTotalNumberOfSourceRows(); 
	}


	/**********************************************************
	 *             Default methods on loaded data             *
	 **********************************************************/

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, input row should return any source rows
	 * on a one-to-one basis.
	 */
	@Test
	public void testLoadedGetInputRow() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		double[] found = super.getInputRow(1); 
		assertNotNull(found);
		assertTrue(2.5 == found[2]);
	}

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, all columns should be allocated to the input
	 * on a one-to-one basis.
	 */
	@Test
	public void testLoadedGetNumberOfInputColumns() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		int found = super.getNumberOfInputColumns(); 
		assertTrue(3 == found);
	}

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, no columns should be assigned to the output.
	 */
	@Test
	public void testLoadedGetNumberOfOutputColumns() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		int found = super.getNumberOfOutputColumns();
		assertTrue(0 == found);
	}

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, no columns should be assigned to the output.
	 */
	@Test
	public void testLoadedGetOutputRow() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		double found[] = super.getOutputRow(1);
		assertNull(found);
	}

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, source columns should be equal to loaded ones.
	 */
	@Test
	public void testLoadedGetTotalNumberOfSourceColumns() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		int found = super.getTotalNumberOfSourceColumns();
		assertTrue(3 == found);
	}

	/**
	 * Testing method on loaded source.
	 * 
	 * By default, source rows should be equal to loaded ones.
	 */
	@Test
	public void testLoadedGetTotalNumberOfSourceRows() { 
		dataSetSource = new double[2][3];
		dataSetSource[1][2] = 2.5;
		int found = super.getTotalNumberOfSourceRows();
		assertTrue(2 == found);
	}
	
	/***********************************************************
	 *   Transformations and input/output index allocations    *
	 *                                                         *
	 *  Transforms are not to affect in any way already loaded *
	 *  data. These should have been applied at the point      *
	 *  when load() is called, and done once then.             *
	 ***********************************************************/

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetInputRowTransform() { 
		// Source[0] -> Input[1] += 1.3
		inputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		inputColumnMap.put(0, transform);
		super.setInputColumns(inputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		double[] found = super.getInputRow(0); 
		assertNotNull(found);
		assertTrue(0.0 == found[0]);
		assertTrue(5.3 == found[1]);
		assertTrue(0.0 == found[2]);
	}

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetNumberOfInputColumnsTransform() { 
		// Source[0] -> Input[1] += 1.3
		inputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		inputColumnMap.put(0, transform);
		super.setInputColumns(inputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		int found = super.getNumberOfInputColumns(); 
		assertTrue(3 == found);
	}

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetNumberOfOutputColumnsTransform() { 
		// Source[0] -> Input[1] += 1.3
		inputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		inputColumnMap.put(0, transform);
		super.setInputColumns(inputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		int found = super.getNumberOfOutputColumns();
		assertTrue(0 == found);
	}

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetOutputRowTransform() { 
		// Source[0] -> Input[1] += 1.3
		outputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		outputColumnMap.put(0, transform);
		super.setInputColumns(outputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		double found[] = super.getOutputRow(1);
		assertNotNull(found);
		assertTrue(0.0 == found[0]);
		assertTrue(5.3 == found[1]);
		assertTrue(0.0 == found[2]);
	}

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetTotalNumberOfSourceColumnsTransform() { 
		// Source[0] -> Input[1] += 1.3
		outputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		outputColumnMap.put(0, transform);
		super.setInputColumns(outputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		int found = super.getTotalNumberOfSourceColumns();
		assertTrue(3 == found);
	}

	/**
	 * Testing method on loaded source using map transforms.
	 */
	@Test
	public void testLoadedGetTotalNumberOfSourceRowsTransform() { 
		// Source[0] -> Input[1] += 1.3
		outputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
		Map<Integer,MapTransform> transform = new HashMap<>();
		transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
		outputColumnMap.put(0, transform);
		super.setInputColumns(outputColumnMap);

		dataSetSource = new double[2][3];
		dataSetSource[0][1] = 5.3;
		int found = super.getTotalNumberOfSourceRows();
		assertTrue(2 == found);
	}
	
}
