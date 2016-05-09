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
        load(); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTrainingInputRow() { 
        getTrainingInputRow(0); 
    }

    /**
     * Expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTestingInputRow() { 
        getTestingInputRow(0); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTrainingOutputRow() { 
        getTrainingOutputRow(0); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTestingOutputRow() { 
        getTestingOutputRow(0); 
    }

    /**
     * Expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetNumberOfInputColumns() { 
        getNumberOfInputColumns(); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetNumberOfOutputColumns() { 
        getNumberOfOutputColumns(); 
    }

    /**
     * Expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetNumberOfInputColumnsWithTestingDataSet() { 
    	testingDataSet = new double[2][2];
    	testingDataSet[0][1] = 2.3;
        getNumberOfInputColumns(); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetNumberOfOutputColumnsWithTestingDataSet() { 
    	testingDataSet = new double[2][2];
    	testingDataSet[0][1] = 2.3;
        getNumberOfOutputColumns(); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTotalNumberOfSourceColumns() { 
        getTotalNumberOfSourceColumns(); 
    }

    /**
     * Testing expected exception on a not yet loaded file.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetTotalNumberOfSourceRows() { 
        getTotalNumberOfSourceRows(); 
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, source columns still give an exception. 
     * It is with the extending class where the source is loaded
     * and these are calculated. After transformation and stored in
     * either trainingDataSet or testingDataSet, these will not be 
     * the same.
     */
    @Test(expected=IllegalStateException.class)
    public void testLoadedGetTotalNumberOfSourceColumns() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        getTotalNumberOfSourceColumns();
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, source rows still give an exception. 
     * It is with the extending class where the source is loaded
     * and these are calculated. After transformation and stored in
     * either trainingDataSet or testingDataSet, these will not be 
     * the same.
     */
    @Test(expected=IllegalStateException.class)
    public void testLoadedGetTotalNumberOfSourceRows() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        getTotalNumberOfSourceRows();
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
    public void testLoadedGetTrainingInputRow() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        double[] found = getTrainingInputRow(1); 
        assertNotNull(found);
        assertTrue(2.5 == found[2]);
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, input row should return any source rows
     * on a one-to-one basis.
     */
    @Test
    public void testLoadedGetTestingInputRow() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        double[] found = getTestingInputRow(1); 
        assertNotNull(found);
        assertTrue(2.5 == found[2]);
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, no columns should be assigned to the output.
     */
    @Test
    public void testLoadedGetTrainingOutputRow() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        double found[] = getTrainingOutputRow(1);
        assertNull(found);
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, no columns should be assigned to the output.
     */
    @Test
    public void testLoadedGetTestingOutputRow() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        double found[] = getTestingOutputRow(1);
        assertNull(found);
    }

    /**
     * Testing method on loaded source.
     * 
     * By default, all columns should be allocated to the input
     * on a one-to-one basis.
     */
    @Test
    public void testLoadedGetNumberOfInputColumnsOnlyTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        int found = getNumberOfInputColumns(); 
        assertTrue(3 == found);
    }

    /**
     * Testing method on loaded source.
     * 
     * When no trainingDataSet is supplied, the number of columns
     * for the testingDataSet is irrelevant and it is always expected
     * to return zero.
     */
    @Test
    public void testLoadedGetNumberOfInputColumnsOnlyTesting() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        int found = getNumberOfInputColumns(); 
        assertTrue(0 == found);
    }

    /**
     * Testing method on loaded source.
     * 
     * When no trainingDataSet is supplied, the number of columns
     * for the testingDataSet is irrelevant and it is always expected
     * to return zero.
     */
    @Test
    public void testLoadedGetNumberOfOutputColumnsOnlyTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        int found = getNumberOfOutputColumns();
        assertTrue(0 == found);
    }

    /**
     * Testing method on loaded source.
     * 
     * When no trainingDataSet is supplied, the number of columns
     * for the testingDataSet is irrelevant and it is always expected
     * to return zero.
     */
    @Test
    public void testLoadedGetNumberOfOutputColumnsOnlyTesting() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        int found = getNumberOfOutputColumns();
        assertTrue(0 == found);
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
    public void testLoadedGetInputRowTransformTraining() { 
        // Source[0] -> Input[1] += 1.3
        inputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
        Map<Integer,MapTransform> transform = new HashMap<>();
        transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
        inputColumnMap.put(0, transform);
        setInputColumns(inputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        double[] found = getTrainingInputRow(0); 
        assertNotNull(found);
        assertTrue(0.0 == found[0]);
        assertTrue(5.3 == found[1]);
        assertTrue(0.0 == found[2]);
    }

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetInputRowTransformTesting() { 
        // Source[0] -> Input[1] += 1.3
        inputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
        Map<Integer,MapTransform> transform = new HashMap<>();
        transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
        inputColumnMap.put(0, transform);
        setInputColumns(inputColumnMap);

        testingDataSet = new double[2][3];
        testingDataSet[0][1] = 5.3;
        double[] found = getTestingInputRow(0); 
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
        setInputColumns(inputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        int found = getNumberOfInputColumns(); 
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
        setInputColumns(inputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        int found = getNumberOfOutputColumns();
        assertTrue(0 == found);
    }

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetOutputRowTransformTraining() { 
        // Source[0] -> 1.3 + Source[0] -> dataSet[1] += 1.3
        outputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
        Map<Integer,MapTransform> transform = new HashMap<>();
        transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
        outputColumnMap.put(0, transform);
        setOutputColumns(outputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[1][0] = 5.3;
        double found[] = getTrainingOutputRow(1);
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(5.3 == found[0]);
    }

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetOutputRowTransformTesting() { 
        // Source[0] -> 1.3 + Source[0] -> dataSet[1] += 1.3
        outputColumnMap = new HashMap<Integer, Map<Integer,MapTransform>>();
        Map<Integer,MapTransform> transform = new HashMap<>();
        transform.put(1, new MapTransform(MathOperator.ADD, 1.3));
        outputColumnMap.put(0, transform);
        setOutputColumns(outputColumnMap);

        testingDataSet = new double[2][3];
        testingDataSet[1][0] = 5.3;
        double found[] = getTestingOutputRow(1);
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(5.3 == found[0]);
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
        setInputColumns(outputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        int found = getTotalNumberOfSourceColumns();
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
        setInputColumns(outputColumnMap);

        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        int found = getTotalNumberOfSourceRows();
        assertTrue(2 == found);
    }
}
