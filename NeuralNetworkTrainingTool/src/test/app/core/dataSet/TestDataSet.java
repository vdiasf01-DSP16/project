package test.app.core.dataSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import app.core.dataSet.DataSet;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.dataSet.VectorMap;

/**
 * Testing the abstract class DataSet to ensure all exceptions are thrown
 * as well as all default logic implemented works as expected.
 * 
 * Two protected dataSets supplied: 
 *  trainingDataSet
 *  testingDataSet
 *  
 * These two data sets are independent and share same functionality. The data
 * that should be loaded in each will be determined outside of this class.
 * 
 * @author Vasco
 *
 */
public class TestDataSet extends DataSet {

    /************************************************************************
     *                          Testing exceptions                          *
     ************************************************************************/

    /**
     * Testing exception on load() requiring implementation on extending class.
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
     * Testing exception on not defined input column map.
     */
    @Test(expected=IllegalStateException.class)
    public void testExceptionOnNotDefinedInputMap() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        getTrainingInputRow(0);
    }

    /**
     * Testing exception on not defined output column map.
     */
    @Test(expected=IllegalStateException.class)
    public void testExceptionOnNotDefinedOutputMap() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        getTrainingOutputRow(0);
    }

    /*************************************************************************
     *                    Default methods on loaded data                     *
     *************************************************************************/

    /**
     * Asserting Training Input row zero returns expected vector.
     */
    @Test
    public void testLoadedGetTrainingInputRow0() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingInputRow(0); 
        assertNotNull(found);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
        assertTrue(0 == found[2]);
    }

    /**
     * Asserting Training Input row one returns expected vector.
     */
    @Test
    public void testLoadedGetTrainingInputRow1() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingInputRow(1); 
        assertNotNull(found);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
        assertTrue(2.5 == found[2]);
    }

    /**
     * Asserting Training Output row zero returns expected vector.
     */
    @Test
    public void testLoadedGetTrainingOutputRow0() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        trainingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingOutputRow(0); 
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(0 == found[0]);
    }

    /**
     * Asserting Training Output row one returns expected vector.
     */
    @Test
    public void testLoadedGetTrainingOutputRow1() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        trainingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingOutputRow(1); 
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(22.5 == found[0]);
    }

    /**
     * Asserting Training Output row zero returns expected vector when two 
     * columns are expected.
     */
    @Test
    public void testLoadedGetTrainingOutput2Row0() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        trainingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingOutputRow(0); 
        assertNotNull(found);
        assertTrue(2 == found.length);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
    }

    /**
     * Asserting Training Output row one returns expected vector when two 
     * columns are expected.
     */
    @Test
    public void testLoadedGetTrainingOutput2Row1() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        trainingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTrainingOutputRow(1); 
        assertNotNull(found);
        assertTrue(2 == found.length);
        assertTrue(22.5 == found[0]);
        assertTrue(0 == found[1]);
    }

    /**
     * Asserting Testing Input row zero returns expected vector.
     */
    @Test
    public void testLoadedGetTestingInputRow0() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingInputRow(0); 
        assertNotNull(found);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
        assertTrue(0 == found[2]);
    }

    /**
     * Asserting Testing Input row one returns expected vector.
     */
    @Test
    public void testLoadedGetTestingInputRow1() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingInputRow(1); 
        assertNotNull(found);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
        assertTrue(2.5 == found[2]);
    }

    /**
     * Asserting Testing Output row zero returns expected vector.
     */
    @Test
    public void testLoadedGetTestingOutputRow0() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        testingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingOutputRow(0); 
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(0 == found[0]);
    }

    /**
     * Asserting Testing Output row one returns expected vector.
     */
    @Test
    public void testLoadedGetTestingOutputRow1() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        testingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingOutputRow(1); 
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(22.5 == found[0]);
    }

    /**
     * Asserting Testing Output row zero returns expected vector when two 
     * columns are expected.
     */
    @Test
    public void testLoadedGetTestingOutput2Row0() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        testingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingOutputRow(0); 
        assertNotNull(found);
        assertTrue(2 == found.length);
        assertTrue(0 == found[0]);
        assertTrue(0 == found[1]);
    }

    /**
     * Asserting Testing Output row one returns expected vector when two 
     * columns are expected.
     */
    @Test
    public void testLoadedGetTestingOutput2Row1() { 
        testingDataSet = new double[2][3];
        testingDataSet[1][2] = 2.5;
        testingDataSet[1][0] = 22.5;
        inputColumnMap = new LinkedList<>();
        outputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        outputColumnMap.add(new VectorMap(0, null));
        double[] found = getTestingOutputRow(1); 
        assertNotNull(found);
        assertTrue(2 == found.length);
        assertTrue(22.5 == found[0]);
        assertTrue(0 == found[1]);
    }

    /*************************************************************************
     *                            Column lengths                             *
     *************************************************************************/

    /**
     * Testing method on loaded source.
     */
    @Test
    public void testLoadedGetNumberOfInputColumnsOnlyTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
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
        inputColumnMap = new LinkedList<>();
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
        inputColumnMap.add(new VectorMap(0, null));
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
    public void testLoadedGetNumberOfOutputColumnsOnlyTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][2] = 2.5;
        outputColumnMap = new LinkedList<>();
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
        outputColumnMap = new LinkedList<>();
        int found = getNumberOfOutputColumns();
        assertTrue(0 == found);
    }

    
    /*************************************************************************
     *          Transformations and input/output index allocations           *
     *                                                                       *
     *  Transforms are not to affect in any way already loaded data. These   *
     *  should have been applied at the point when load() is called, and     *
     *  done once then.                                                      *
     *************************************************************************/

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetInputRowTransformTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
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
        testingDataSet = new double[2][3];
        testingDataSet[0][1] = 5.3;
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
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
        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
        int found = getNumberOfInputColumns(); 
        assertTrue(1 == found);
    }

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetNumberOfOutputColumnsTransform() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[0][1] = 5.3;
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
        outputColumnMap = new LinkedList<>();
        int found = getNumberOfOutputColumns();
        assertTrue(0 == found);
    }

    /**
     * Testing method on loaded source using map transforms.
     */
    @Test
    public void testLoadedGetOutputRowTransformTraining() { 
        trainingDataSet = new double[2][3];
        trainingDataSet[1][0] = 5.3;
        outputColumnMap = new LinkedList<VectorMap>();
        outputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
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
        testingDataSet = new double[2][3];
        testingDataSet[1][0] = 5.3;
        outputColumnMap = new LinkedList<VectorMap>();
        outputColumnMap.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.ADD, 1.3)));
        double found[] = getTestingOutputRow(1);
        assertNotNull(found);
        assertTrue(1 == found.length);
        assertTrue(5.3 == found[0]);
    }
    
    /**
     * Test Normalisation of loaded data.
     */
    @Test
    public void testNormalisation() {
        // Loaded data
        trainingDataSet = new double[3][3];
        // MIN -1.0 MAX 1.5
        trainingDataSet[0][0] = -1.0; // output row 0 
        trainingDataSet[1][0] =  1.4; // output row 0
        trainingDataSet[2][0] =  1.5; // output row 0

        // MIN 0.0 MAX 4.5
        trainingDataSet[0][1] =  0.0; // input row 0
        trainingDataSet[1][1] = 2.25; // input row 0
        trainingDataSet[2][1] =  4.5; // input row 1

        // MIN -125.0 MAX 125.0
        trainingDataSet[0][2] = -125.0; // input row 1
        trainingDataSet[1][2] = -50.0;  // input row 1
        trainingDataSet[2][2] =  125.0; // input row 1

        outputColumnMap = new LinkedList<VectorMap>();
        outputColumnMap.add(new VectorMap(0, null));
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(1, null));
        inputColumnMap.add(new VectorMap(2, null));

        // Get all values normalised between 0.0 and 1.0
        normalise();
        
        // output row 0 (-1.0) -> 0.0
        // output row 1 ( 1.4) -> 0.96
        // output row 2 ( 1.5) -> 1.0

        // input 0 row 0 ( 0.0) -> 0.0
        // input 0 row 1 (2.25) -> 0.5
        // input 0 row 2 ( 4.5) -> 1.0

        // input 1 row 0 (-125.0) -> 0.0
        // input 1 row 1 ( -50.0) -> 0.3
        // input 1 row 2 ( 125.0) -> 1.0

        // Let's check the outcome for output values
        double outputFound[] = getTrainingOutputRow(0);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(0.0 == outputFound[0]);
        outputFound = getTrainingOutputRow(1);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(0.96 == outputFound[0]);
        outputFound = getTrainingOutputRow(2);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(1.0 == outputFound[0]);

        // Let's check the outcome for input values
        double inputFound[] = getTrainingInputRow(0);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(0.0 == inputFound[0]);
        assertTrue(0.0 == inputFound[1]);
        inputFound = getTrainingInputRow(1);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(0.5 == inputFound[0]);
        assertTrue(0.3 == inputFound[1]);
        inputFound = getTrainingInputRow(2);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(1.0 == inputFound[0]);
        assertTrue(1.0 == inputFound[1]);
    }

    /**
     * Test DeNormalisation of loaded data.
     */
    @Test
    public void testDeNormalisation() {
        // Loaded data
        trainingDataSet = new double[3][3];
        // MIN -1.0 MAX 1.5
        trainingDataSet[0][0] = 0.0;  // output row 0 
        trainingDataSet[1][0] = 0.96; // output row 0
        trainingDataSet[2][0] = 1.0;  // output row 0

        // MIN 0.0 MAX 4.5
        trainingDataSet[0][1] = 0.0; // input row 0
        trainingDataSet[1][1] = 0.5; // input row 0
        trainingDataSet[2][1] = 1.0; // input row 1

        // MIN -125.0 MAX 125.0
        trainingDataSet[0][2] = 0.0; // input row 1
        trainingDataSet[1][2] = 0.3; // input row 1
        trainingDataSet[2][2] = 1.0; // input row 1
        
        outputColumnMap = new LinkedList<VectorMap>();
        outputColumnMap.add(new VectorMap(0, null));
        inputColumnMap = new LinkedList<VectorMap>();
        inputColumnMap.add(new VectorMap(1, null));
        inputColumnMap.add(new VectorMap(2, null));

        minValues = new double[3];
        maxValues = new double[3];
        minValues[0] = -1.0;   maxValues[0] = 1.5;
        minValues[1] = 0.0;    maxValues[1] = 4.5;
        minValues[2] = -125.0; maxValues[2] = 125.0;

        // Get all values normalised between 0.0 and 1.0
        deNormalise();
        
        // output row 0 (-1.0) <- 0.0
        // output row 1 ( 1.4) <- 0.96
        // output row 2 ( 1.5) <- 1.0

        // input 0 row 0 ( 0.0) <- 0.0
        // input 0 row 1 (2.25) <- 0.5
        // input 0 row 2 ( 4.5) <- 1.0

        // input 1 row 0 (-125.0) <- 0.0
        // input 1 row 1 ( -50.0) <- 0.3
        // input 1 row 2 ( 125.0) <- 1.0

        // Let's check the outcome for output values
        double outputFound[] = getTrainingOutputRow(0);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(-1.0 == outputFound[0]);
        outputFound = getTrainingOutputRow(1);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(1.4 == outputFound[0]);
        outputFound = getTrainingOutputRow(2);
        assertNotNull(outputFound);
        assertTrue(1 == outputFound.length);
        assertTrue(1.5 == outputFound[0]);

        // Let's check the outcome for input values
        double inputFound[] = getTrainingInputRow(0);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(0.0 == inputFound[0]);
        assertTrue(-125.0 == inputFound[1]);
        inputFound = getTrainingInputRow(1);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(2.25 == inputFound[0]);
        assertTrue(-50.0 == inputFound[1]);
        inputFound = getTrainingInputRow(2);
        assertNotNull(inputFound);
        assertTrue(2 == inputFound.length);
        assertTrue(4.5 == inputFound[0]);
        assertTrue(125.0 == inputFound[1]);
    }
}
