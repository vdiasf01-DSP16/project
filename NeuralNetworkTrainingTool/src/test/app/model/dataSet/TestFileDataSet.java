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
    private final int TRAINING_START_INDEX = 1;
    private final int TRAINING_END_INDEX   = 2;
    private final int TESTING_START_INDEX  = 3;
    private final int TESTING_END_INDEX    = 5;

    /**
     * The File with Header Attributes.
     */
    private FileAttributes fileHeaderAttributes;
    private final int HEADER_TRAINING_START_INDEX = 1 + HEADER_ROWS;
    private final int HEADER_TRAINING_END_INDEX   = 2 + HEADER_ROWS;
    private final int HEADER_TESTING_START_INDEX  = 3 + HEADER_ROWS;
    private final int HEADER_TESTING_END_INDEX    = 5 + HEADER_ROWS;

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

        fileHeaderAttributes = new FileAttributes();
        fileHeaderAttributes.setHeaderRows(HEADER_ROWS);
        fileHeaderAttributes.setFooterRows(FOOTER_ROWS);
        fileHeaderAttributes.setTestingRangeIndex(HEADER_TESTING_START_INDEX, HEADER_TESTING_END_INDEX);
        fileHeaderAttributes.setTrainingRangeIndex(HEADER_TRAINING_START_INDEX, HEADER_TRAINING_END_INDEX);
        fileHeaderAttributes.setFilename(FILENAME_WITH_HEADER_AND_FOOTER);
        fileHeaderAttributes.setSeparator(SEPARATOR);
    }

    /*************************************************************************
     *                 Verify core methods were implemented                  *
     *              and loads correctly on training and testing              *
     *************************************************************************/

    /**
     * Check if load was implemented.
     */
    @Test
    public void testLoadWasImplemented() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
    }
    
    /*************************************************************************
     *                Checking both files load correctly on                  *
     *                   training and testing data sets.                     *
     *************************************************************************/
    
    /**
     * Check Training load on fileAttributes.
     */
    @Test
    public void testLoadTrainingFileAttributesRow0() {
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
     * Check Training load on fileAttributes.
     */
    @Test
    public void testLoadTrainingFileAttributesRow1() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(1.0 == input[0]);
        assertTrue(1.1 == input[1]);
        assertTrue(1.2 == input[2]);
        assertTrue(1.3 == input[3]);
    }

    /**
     * Check exception Training load on fileAttributes.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTrainingFileAttributesRow2() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        fileDataSet.getTrainingInputRow(2);
    }

    /**
     * Check Testing load on fileAttributes.
     */
    @Test
    public void testLoadTestingFileAttributesRow2() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(0);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(2.0 == input[0]);
        assertTrue(2.1 == input[1]);
        assertTrue(2.2 == input[2]);
        assertTrue(2.3 == input[3]);
    }

    /**
     * Check Testing load on fileAttributes.
     */
    @Test
    public void testLoadTestingFileAttributesRow3() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(3.0 == input[0]);
        assertTrue(3.1 == input[1]);
        assertTrue(3.2 == input[2]);
        assertTrue(3.3 == input[3]);
    }

    /**
     * Check Testing load on fileAttributes.
     */
    @Test
    public void testLoadTestingFileAttributesRow4() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(2);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check exception Testing load on fileAttributes.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTestingFileAttributesRow5() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        fileDataSet.getTestingInputRow(3);
    }

    /**
     * Check Training load on fileHeaderAttributes.
     */
    @Test
    public void testLoadTrainingFileHeaderAttributesRow0() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
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
     * Check Training load on fileHeaderAttributes.
     */
    @Test
    public void testLoadTrainingFileHeaderAttributesRow1() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(1.0 == input[0]);
        assertTrue(1.1 == input[1]);
        assertTrue(1.2 == input[2]);
        assertTrue(1.3 == input[3]);
    }

    /**
     * Check exception Training load on fileHeaderAttributes.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTrainingFileHeaderAttributesRow2() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        fileDataSet.getTrainingInputRow(2);
    }

    /**
     * Check Testing load on fileHeaderAttributes.
     */
    @Test
    public void testLoadTestingFileHeaderAttributesRow2() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(0);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(2.0 == input[0]);
        assertTrue(2.1 == input[1]);
        assertTrue(2.2 == input[2]);
        assertTrue(2.3 == input[3]);
    }

    /**
     * Check Testing load on fileHeaderAttributes.
     */
    @Test
    public void testLoadTestingFileHeaderAttributesRow3() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(3.0 == input[0]);
        assertTrue(3.1 == input[1]);
        assertTrue(3.2 == input[2]);
        assertTrue(3.3 == input[3]);
    }

    /**
     * Check Testing load on fileHeaderAttributes.
     */
    @Test
    public void testLoadTestingFileHeaderAttributesRow4() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(2);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check exception Testing load on fileHeaderAttributes.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTestingFileHeaderAttributesRow5() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();
        fileDataSet.getTestingInputRow(3);
    }

    /**
     * Check exception on attempting to load more rows than those in file.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTrainingFileAttributesLoadMoreThanAvailable() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(TRAINING_START_INDEX, 120125);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        fileDataSet.getTrainingInputRow(3);
    }

    /**
     * Check exception on attempting to load more rows than those in file.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadTestingFileAttributesLoadMoreThanAvailable() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(TESTING_START_INDEX, 120125);
        fileAttributes.setHasTrainingRange(false);
        fileDataSet.load();
        fileDataSet.getTestingInputRow(3);
    }


    /*************************************************************************
     *                 Checking File attributes extended                     *
     *************************************************************************/

    /**
     * Check Data set ranges on negative ranges.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadFileAttributesDataSetNegativeRangesTesting() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(-1, 5);
        fileDataSet.load();
    }

    /**
     * Check Data set ranges on negative ranges.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadFileAttributesDataSetNegativeRangesTraining() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(-1, 5);
        fileDataSet.load();
    }

    /**
     * Check Data set ranges overlap exceptions.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadFileAttributesDataSetOverlapSame() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileDataSet.load();
    }

    /**
     * Check Data set ranges overlap exceptions.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadFileAttributesDataSetOverlapPartialTesting() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 3);
        fileAttributes.setTrainingRangeIndex(2, 5);
        fileDataSet.load();
    }

    /**
     * Check Data set ranges overlap exceptions.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadFileAttributesDataSetOverlapPartialTraining() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(2, 5);
        fileAttributes.setTrainingRangeIndex(1, 3);
        fileDataSet.load();
    }

    /**
     * Check Data set ranges no overlap exceptions.
     */
    @Test
    public void testLoadFileAttributesDataSetNoOverlap() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 3);
        fileAttributes.setTrainingRangeIndex(4, 5);
        fileDataSet.load();
    }

    /**
     * Check Training on different ranges.
     */
    @Test
    public void testLoadTrainingUpdatedForRow5a() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(4);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Training on different ranges.
     */
    @Test
    public void testLoadTrainingUpdatedForRow5b() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(2, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(3);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Training on different ranges.
     */
    @Test
    public void testLoadTrainingUpdatedForRow5c() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(3, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(2);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Training on different ranges.
     */
    @Test
    public void testLoadTrainingUpdatedForRow5d() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(4, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Training on different ranges.
     */
    @Test
    public void testLoadTrainingUpdatedForRow5e() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(5, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTrainingInputRow(0);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Testing on different ranges.
     */
    @Test
    public void testLoadTestingUpdatedForRow5a() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setHasTrainingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(4);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Testing on different ranges.
     */
    @Test
    public void testLoadTestingUpdatedForRow5b() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(2, 5);
        fileAttributes.setHasTrainingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(3);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Testing on different ranges.
     */
    @Test
    public void testLoadTestingUpdatedForRow5c() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(3, 5);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(2);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Testing on different ranges.
     */
    @Test
    public void testLoadTestingUpdatedForRow5d() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(4, 5);
        fileAttributes.setHasTrainingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(1);
        assertNotNull(input);
        assertTrue(4 == input.length);
        assertTrue(4.0 == input[0]);
        assertTrue(4.1 == input[1]);
        assertTrue(4.2 == input[2]);
        assertTrue(4.3 == input[3]);
    }

    /**
     * Check Testing on different ranges.
     */
    @Test
    public void testLoadTestingUpdatedForRow5e() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(5, 5);
        fileAttributes.setHasTrainingRange(false);
        fileDataSet.load();
        double[] input = fileDataSet.getTestingInputRow(0);
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

    /**
     * Check if exception is thrown on testing.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadWasImplementedAndDataSetUpdatedForTestingRow5Exception() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 4);
        fileAttributes.setTrainingRangeIndex(4, 5);
        fileDataSet.load();
        fileDataSet.getTestingInputRow(5);
    }

    /**
     * Check if load was implemented correctly.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadWasImplementedAndDataSetUpdatedWithOutOfBoundsOnRow2() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        fileDataSet.getTrainingInputRow(2);
    }

    /**
     * Check if load was implemented correctly.
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testLoadWasImplementedAndDataSetUpdatedForRow5Exception() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
        fileDataSet.load();
        fileDataSet.getTrainingInputRow(5);
    }

    /**
     * Check if rows start at 1 instead of zero from source.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadWithRangeFromZeroException() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(0, 5);
        fileDataSet.load();
    }

    /**
     * Check upper-bound range from source.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadWithRangeHigherThenMaxFromSourceException() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 1000);
        fileDataSet.load();
    }

    /*************************************************************************
     *                  Testing input and output mappings                    *
     *************************************************************************/

    /**
     * No exception when no output map supplied and output is requested.
     * A zero length array should be returned possibly due to a non 
     * supervised training.
     */
    @Test
    public void testNoOutputMapGetOutputRow() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();
        double foundRow[] = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(foundRow);
        assertTrue(0 == foundRow.length);
    }

    /**
     * Training.
     * Check file loaded successfully when no input map is supplied.
     */
    @Test
    public void testLoadWithNoInputMap() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();

        // When not internal map is set, source is all mapped as input.
        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(0.0 == inputRow[0]);
        assertTrue(0.1 == inputRow[1]);
        assertTrue(0.2 == inputRow[2]);
        assertTrue(0.3 == inputRow[3]);

        assertTrue(4 == inputColumnNumber);
        assertTrue(0 == outputColumnNumber);
    }

    /**
     * Training.
     * Check file header loaded successfully when no input map is supplied.
     */
    @Test
    public void testLoadHeaderWithNoInputMap() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();

        // When not internal map is set, source is all mapped as input.
        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(0.0 == inputRow[0]);
        assertTrue(0.1 == inputRow[1]);
        assertTrue(0.2 == inputRow[2]);
        assertTrue(0.3 == inputRow[3]);

        assertTrue(4 == inputColumnNumber);
        assertTrue(0 == outputColumnNumber);
    }

    /**
     * Testing.
     * Check file loaded successfully when no input map is supplied.
     */
    @Test
    public void testLoadTestingWithNoInputMap() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileDataSet.load();

        // When not internal map is set, source is all mapped as input.
        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(4 == inputRow.length);
        assertTrue(0 == outputColumnNumber);
        assertTrue(4 == inputColumnNumber);

        assertTrue(2.0 == inputRow[0]);
        assertTrue(2.1 == inputRow[1]);
        assertTrue(2.2 == inputRow[2]);
        assertTrue(2.3 == inputRow[3]);

    }

    /**
     * Testing.
     * Check file header loaded successfully when no input map is supplied.
     */
    @Test
    public void testLoadTestingHeaderWithNoInputMap() {
        DataSet fileDataSet = new FileDataSet(fileHeaderAttributes);
        fileDataSet.load();

        // When not internal map is set, source is all mapped as input.
        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(4 == inputRow.length);
        assertTrue(0 == outputColumnNumber);
        assertTrue(4 == inputColumnNumber);

        assertTrue(2.0 == inputRow[0]);
        assertTrue(2.1 == inputRow[1]);
        assertTrue(2.2 == inputRow[2]);
        assertTrue(2.3 == inputRow[3]);
    }


    /*************************************************************************
     * If no input map is supplied, then the output map must be smaller than *
     * the input source columns. If no input source columns are left to be   *
     * added into the input map by default, then this should return an       *
     * exception. A Neural Network can work with as many inputs as needed    * 
     * but cannot train without any.                                         *
     *************************************************************************/

    /**
     * Training.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadWithNoInputWithOutputMap1() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        double outputRow[]     = fileDataSet.getTrainingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(0.0 == outputRow[0]);
        assertTrue(0.1 == inputRow[0]);
        assertTrue(0.2 == inputRow[1]);
        assertTrue(0.3 == inputRow[2]);

        assertTrue(3 == inputColumnNumber);
        assertTrue(1 == outputColumnNumber);
    }

    /**
     * Training.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadWithNoInputWithOutputMap2() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        double outputRow[]     = fileDataSet.getTrainingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(0.0 == outputRow[0]);
        assertTrue(0.1 == outputRow[1]);
        assertTrue(0.2 == inputRow[0]);
        assertTrue(0.3 == inputRow[1]);

        assertTrue(2 == inputColumnNumber);
        assertTrue(2 == outputColumnNumber);
    }

    /**
     * Training.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadWithNoInputWithOutputMap3() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        double outputRow[]     = fileDataSet.getTrainingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(1 == inputRow.length);
        assertTrue(3 == outputRow.length);
        assertTrue(3 == outputColumnNumber);
        assertTrue(1 == inputColumnNumber);

        assertTrue(0.0 == outputRow[0]);
        assertTrue(0.1 == outputRow[1]);
        assertTrue(0.2 == outputRow[2]);
        assertTrue(0.3 == inputRow[0]);
    }

    /**
     * Training.
     * Check file loaded successfully when no input map is supplied and 
     * output map is supplied. In this case, the output is duplicating the 
     * source such that two output indexes would have same source values.
     */
    @Test
    public void testLoadWithNoInputWithOutputMap4() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        outputColumns.add(new VectorMap(2, new MapTransform(MathOperator.ADD, 2)));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTrainingInputRow(0);
        double outputRow[]     = fileDataSet.getTrainingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(0.0 == outputRow[0]);
        assertTrue(0.1 == outputRow[1]);
        assertTrue(0.2 == outputRow[2]);
        assertTrue(2.2 == outputRow[3]);
        assertTrue(0.3 == inputRow[0]);

        assertTrue(1 == inputColumnNumber);
        assertTrue(4 == outputColumnNumber);
    }

    /**
     * Training.
     * Output map taking all columns from source, leaving none for the input
     * map to use. An exception is expected in this case.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadWithNoInputWithOutputMap4all() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        outputColumns.add(new VectorMap(3, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();
    }

    /**
     * Testing.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadTestingWithNoInputWithOutputMap1() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        double outputRow[]     = fileDataSet.getTestingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(2.0 == outputRow[0]);
        assertTrue(2.1 == inputRow[0]);
        assertTrue(2.2 == inputRow[1]);
        assertTrue(2.3 == inputRow[2]);

        assertTrue(3 == inputColumnNumber);
        assertTrue(1 == outputColumnNumber);
    }

    /**
     * Testing.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadTestingWithNoInputWithOutputMap2() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        double outputRow[]     = fileDataSet.getTestingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(2.0 == outputRow[0]);
        assertTrue(2.1 == outputRow[1]);
        assertTrue(2.2 == inputRow[0]);
        assertTrue(2.3 == inputRow[1]);

        assertTrue(2 == inputColumnNumber);
        assertTrue(2 == outputColumnNumber);
    }

    /**
     * Testing.
     * Check file loaded successfully when no input map is supplied 
     * and output map is supplied.
     */
    @Test
    public void testLoadTestingWithNoInputWithOutputMap3() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        double outputRow[]     = fileDataSet.getTestingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(2.0 == outputRow[0]);
        assertTrue(2.1 == outputRow[1]);
        assertTrue(2.2 == outputRow[2]);
        assertTrue(2.3 == inputRow[0]);

        assertTrue(1 == inputColumnNumber);
        assertTrue(3 == outputColumnNumber);
    }

    /**
     * Testing.
     * Check file loaded successfully when no input map is supplied and 
     * output map is supplied. In this case, the output is duplicating the 
     * source such that two output indexes would have same source values.
     */
    @Test
    public void testLoadTestingWithNoInputWithOutputMap4() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        outputColumns.add(new VectorMap(2, new MapTransform(MathOperator.ADD, 2)));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();

        double inputRow[]      = fileDataSet.getTestingInputRow(0);
        double outputRow[]     = fileDataSet.getTestingOutputRow(0);
        int inputColumnNumber  = fileDataSet.getNumberOfInputColumns();
        int outputColumnNumber = fileDataSet.getNumberOfOutputColumns();
        assertTrue(2.0 == outputRow[0]);
        assertTrue(2.1 == outputRow[1]);
        assertTrue(2.2 == outputRow[2]);
        assertTrue(4.2 == outputRow[3]);
        assertTrue(2.3 == inputRow[0]);

        assertTrue(1 == inputColumnNumber);
        assertTrue(4 == outputColumnNumber);
    }

    /**
     * Testing.
     * Output map taking all columns from source, leaving none for the input
     * map to use. An exception is expected in this case.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadTestingWithNoInputWithOutputMap4all() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        List<VectorMap> outputColumns = new java.util.LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        outputColumns.add(new VectorMap(1, null));
        outputColumns.add(new VectorMap(2, null));
        outputColumns.add(new VectorMap(3, null));
        fileDataSet.setOutputColumns(outputColumns);
        fileDataSet.load();
    }


    /*************************************************************************
     * Each input and output maps can apply a transformation to the source   *
     * values which then are saved into the data set. The following tests    *
     * will ensure that the se transformations took place.                   *
     *************************************************************************/


    /**
     * Training.
     * Testing mapping with first column being the output
     * and all the other columns the input where the transform
     * is applying changes to original values.
     */
    @Test
    public void testOutputInputMapAndTransforms() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        // Override training range to use all file.
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
        
        // The output transform
        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.ADD, 2.4)));

        // The input transform
        List<VectorMap> inputColumns = new LinkedList<>();
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

    /**
     * Testing.
     * Testing mapping with first column being the output
     * and all the other columns the input where the transform
     * is applying changes to original values.
     */
    @Test
    public void testOutputInputMapAndTransformsTesting() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        // Override testing range to use all file.
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setHasTrainingRange(false);
        
        // The output transform
        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.ADD, 2.4)));

        // The input transform
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(1, new MapTransform(MathOperator.ADD, 2.5)));
        inputColumns.add(new VectorMap(2, new MapTransform(MathOperator.ADD, 2.5)));
        inputColumns.add(new VectorMap(3, new MapTransform(MathOperator.ADD, 2.5)));
        
        fileDataSet.setInputColumns(inputColumns);
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        
        // Get and verify data
        double[] inputRow0  = fileDataSet.getTestingInputRow(0);
        double[] outputRow0 = fileDataSet.getTestingOutputRow(0);
        assertNotNull(outputRow0); 
        assertTrue(1 == outputRow0.length); 
        assertTrue(2.4 == outputRow0[0]);
        assertNotNull(inputRow0);  
        assertTrue(3 == inputRow0.length); 
        assertTrue(2.6 == inputRow0[0]);
        assertTrue(2.7 == inputRow0[1]);
        assertTrue(2.8 == inputRow0[2]);

        double[] inputRow1  = fileDataSet.getTestingInputRow(1);
        double[] outputRow1 = fileDataSet.getTestingOutputRow(1);
        assertNotNull(outputRow1); 
        assertTrue(1 == outputRow1.length); 
        assertTrue(3.4 == outputRow1[0]);
        assertNotNull(inputRow1);  
        assertTrue(3 == inputRow1.length); 
        assertTrue(3.6 == inputRow1[0]);
        assertTrue(3.7 == inputRow1[1]);
        assertTrue(3.8 == inputRow1[2]);

        double[] inputRow2  = fileDataSet.getTestingInputRow(2);
        double[] outputRow2 = fileDataSet.getTestingOutputRow(2);
        assertNotNull(outputRow2); 
        assertTrue(1 == outputRow2.length); 
        assertTrue(4.4 == outputRow2[0]); 
        assertNotNull(inputRow2);  
        assertTrue(3 == inputRow2.length); 
        assertTrue(4.6 == inputRow2[0]);
        assertTrue(4.7 == inputRow2[1]);
        assertTrue(4.8 == inputRow2[2]);

        double[] inputRow3  = fileDataSet.getTestingInputRow(3);
        double[] outputRow3 = fileDataSet.getTestingOutputRow(3);
        assertNotNull(outputRow3); 
        assertTrue(1 == outputRow3.length); 
        assertTrue(5.4 == outputRow3[0]);
        assertNotNull(inputRow3);  
        assertTrue(3 == inputRow3.length); 
        assertTrue(5.6 == inputRow3[0]);
        assertTrue(5.7 == inputRow3[1]);
        assertTrue(5.8 == inputRow3[2]);

        double[] inputRow4  = fileDataSet.getTestingInputRow(4);
        double[] outputRow4 = fileDataSet.getTestingOutputRow(4);
        assertNotNull(outputRow4); 
        assertTrue(1 == outputRow4.length); 
        assertTrue(6.4 == outputRow4[0]);
        assertNotNull(inputRow4);  
        assertTrue(3 == inputRow4.length); 
        assertTrue(6.6 == inputRow4[0]);
        assertTrue(6.7 == inputRow4[1]);
        assertTrue(6.8 == inputRow4[2]);
    }

    /*************************************************************************
     *               Testing normalisation / deNormalisation                 *
     *************************************************************************/

    /**
     * Training.
     * Normalising and deNormalising data.
     */
    @Test
    public void testTrainingNormalisationDeNormalisation() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
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

    /**
     * Testing.
     * Normalising and deNormalising data.
     */
    @Test
    public void testTestingNormalisationDeNormalisation() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setHasTrainingRange(false);
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

    /**
     * Training.
     * Normalising and deNormalising data with one Output.
     */
    @Test
    public void testTrainingNormalisationDeNormalisationOneOutput() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] inputRow = fileDataSet.getTrainingInputRow(0);
        double[] outputRow = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.0 == inputRow[0]); 
        assertTrue(0.0 == inputRow[1]); 
        assertTrue(0.0 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(1);
        outputRow = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.25 == outputRow[0]); 
        assertTrue(0.25 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.25 == inputRow[1]); 
        assertTrue(0.25 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(2);
        outputRow = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.5 == outputRow[0]); 
        assertTrue(0.5 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTrainingInputRow(3);
        outputRow = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.75 == outputRow[0]); 
        assertTrue(0.75 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTrainingInputRow(4);
        outputRow = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(1 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[2]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        inputRow = fileDataSet.getTrainingInputRow(0);
        outputRow = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.1 == inputRow[0]); 
        assertTrue(0.2 == inputRow[1]); 
        assertTrue(0.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(1);
        outputRow = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(1.0 == outputRow[0]); 
        assertTrue(1.1 == inputRow[0]); 
        assertTrue(1.2 == inputRow[1]); 
        assertTrue(1.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(2);
        outputRow = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(2.0 == outputRow[0]); 
        assertTrue(2.1 == inputRow[0]); 
        assertTrue(2.2 == inputRow[1]); 
        assertTrue(2.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(3);
        outputRow = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(3.0 == outputRow[0]); 
        assertTrue(3.1 == inputRow[0]); 
        assertTrue(3.2 == inputRow[1]); 
        assertTrue(3.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(4);
        outputRow = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(4.0 == outputRow[0]); 
        assertTrue(4.1 == inputRow[0]); 
        assertTrue(4.2 == inputRow[1]); 
        assertTrue(4.3 == inputRow[2]);
    }

    /**
     * Testing.
     * Normalising and deNormalising data with one Output.
     */
    @Test
    public void testTestingNormalisationDeNormalisationOneOutput() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setHasTrainingRange(false);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, null));
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] inputRow = fileDataSet.getTestingInputRow(0);
        double[] outputRow = fileDataSet.getTestingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.0 == inputRow[0]); 
        assertTrue(0.0 == inputRow[1]); 
        assertTrue(0.0 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(1);
        outputRow = fileDataSet.getTestingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.25 == outputRow[0]); 
        assertTrue(0.25 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.25 == inputRow[1]); 
        assertTrue(0.25 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(2);
        outputRow = fileDataSet.getTestingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.5 == outputRow[0]); 
        assertTrue(0.5 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTestingInputRow(3);
        outputRow = fileDataSet.getTestingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.75 == outputRow[0]); 
        assertTrue(0.75 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTestingInputRow(4);
        outputRow = fileDataSet.getTestingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(1 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[2]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        inputRow = fileDataSet.getTestingInputRow(0);
        outputRow = fileDataSet.getTestingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.1 == inputRow[0]); 
        assertTrue(0.2 == inputRow[1]); 
        assertTrue(0.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(1);
        outputRow = fileDataSet.getTestingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(1.0 == outputRow[0]); 
        assertTrue(1.1 == inputRow[0]); 
        assertTrue(1.2 == inputRow[1]); 
        assertTrue(1.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(2);
        outputRow = fileDataSet.getTestingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(2.0 == outputRow[0]); 
        assertTrue(2.1 == inputRow[0]); 
        assertTrue(2.2 == inputRow[1]); 
        assertTrue(2.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(3);
        outputRow = fileDataSet.getTestingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(3.0 == outputRow[0]); 
        assertTrue(3.1 == inputRow[0]); 
        assertTrue(3.2 == inputRow[1]); 
        assertTrue(3.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(4);
        outputRow = fileDataSet.getTestingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(1 == outputRow.length);
        assertTrue(4.0 == outputRow[0]); 
        assertTrue(4.1 == inputRow[0]); 
        assertTrue(4.2 == inputRow[1]); 
        assertTrue(4.3 == inputRow[2]);
    }

    /**
     * Training.
     * Normalising and deNormalising data with two Outputs.
     */
    @Test
    public void testTrainingNormalisationDeNormalisationTwoOutput() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTrainingRangeIndex(1, 5);
        fileAttributes.setHasTestingRange(false);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.BIN)));
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.INV)));
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] inputRow = fileDataSet.getTrainingInputRow(0);
        double[] outputRow = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(0 == outputRow[0]); 
        assertTrue(1 == outputRow[1]); 
        assertTrue(0.0 == inputRow[0]); 
        assertTrue(0.0 == inputRow[1]); 
        assertTrue(0.0 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(1);
        outputRow = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.25 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.25 == inputRow[1]); 
        assertTrue(0.25 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(2);
        outputRow = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.5 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTrainingInputRow(3);
        outputRow = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.75 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTrainingInputRow(4);
        outputRow = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(1 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[2]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        inputRow = fileDataSet.getTrainingInputRow(0);
        outputRow = fileDataSet.getTrainingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(0 == outputRow[0]); 
        assertTrue(1 == outputRow[1]); 
        assertTrue(0.1 == inputRow[0]); 
        assertTrue(0.2 == inputRow[1]); 
        assertTrue(0.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(1);
        outputRow = fileDataSet.getTrainingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(1.1 == inputRow[0]); 
        assertTrue(1.2 == inputRow[1]); 
        assertTrue(1.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(2);
        outputRow = fileDataSet.getTrainingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(2.1 == inputRow[0]); 
        assertTrue(2.2 == inputRow[1]); 
        assertTrue(2.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(3);
        outputRow = fileDataSet.getTrainingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(3.1 == inputRow[0]); 
        assertTrue(3.2 == inputRow[1]); 
        assertTrue(3.3 == inputRow[2]);

        inputRow = fileDataSet.getTrainingInputRow(4);
        outputRow = fileDataSet.getTrainingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(4.1 == inputRow[0]); 
        assertTrue(4.2 == inputRow[1]); 
        assertTrue(4.3 == inputRow[2]);
    }

    /**
     * Testing.
     * Normalising and deNormalising data with two Outputs.
     */
    @Test
    public void testTestingNormalisationDeNormalisationTwoOutput() {
        DataSet fileDataSet = new FileDataSet(fileAttributes);
        fileAttributes.setTestingRangeIndex(1, 5);
        fileAttributes.setHasTrainingRange(false);
        List<VectorMap> inputColumns = new LinkedList<>();
        inputColumns.add(new VectorMap(1, null));
        inputColumns.add(new VectorMap(2, null));
        inputColumns.add(new VectorMap(3, null));
        fileDataSet.setInputColumns(inputColumns);

        List<VectorMap> outputColumns = new LinkedList<>();
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.BIN)));
        outputColumns.add(new VectorMap(0, new MapTransform(MathOperator.INV)));
        fileDataSet.setOutputColumns(outputColumns);

        fileDataSet.load();
        fileDataSet.normalise();

        double[] inputRow = fileDataSet.getTestingInputRow(0);
        double[] outputRow = fileDataSet.getTestingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.0 == inputRow[0]); 
        assertTrue(0.0 == inputRow[1]); 
        assertTrue(0.0 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(1);
        outputRow = fileDataSet.getTestingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.25 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.25 == inputRow[1]); 
        assertTrue(0.25 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(2);
        outputRow = fileDataSet.getTestingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.5 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.5 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTestingInputRow(3);
        outputRow = fileDataSet.getTestingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(0.75 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(0.75 == (Math.round(inputRow[2]*100.0)/100.0));

        inputRow = fileDataSet.getTestingInputRow(4);
        outputRow = fileDataSet.getTestingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(1 == (Math.round(inputRow[0]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[1]*100.0)/100.0)); 
        assertTrue(1 == (Math.round(inputRow[2]*100.0)/100.0));

        // Invert expecting original values
        fileDataSet.deNormalise();

        inputRow = fileDataSet.getTestingInputRow(0);
        outputRow = fileDataSet.getTestingOutputRow(0);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(0.0 == outputRow[0]); 
        assertTrue(0.1 == inputRow[0]); 
        assertTrue(0.2 == inputRow[1]); 
        assertTrue(0.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(1);
        outputRow = fileDataSet.getTestingOutputRow(1);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1.0 == outputRow[0]); 
        assertTrue(1.1 == inputRow[0]); 
        assertTrue(1.2 == inputRow[1]); 
        assertTrue(1.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(2);
        outputRow = fileDataSet.getTestingOutputRow(2);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(2.1 == inputRow[0]); 
        assertTrue(2.2 == inputRow[1]); 
        assertTrue(2.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(3);
        outputRow = fileDataSet.getTestingOutputRow(3);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(3.1 == inputRow[0]); 
        assertTrue(3.2 == inputRow[1]); 
        assertTrue(3.3 == inputRow[2]);

        inputRow = fileDataSet.getTestingInputRow(4);
        outputRow = fileDataSet.getTestingOutputRow(4);
        assertNotNull(inputRow);
        assertNotNull(outputRow);
        assertTrue(3 == inputRow.length);
        assertTrue(2 == outputRow.length);
        assertTrue(1 == outputRow[0]); 
        assertTrue(0 == outputRow[1]); 
        assertTrue(4.1 == inputRow[0]); 
        assertTrue(4.2 == inputRow[1]); 
        assertTrue(4.3 == inputRow[2]);
    }
}