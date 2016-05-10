package test.app.model.dataSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import app.model.dataSet.DataSet;
import app.model.dataSet.FileAttributes;
import app.model.dataSet.FileDataSet;
import app.model.dataSet.MapTransform;
import app.model.dataSet.MathOperator;

/**
 * Testing the FileDataSet class implementation from DataSet abstract class.
 * 
 * @author Vasco
 *
 */
public class TestFileDataSet {

	/**
	 * Path to testing resource files.
	 */
	private final String PATH = "testingResources"+File.separatorChar;

	/**
     * The test file name to use.
     */
    private final String FILENAME = PATH+"TestFileDataSet.csv";

    /**
     * The test file name with header and footer.
     */
    private final String FILENAME_WITH_HEADER_AND_FOOTER = PATH+"TestFileDataSetHeaderFooter.csv";

    /**
     * The separator to be used.
     */
    private final String SEPARATOR = ",";

    /**
     * The number of header rows in file.
     */
    private final Integer HEADER_ROWS = 2;

    /**
     * The number of footer rows in file.
     */
    private final Integer FOOTER_ROWS = 2;

    /**
     * The FileAttributes.
     */
    private FileAttributes fileAttributes;
    
    private final int TESTING_START_INDEX  = 3;
    private final int TESTING_END_INDEX    = 4;
    private final int TRAINING_START_INDEX = 0;
    private final int TRAINING_END_INDEX   = 2;

    /**
     * Initialising the FileAttributes with the default settings.
     */
    @Before
    public void before() {
        fileAttributes = new FileAttributes();
        fileAttributes.setHeaderRows(0);
        fileAttributes.setFooterRows(0);
        fileAttributes.setTestingRangeIndex(TESTING_START_INDEX, TESTING_END_INDEX);
        fileAttributes.setTrainingRangeIndex(TRAINING_START_INDEX, TRAINING_END_INDEX);
        fileAttributes.setFilename(FILENAME);
        fileAttributes.setSeparator(SEPARATOR);
    }

    /**********************************************************
     *          Verify core methods were implemented          *
     **********************************************************/

    /**
     * Check if load was implemented.
     */
    @Test
    public void testLoadWasImplemented() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
    }

    /**
     * Check if loadTraining was implemented.
     */
    @Test
    public void testLoadTrainingWasImplemented() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.loadTraining();
    }

    /**
     * Check if loadTesting was implemented.
     */
    @Test
    public void testLoadTestingWasImplemented() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.loadTesting();
    }

    /**
     * Check if load was implemented correctly.
     */
    @Test
    public void testLoadWasImplementedAndDataSetUpdated() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(0);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(0.0 == input[0]);
        assertTrue(0.1 == input[1]);
        assertTrue(0.2 == input[2]);
        assertTrue(0.3 == input[3]);
    }

    /**
     * Check if loadTraining was implemented correctly.
     */
    @Test
    public void testLoadTrainingWasImplementedAndDataSetUpdated() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        double[] input = fileDataSet.getTrainingInputRow(4);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**********************************************************
     *                  Testing exceptions                    *
     **********************************************************/

    /**
     * Check if exception is thrown on non existing file name.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBadFileName() {
        fileAttributes.setFilename("Iamnotafile");
        new FileDataSet(fileAttributes);
    }

    /**********************************************************
     *                    Testing methods                     *
     **********************************************************/

    /**
     * Check if no exception is thrown when no output map is given
     * and output row is requested.
     */
    @Test
    public void testNoOutputMapGetOutpuRow() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        // Should give exception for there is not output row set.
        assertNull(fileDataSet.getTrainingOutputRow(0));
    }

    /**
     * Check file loaded successfully.
     */
    @Test
    public void testLoadSuccessfully() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();

        // By default, when the internal map is not set, these will
        // take the source columns all as input.
        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        // When the map is not set, the output takes nothing
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();

        assertTrue(0.0 == inputRow[0]);
        assertTrue(0.1 == inputRow[1]);
        assertTrue(0.2 == inputRow[2]);
        assertTrue(0.3 == inputRow[3]);

        assertTrue(4 == inputColumnNumber);
        assertTrue(0 == outputColumnNumber);
    }

    /**
     * Check file loaded successfully with a header and footer.
     */
    @Test
    public void testLoadSuccessfullyHeaderFooter() {
        fileAttributes.setHeaderRows(HEADER_ROWS);
        fileAttributes.setFooterRows(FOOTER_ROWS);
        fileAttributes.setFilename(FILENAME_WITH_HEADER_AND_FOOTER);
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();

        assertTrue(4 == fileDataSet.getNumberOfInputColumns());
        // When the map is not set, the output takes nothing
        assertTrue(0 == fileDataSet.getNumberOfOutputColumns());

        // By default, when the internal map is not set, these will
        // take the source columns all as input.
        double inputRow[] = fileDataSet.getTrainingInputRow(0);
        assertTrue(0.1 == inputRow[0]);
        assertTrue(0.2 == inputRow[1]);
        assertTrue(0.3 == inputRow[2]);
        assertTrue(0.4 == inputRow[3]);
    }

    /**
     * Testing mapping with first column being the output
     * and all the other columns the input.
     */
    @Test
    public void testOutputInputMaps() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);

        // The output transform
        Map<Integer, MapTransform> outputColumn = new HashMap<>();
        // The overall transform from source to output.
        Map<Integer, Map<Integer, MapTransform>> outputColumns = new HashMap<>();
        // Output index 0 gets no transformation on the allocated value.
        outputColumn.put(0, null);
        // Output column index inherits from index 0 of source.
        outputColumns.put(0, outputColumn);

        // The input transform
        Map<Integer, MapTransform> inputColumn0 = new HashMap<>();
        Map<Integer, MapTransform> inputColumn1 = new HashMap<>();
        Map<Integer, MapTransform> inputColumn2 = new HashMap<>();
        // The overall transform from source to input.
        Map<Integer, Map<Integer, MapTransform>> inputColumns = new HashMap<>();
        // Input index 0..2 gets no transformation on the allocated value.
        inputColumn0.put(0, null);
        inputColumn1.put(1, null);
        inputColumn2.put(2, null);
        // Input column index inherits from indexes 1..3 of source.
        inputColumns.put(1, inputColumn0);
        inputColumns.put(2, inputColumn1);
        inputColumns.put(3, inputColumn2);
        
        fileDataSet.setInputColumns(inputColumns);
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        
        // Get data and verify results match expectation.
        double[] inputRow0  = fileDataSet.getTrainingInputRow(0);
        double[] outputRow0 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow0); 
        assertTrue(1 == outputRow0.length); 
        assertNotNull(inputRow0);  
        assertTrue(3 == inputRow0.length); 
        assertTrue(0.1 == inputRow0[0]); 
        assertTrue(0.2 == inputRow0[1]);
        assertTrue(0.3 == inputRow0[2]); 

        double[] inputRow1  = fileDataSet.getTrainingInputRow(1);
        double[] outputRow1 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow1); 
        assertTrue(1 == outputRow1.length); 
        assertNotNull(inputRow1); 
        assertTrue(3 == inputRow1.length); 
        assertTrue(1.1 == inputRow1[0]); 
        assertTrue(1.2 == inputRow1[1]);
        assertTrue(1.3 == inputRow1[2]); 
        
        double[] inputRow2  = fileDataSet.getTrainingInputRow(2);
        double[] outputRow2 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow2); 
        assertTrue(1 == outputRow2.length); 
        assertNotNull(inputRow2); 
        assertTrue(3 == inputRow2.length); 
        assertTrue(2.1 == inputRow2[0]); 
        assertTrue(2.2 == inputRow2[1]);
        assertTrue(2.3 == inputRow2[2]); 

        double[] inputRow3  = fileDataSet.getTrainingInputRow(3);
        double[] outputRow3 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow3); 
        assertTrue(1 == outputRow3.length); 
        assertNotNull(inputRow3); 
        assertTrue(3 == inputRow3.length); 
        assertTrue(3.1 == inputRow3[0]); 
        assertTrue(3.2 == inputRow3[1]);
        assertTrue(3.3 == inputRow3[2]); 
        
        double[] inputRow4  = fileDataSet.getTrainingInputRow(4);
        double[] outputRow4 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow4); 
        assertTrue(1 == outputRow4.length); 
        assertNotNull(inputRow4); 
        assertTrue(3 == inputRow4.length); 
        assertTrue(4.1 == inputRow4[0]); 
        assertTrue(4.2 == inputRow4[1]);
        assertTrue(4.3 == inputRow4[2]); 
    }

    /**
     * Testing mapping with first column being the output
     * and all the other columns the input where the transform
     * is applying changes to original values.
     */
    @Test
    public void testOutputInputMapAndTransforms() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        
        double plusTrandformInput = 2.4;
        double plusTrandformOutput = 2.5;

        // The output transform
        Map<Integer, MapTransform> outputColumn = new HashMap<>();
        // The overall transform from source to output.
        Map<Integer, Map<Integer, MapTransform>> outputColumns = new HashMap<>();
        // Output index 0 gets no transformation on the allocated value.
        outputColumn.put(0, new MapTransform(MathOperator.ADD, plusTrandformOutput));
        // Output column index inherits from index 0 of source.
        outputColumns.put(0, outputColumn);

        // The input transform
        Map<Integer, MapTransform> inputColumn0 = new HashMap<>();
        Map<Integer, MapTransform> inputColumn1 = new HashMap<>();
        Map<Integer, MapTransform> inputColumn2 = new HashMap<>();
        // The overall transform from source to input.
        Map<Integer, Map<Integer, MapTransform>> inputColumns = new HashMap<>();
        // Input index 0..2 gets no transformation on the allocated value.
        inputColumn0.put(0, new MapTransform(MathOperator.ADD, plusTrandformInput));
        inputColumn1.put(1, new MapTransform(MathOperator.ADD, plusTrandformInput));
        inputColumn2.put(2, new MapTransform(MathOperator.ADD, plusTrandformInput));
        // Input column index inherits from indexes 1..3 of source.
        inputColumns.put(1, inputColumn0);
        inputColumns.put(2, inputColumn1);
        inputColumns.put(3, inputColumn2);
        
        fileDataSet.setInputColumns(inputColumns);
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        
        // Get and verify data
        double[] inputRow0  = fileDataSet.getTrainingInputRow(0);
        double[] outputRow0 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow0); 
        assertTrue(1 == outputRow0.length); 
        assertTrue(0.0+plusTrandformOutput == outputRow0[0]);
        assertNotNull(inputRow0);  
        assertTrue(3 == inputRow0.length); 
        assertTrue(0.1+plusTrandformInput == inputRow0[0]);
        assertTrue(0.2+plusTrandformInput == inputRow0[1]);
        assertTrue(0.3+plusTrandformInput == inputRow0[2]);

        double[] inputRow1  = fileDataSet.getTrainingInputRow(1);
        double[] outputRow1 = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(outputRow1); 
        assertTrue(1 == outputRow1.length); 
        assertTrue(1.0+plusTrandformOutput == outputRow1[0]);
        assertNotNull(inputRow1);  
        assertTrue(3 == inputRow1.length); 
        assertTrue(1.1+plusTrandformInput == inputRow1[0]);
        assertTrue(1.2+plusTrandformInput == inputRow1[1]);
        assertTrue(1.3+plusTrandformInput == inputRow1[2]);

        double[] inputRow2  = fileDataSet.getTrainingInputRow(2);
        double[] outputRow2 = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(outputRow2); 
        assertTrue(1 == outputRow2.length); 
        assertTrue(2.0+plusTrandformOutput == outputRow2[0]); 
        assertNotNull(inputRow2);  
        assertTrue(3 == inputRow2.length); 
        assertTrue(2.1+plusTrandformInput == inputRow2[0]);
        assertTrue(2.2+plusTrandformInput == inputRow2[1]);
        assertTrue(2.3+plusTrandformInput == inputRow2[2]);

        double[] inputRow3  = fileDataSet.getTrainingInputRow(3);
        double[] outputRow3 = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(outputRow3); 
        assertTrue(1 == outputRow3.length); 
        assertTrue(3.0+plusTrandformOutput == outputRow3[0]);
        assertNotNull(inputRow3);  
        assertTrue(3 == inputRow3.length); 
        assertTrue(3.1+plusTrandformInput == inputRow3[0]);
        assertTrue(3.2+plusTrandformInput == inputRow3[1]);
        assertTrue(3.3+plusTrandformInput == inputRow3[2]);

        double[] inputRow4  = fileDataSet.getTrainingInputRow(4);
        double[] outputRow4 = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(outputRow4); 
        assertTrue(1 == outputRow4.length); 
        assertTrue(4.0+plusTrandformOutput == outputRow4[0]);
        assertNotNull(inputRow4);  
        assertTrue(3 == inputRow4.length); 
        assertTrue(4.1+plusTrandformInput == inputRow4[0]);
        assertTrue(4.2+plusTrandformInput == inputRow4[1]);
        assertTrue(4.3+plusTrandformInput == inputRow4[2]);
    }

}