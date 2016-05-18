package app.model.dataSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * FileDataSet extends abstract class DataSet to implement required 
 * methods in needs of being specified for File processing, and any
 * adjacent methods required to aid File data set source management.
 * 
 * @author Vasco
 *
 */
public class FileDataSet extends DataSet {
    
    /**
     * The extra file attributes.
     */
    private final FileAttributes fileAttributes;
    
    /**
     * The number of columns of data in file.
     */
    private int numberOfSourceColumnsPerRow = 0;

    /**
     * The number of columns in final vector.
     */
    private int numberOfVectorColumns = 0;

    /**
     * Constructing with FileAttributes to allow changes to requirements of
     * the load to be set away from this class.
     * 
     * @param fileAttributes
     */
    public FileDataSet(FileAttributes fileAttributes) {
        super();
        this.fileAttributes = fileAttributes;

        // Verify supplied file exists and can be read.
        BufferedReader testFile = null;
        try {
            FileReader fr = new FileReader(fileAttributes.getFilename());
            testFile = new BufferedReader(fr);
            testFile.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not fould: "+fileAttributes.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            testFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        // The number of columns in file
        calculateNumberOfColumnsInFile();

        // Ensure maps are loaded or with defaults 
        prepareMaps();

        // Calculate the total amount of columns in the final vector
        numberOfVectorColumns = outputColumnMap.size() + inputColumnMap.size();

        // Load the training data into memory
        loadTraining();
        
        // Load testing data into memory
        loadTesting();
    }

    /**
     * Set the maps to their defaults if not set.
     */
    private void prepareMaps() {
        // 1 - If input is not set and output is not set, default to link input from source 1-to-1
        // 2 - If input is set     and output is not set, nothing to do.
        // 3 - If input is not set and output is set, set input with remaining 1-to-1 or exception.
        // 4 - If input is set     and output is set, nothing to do.
        if ( inputColumnMap == null ) inputColumnMap = new LinkedList<>();
        if ( outputColumnMap == null ) outputColumnMap = new LinkedList<>();
        if ( inputColumnMap.size() > 0 ) return; 

        // Below are all the cases when the input map is not set.
        
        if ( outputColumnMap.size() == 0 ) {
            // inputColumnMap gets it all 1-to-1 between the file and input vector
            inputColumnMap = new LinkedList<VectorMap>();
            for( int fileColumn = 0; fileColumn < numberOfSourceColumnsPerRow; fileColumn++ ) {
                inputColumnMap.add(new VectorMap(fileColumn, null));
            }
        }
        else {
            // We have output but not input so input gets the rest unless there is nothing
            // else left which will result in an exception: we must have input mapping.
            List<Integer> sourceIndexesUsedByOutput = new LinkedList<>();
            inputColumnMap = new LinkedList<VectorMap>();
            for( VectorMap vector : outputColumnMap ) {
                sourceIndexesUsedByOutput.add(vector.getSourceIndex());
            }
            
            // Going over all source inputs, skip those already used by the output
            // assign to input the remaining source columns into the available target
            // indexes, creating new ones if required.
            for( int fileColumn = 0 ; fileColumn < numberOfSourceColumnsPerRow; fileColumn++ ) {
                if ( sourceIndexesUsedByOutput.contains(fileColumn) ) continue;
                inputColumnMap.add(new VectorMap(fileColumn, null));
            }
            if ( inputColumnMap.size() == 0 )
                throw new IllegalArgumentException("No Input map assigned.");
        }
    }
    
    /**
     * Finds the first row within ranges specified, calculate the number of 
     * columns found in it.
     */
    private void calculateNumberOfColumnsInFile() {
        // Get the buffer handler ready..
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(fileAttributes.getFilename()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // The file pointer.
        int currentRowPosition = 0;
        
        try {
            // Skip header rows.
            if ( fileAttributes.getHeaderRows() > 0 ) {
                int ignoreRows = fileAttributes.getHeaderRows();
                while ( bf.readLine() != null & ignoreRows-- > 0 ) { currentRowPosition++; };
            }

            // Only consider rows within the range specified.
            String line = null;
            do {
                line = bf.readLine();
                currentRowPosition++;
            }
            while ( line != null & ( fileAttributes.getTestingStartIndex() > currentRowPosition 
            		| fileAttributes.getTrainingStartIndex() > currentRowPosition ) );

            // Now we know the columns
            numberOfSourceColumnsPerRow = line.split(fileAttributes.getSeparator()).length;
            
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadTraining() {
        int startIndex        = fileAttributes.getTrainingStartIndex();
        int endIndex          = fileAttributes.getTrainingEndIndex();
        int totalTrainingRows = endIndex - startIndex;
        super.trainingDataSet = new double[totalTrainingRows][numberOfVectorColumns];

        // Get the buffer handler ready..
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(fileAttributes.getFilename()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int currentRowPosition   = 0;
        int trainingDataSetIndex = 0;

        try {
            // If any header rows, ignore them, move bf header to next rows.
            int ignoreRows = fileAttributes.getHeaderRows();
            if ( ignoreRows > 0 ) {
                while ( ignoreRows-- > 0 ) {
                    String line = bf.readLine();
                	if ( line == null ) break;
                	currentRowPosition++; 
                }
            }

            // If first testing or training index is further down, keep skipping...
            if ( startIndex > currentRowPosition+1 ) {
                while ( bf.readLine() != null & startIndex > currentRowPosition+1 ) { currentRowPosition++; }
            }

            // Load all lines to training applying map rules...
            do {
                String line = bf.readLine(); 
                if ( line == null ) break;
                trainingDataSet[trainingDataSetIndex] = getParsedRow(line);
                currentRowPosition++; 
                trainingDataSetIndex++;
            } while ( endIndex > currentRowPosition );
            
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadTesting() {
    	int startIndex        = fileAttributes.getTestingStartIndex();
        int endIndex          = fileAttributes.getTestingEndIndex();
        int totalTestingRows = endIndex - startIndex;
        super.testingDataSet = new double[totalTestingRows][numberOfVectorColumns];

        // Get the buffer handler ready..
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(fileAttributes.getFilename()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int currentRowPosition   = 0;
        int testingDataSetIndex = 0;

        try {
            // If any header rows, ignore them, move bf header to next rows.
            if ( fileAttributes.getHeaderRows() > 0 ) {
                int ignoreRows = fileAttributes.getHeaderRows();
                while ( bf.readLine() != null & ignoreRows-- > 0 ) { currentRowPosition++; }
            }

            // If first testing or training index is further down, keep skipping...
            if ( startIndex > currentRowPosition ) {
                while ( bf.readLine() != null & startIndex > currentRowPosition ) { currentRowPosition++; }
            }

            // Load all lines to training applying map rules...
            while ( endIndex > currentRowPosition ) { 
                String line = bf.readLine(); 
                if ( line == null ) break;
                currentRowPosition++; 
                testingDataSet[testingDataSetIndex] = getParsedRow(line);
                testingDataSetIndex++;
            }

            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes one line, splits into columns, applies the input/output 
     * mapping and any transforms, and returns the array of doubles
     * already normalised.
     * 
     * @param rowLine String
     * @return double[]
     */
    private double[] getParsedRow(String rowLine) {
        double[] sourceColumns = new double[numberOfSourceColumnsPerRow];
        double[] finalVector = new double[numberOfVectorColumns];
        int index = 0;
        for( String element : rowLine.split(fileAttributes.getSeparator())) {
            sourceColumns[index] = Double.parseDouble(element);
            index++;
        }

        // The final column vector is composed of the output and input mappings.
        // To apply this, each of the maps needs to be applied in turn, picking
        // the selected source column, and added in turn as per supplied sequence.
        int vectorIndex = 0;
        for( VectorMap outputVector : outputColumnMap ) {
            outputVector.setSource(sourceColumns);
            finalVector[vectorIndex] = outputVector.getTargetValue();
            vectorIndex++;
        }
        for( VectorMap inputVector : inputColumnMap ) {
            inputVector.setSource(sourceColumns);
            finalVector[vectorIndex] = inputVector.getTargetValue();
            vectorIndex++;
        }
        
        return finalVector;
    }
}
