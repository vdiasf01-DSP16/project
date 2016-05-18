package test.app.model.dataSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import app.model.dataSet.DataSet;
import app.model.dataSet.FileAttributes;
import app.model.dataSet.FileDataSet;
import app.model.dataSet.MapTransform;
import app.model.dataSet.MathOperator;
import app.model.dataSet.VectorMap;

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
        fileDataSet.load();
        fileDataSet.loadTraining();
    }

    /**
     * Check if loadTesting was implemented.
     */
    @Test
    public void testLoadTestingWasImplemented() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
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
        fileAttributes.setTrainingRangeIndex(0, 5);
        fileDataSet.load();
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
        assertTrue(fileDataSet.getTrainingOutputRow(0).length == 0);
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
        assertTrue(0.0 == inputRow[0]);
        assertTrue(0.1 == inputRow[1]);
        assertTrue(0.2 == inputRow[2]);
        assertTrue(0.3 == inputRow[3]);
    }

    /**
     * Testing mapping with first column being the output
     * and all the other columns the input.
     */
    @Test
    public void testOutputInputMaps() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(0, 5);

        // The output transform
        List<VectorMap> outputVectorMap = new LinkedList<>();
        // Output inherits from source index 0 and gets assigned to index 0 with no transformation.
        outputVectorMap.add(new VectorMap(0, null));

        // The input transform
        List<VectorMap> inputVectorMap = new LinkedList<>();
        // Input index 0..2 gets no transformation on the allocated value.
        // Input column index inherits from indexes 1..3 of source.
        inputVectorMap.add(new VectorMap(1, null));
        inputVectorMap.add(new VectorMap(2, null));
        inputVectorMap.add(new VectorMap(3, null));
        
        fileDataSet.setInputColumns(inputVectorMap);
        fileDataSet.setOutputColumns(outputVectorMap);

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
        fileAttributes.setTrainingRangeIndex(0, 5);
        
        // The output transform
        List<VectorMap> outputColumns = new LinkedList<>();
        // Output index 0 gets no transformation on the allocated value.
        // Output column index inherits from index 0 of source.
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.ADD, 2.4)));

        // The input transform
        List<VectorMap> inputColumns = new LinkedList<>();
        // The overall transform from source to input.
        // Input index 0..2 gets no transformation on the allocated value.
        // Input column index inherits from indexes 1..3 of source.
        inputColumns.add(new VectorMap(1, new MapTransform(MathOperator.ADD, 2.5)));
        inputColumns.add(new VectorMap(2, new MapTransform(MathOperator.ADD, 2.5)));
        inputColumns.add(new VectorMap(3, new MapTransform(MathOperator.ADD, 2.5)));
        
        fileDataSet.setInputColumns(inputColumns);
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        
        // Get and verify data
        double[] inputRow0  = fileDataSet.getTrainingInputRow(0);
        double[] outputRow0 = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(outputRow0); 
        assertTrue(1 == outputRow0.length); 
        assertTrue(2.4 == outputRow0[0]);
        assertNotNull(inputRow0);  
        assertTrue(3 == inputRow0.length); 
        assertTrue(2.6 == inputRow0[0]);
        assertTrue(2.7 == inputRow0[1]);
        assertTrue(2.8 == inputRow0[2]);

        double[] inputRow1  = fileDataSet.getTrainingInputRow(1);
        double[] outputRow1 = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(outputRow1); 
        assertTrue(1 == outputRow1.length); 
        assertTrue(3.4 == outputRow1[0]);
        assertNotNull(inputRow1);  
        assertTrue(3 == inputRow1.length); 
        assertTrue(3.6 == inputRow1[0]);
        assertTrue(3.7 == inputRow1[1]);
        assertTrue(3.8 == inputRow1[2]);

        double[] inputRow2  = fileDataSet.getTrainingInputRow(2);
        double[] outputRow2 = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(outputRow2); 
        assertTrue(1 == outputRow2.length); 
        assertTrue(4.4 == outputRow2[0]); 
        assertNotNull(inputRow2);  
        assertTrue(3 == inputRow2.length); 
        assertTrue(4.6 == inputRow2[0]);
        assertTrue(4.7 == inputRow2[1]);
        assertTrue(4.8 == inputRow2[2]);

        double[] inputRow3  = fileDataSet.getTrainingInputRow(3);
        double[] outputRow3 = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(outputRow3); 
        assertTrue(1 == outputRow3.length); 
        assertTrue(5.4 == outputRow3[0]);
        assertNotNull(inputRow3);  
        assertTrue(3 == inputRow3.length); 
        assertTrue(5.6 == inputRow3[0]);
        assertTrue(5.7 == inputRow3[1]);
        assertTrue(5.8 == inputRow3[2]);

        double[] inputRow4  = fileDataSet.getTrainingInputRow(4);
        double[] outputRow4 = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(outputRow4); 
        assertTrue(1 == outputRow4.length); 
        assertTrue(6.4 == outputRow4[0]);
        assertNotNull(inputRow4);  
        assertTrue(3 == inputRow4.length); 
        assertTrue(6.6 == inputRow4[0]);
        assertTrue(6.7 == inputRow4[1]);
        assertTrue(6.8 == inputRow4[2]);
    }

    /**********************************************************
     *        Testing normalisation / deNormalisation         *
     **********************************************************/

    @Test
    public void testTrainingNormalisationDeNormalisation() {
    	DataSet fileDataSet = new FileDataSet(fileAttributes);
    	fileAttributes.setTrainingRangeIndex(0, 5);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(0, null));
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] row = fileDataSet.getTrainingInputRow(0);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.0 == row[0]); 
        assertTrue(0.0 == row[1]); 
        assertTrue(0.0 == row[2]); 
        assertTrue(0.0 == row[3]);

        row = fileDataSet.getTrainingInputRow(1);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.25 == row[0]); 
        assertTrue(0.25 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.25 == row[2]); 
        assertTrue(0.25 == row[3]);

        row = fileDataSet.getTrainingInputRow(2);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.5 == row[0]); 
        assertTrue(0.5 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(row[3]*100.0)/100.0));

        row = fileDataSet.getTrainingInputRow(3);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.75 == row[0]); 
        assertTrue(0.75 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(row[3]*100.0)/100.0));

        row = fileDataSet.getTrainingInputRow(4);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(1 == row[0]); 
        assertTrue(1 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(row[3]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        row = fileDataSet.getTrainingInputRow(0);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.0 == row[0]); 
        assertTrue(0.1 == row[1]); 
        assertTrue(0.2 == row[2]); 
        assertTrue(0.3 == row[3]);

        row = fileDataSet.getTrainingInputRow(1);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(1.0 == row[0]); 
        assertTrue(1.1 == row[1]); 
        assertTrue(1.2 == row[2]); 
        assertTrue(1.3 == row[3]);

        row = fileDataSet.getTrainingInputRow(2);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(2.0 == row[0]); 
        assertTrue(2.1 == row[1]); 
        assertTrue(2.2 == row[2]); 
        assertTrue(2.3 == row[3]);

        row = fileDataSet.getTrainingInputRow(3);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(3.0 == row[0]); 
        assertTrue(3.1 == row[1]); 
        assertTrue(3.2 == row[2]); 
        assertTrue(3.3 == row[3]);

        row = fileDataSet.getTrainingInputRow(4);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(4.0 == row[0]); 
        assertTrue(4.1 == row[1]); 
        assertTrue(4.2 == row[2]); 
        assertTrue(4.3 == row[3]);
    }

    @Test
    public void testTestingNormalisationDeNormalisation() {
    	DataSet fileDataSet = new FileDataSet(fileAttributes);
    	fileAttributes.setTestingRangeIndex(0, 5);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(0, null));
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] row = fileDataSet.getTestingInputRow(0);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.0 == row[0]); 
        assertTrue(0.0 == row[1]); 
        assertTrue(0.0 == row[2]); 
        assertTrue(0.0 == row[3]);

        row = fileDataSet.getTestingInputRow(1);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.25 == row[0]); 
        assertTrue(0.25 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.25 == row[2]); 
        assertTrue(0.25 == row[3]);

        row = fileDataSet.getTestingInputRow(2);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.5 == row[0]); 
        assertTrue(0.5 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(row[3]*100.0)/100.0));

        row = fileDataSet.getTestingInputRow(3);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.75 == row[0]); 
        assertTrue(0.75 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(row[3]*100.0)/100.0));

        row = fileDataSet.getTestingInputRow(4);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(1 == row[0]); 
        assertTrue(1 == (Math.round(row[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(row[2]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(row[3]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        row = fileDataSet.getTestingInputRow(0);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(0.0 == row[0]); 
        assertTrue(0.1 == row[1]); 
        assertTrue(0.2 == row[2]); 
        assertTrue(0.3 == row[3]);

        row = fileDataSet.getTestingInputRow(1);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(1.0 == row[0]); 
        assertTrue(1.1 == row[1]); 
        assertTrue(1.2 == row[2]); 
        assertTrue(1.3 == row[3]);

        row = fileDataSet.getTestingInputRow(2);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(2.0 == row[0]); 
        assertTrue(2.1 == row[1]); 
        assertTrue(2.2 == row[2]); 
        assertTrue(2.3 == row[3]);

        row = fileDataSet.getTestingInputRow(3);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(3.0 == row[0]); 
        assertTrue(3.1 == row[1]); 
        assertTrue(3.2 == row[2]); 
        assertTrue(3.3 == row[3]);

        row = fileDataSet.getTestingInputRow(4);
        assertNotNull(row);
        assertTrue(4 == row.length);
        assertTrue(4.0 == row[0]); 
        assertTrue(4.1 == row[1]); 
        assertTrue(4.2 == row[2]); 
        assertTrue(4.3 == row[3]);
    }
}