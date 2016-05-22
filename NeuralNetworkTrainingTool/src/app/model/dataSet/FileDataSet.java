package app.model.dataSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * FileDataSet extends abstract class DataSet to implement required defined
 * methods, use already instated default logic, and deal with the specifics 
 * of loading a file into memory. 
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
        // Validate possible new index ranges supplied.
        validateStartAndEndIndexes();

        // The number of columns in file
        calculateNumberOfColumnsInFile();

        // Ensure maps are loaded or with defaults 
        prepareMaps();

        // Calculate the total amount of columns in the final vector which is 
        // given by the number of elements in both input and output maps 
        // together.
        numberOfVectorColumns = outputColumnMap.size() + inputColumnMap.size();

        // Load Training and Testing data sets in one single pass.
        loadData();
    }

    /**
     * Checking all the start and end indexes supplied.
     * Start and ending rows cannot overlap.
     */
    private void validateStartAndEndIndexes() {
        // If no range has been set, complain about it.
        if ( ! fileAttributes.isHasTestingRange() & ! fileAttributes.isHasTrainingRange() )
            throw new IllegalArgumentException("No range set");
        
        // Zero or negative rows do not count.
        if ( fileAttributes.isHasTestingRange() & ( fileAttributes.getTestingStartIndex() <= 0 | 
                fileAttributes.getTestingEndIndex() <= 0 ) ) {
            throw new IllegalArgumentException("Cannot have zero or negative row indexes.");
        }
        if ( fileAttributes.isHasTrainingRange() & ( fileAttributes.getTrainingStartIndex() <= 0 | 
                fileAttributes.getTrainingEndIndex() <= 0 ) ) {
            throw new IllegalArgumentException("Cannot have zero or negative row indexes.");
        }
        
        // Starts before endings...
        if ( fileAttributes.getTestingStartIndex() > fileAttributes.getTestingEndIndex() )
            if ( fileAttributes.isHasTestingRange() )
                throw new IllegalArgumentException("Testing end row cannot be before the starting row.");
        if ( fileAttributes.getTrainingStartIndex() > fileAttributes.getTrainingEndIndex() )
            if ( fileAttributes.isHasTrainingRange() )
                throw new IllegalArgumentException("Training end row cannot be before the starting row.");

        // The following only matters if both ranges are expected to be used.
        if ( fileAttributes.isHasTestingRange() & fileAttributes.isHasTrainingRange() ) {
            // Overlap happen when both data set endings are bigger than both data set starts.
            if ( fileAttributes.getTrainingEndIndex() > fileAttributes.getTestingStartIndex() & 
                 fileAttributes.getTestingEndIndex() > fileAttributes.getTrainingStartIndex() ) {
                    throw new IllegalArgumentException("Cannot overlap data sets.");
            }
            // Start of the next must be after the end of previous.
            if ( fileAttributes.getTrainingStartIndex() < fileAttributes.getTestingStartIndex() ) {
                if ( fileAttributes.getTrainingEndIndex() >= fileAttributes.getTestingStartIndex() )
                    throw new IllegalArgumentException("Cannot overlap data sets.");
            }
            // Start of the next must be after the end of previous.
            if ( fileAttributes.getTestingStartIndex() < fileAttributes.getTrainingStartIndex() ) {
                if ( fileAttributes.getTestingEndIndex() >= fileAttributes.getTrainingStartIndex() )
                    throw new IllegalArgumentException("Cannot overlap data sets.");
            }
        }
        
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
     * Finds the first non header row to calculate the number of columns.
     */
    private void calculateNumberOfColumnsInFile() {
        // Get the buffer handler ready..
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(fileAttributes.getFilename()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            // Skip header rows.
            if ( fileAttributes.getHeaderRows() > 0 ) {
                int ignoreRows = fileAttributes.getHeaderRows();
                while ( bf.readLine() != null & ignoreRows-- > 0 );
            }

            // Grab first data line
            String line = bf.readLine();

            // Now we know the columns
            numberOfSourceColumnsPerRow = line.split(fileAttributes.getSeparator()).length;
            
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    /**
     * Allocates in memory the space that needs to be had for data sets.
     */
    private void initialiseDataSets() {
        // start 1 to 2 means 1 and 2 = 2 rows. Same for start 3 to 3 means 1 row
        int startTrainingIndex = fileAttributes.getTrainingStartIndex();
        int endTrainingIndex   = fileAttributes.getTrainingEndIndex();
        int totalTrainingRows  = endTrainingIndex - startTrainingIndex + 1;
        if ( fileAttributes.isHasTrainingRange()) super.trainingDataSet  = new double[totalTrainingRows][numberOfVectorColumns];
        int startTestingIndex  = fileAttributes.getTestingStartIndex();
        int endTestingIndex    = fileAttributes.getTestingEndIndex();
        int totalTestingRows   = endTestingIndex - startTestingIndex + 1;
        if ( fileAttributes.isHasTestingRange()) super.testingDataSet   = new double[totalTestingRows][numberOfVectorColumns];
    }

    /**
     * Loading both data sets in one go, finding which is to load first.
     */
    private void loadData() {
        // Allocate space in memory for this.
        initialiseDataSets();

        // Get the buffer handler ready..
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileAttributes.getFilename()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int currentRowPosition = 1;

        try {
            // If any header rows, ignore them, move buffered reader header to next rows.
            int ignoreRows = fileAttributes.getHeaderRows();
            if ( ignoreRows > 0 ) {
                // Move the file pointer to the end of the header rows.
                currentRowPosition += ignoreRows; 
                while ( ignoreRows > 0 ) {
                    String line = bufferedReader.readLine();
                    ignoreRows--;
                    // If the file finishes here, nothing else to load.
                    if ( line == null ) return;
                }
            }

            // If both are expected to be used
            if (fileAttributes.isHasTestingRange() & fileAttributes.isHasTrainingRange() ) {
                // Check what came first.. the test or the training.
                if ( fileAttributes.getTrainingStartIndex() < fileAttributes.getTestingStartIndex() ) {
                    while ( fileAttributes.getTrainingStartIndex() > currentRowPosition ) { 
                        // Premature end of file, finishes loading.
                        if ( bufferedReader.readLine() == null ) return; 
                        currentRowPosition++; 
                    }
                }
                else {
                    while ( fileAttributes.getTestingStartIndex() > currentRowPosition ) { 
                        // Premature end of file, finishes loading.
                        if ( bufferedReader.readLine() == null ) return; 
                        currentRowPosition++; 
                    }
                }

                // If Testing starts after, Training loads first
                if ( fileAttributes.getTrainingStartIndex() < fileAttributes.getTestingStartIndex() ) {
                    currentRowPosition = loadTraining(bufferedReader, currentRowPosition);
                    currentRowPosition = loadTesting(bufferedReader, currentRowPosition);
                }
                else {
                    currentRowPosition = loadTesting(bufferedReader, currentRowPosition);
                    currentRowPosition = loadTraining(bufferedReader, currentRowPosition);
                }
            }
            else {
                // Check which one to use.
                if ( fileAttributes.isHasTrainingRange() ) {
                    while ( fileAttributes.getTrainingStartIndex() > currentRowPosition ) { 
                        // Premature end of file, finishes loading.
                        if ( bufferedReader.readLine() == null ) return; 
                        currentRowPosition++; 
                    }
                }
                else {
                    while ( fileAttributes.getTestingStartIndex() > currentRowPosition ) { 
                        // Premature end of file, finishes loading.
                        if ( bufferedReader.readLine() == null ) return; 
                        currentRowPosition++; 
                    }
                }

                if ( fileAttributes.isHasTestingRange() ) {
                    loadTesting(bufferedReader, currentRowPosition);
                }
                else {
                    loadTraining(bufferedReader, currentRowPosition);
                }
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads the Training data set from file into memory and returns the row 
     * position in file for further processing.
     * 
     * @param bufferedReader BufferedReader
     * @param currentRowPosition int
     * @return int currentRowPosition
     */
    private int loadTraining(BufferedReader bufferedReader, int currentRowPosition) {
        int trainingDataSetIndex = 0;
        try {
            do {
                String line = bufferedReader.readLine();
                // Premature end of file is a sign of something going wrong somewhere.
                if ( line == null ) 
                    throw new ArrayIndexOutOfBoundsException("File contains only: "+currentRowPosition+" rows.");
                if ( trainingDataSetIndex >= trainingDataSet.length ) 
                    throw new IllegalArgumentException("Attempting to load more rows than initially allocated for.");

                if ( currentRowPosition < fileAttributes.getTrainingStartIndex() ) {
                    currentRowPosition++; 
                    continue;
                }
                trainingDataSet[trainingDataSetIndex] = getParsedRow(line);
                currentRowPosition++; 
                trainingDataSetIndex++;

            } while ( fileAttributes.getTrainingEndIndex() >= currentRowPosition );

        } catch (IOException e) {
            e.printStackTrace();
        } 

        return currentRowPosition;
    }
    
    /**
     * Loads the Testing data set from file into memory and returns the row 
     * position in file for further processing.
     * 
     * @param bufferedReader BufferedReader
     * @param currentRowPosition int
     * @return int currentRowPosition
     */
    private int loadTesting(BufferedReader bufferedReader, int currentRowPosition) {
        int testingDataSetIndex = 0;
        try {
            do {
                String line;
                line = bufferedReader.readLine();
                // Premature end of file is a sign of something going wrong somewhere.
                if ( line == null ) 
                    throw new ArrayIndexOutOfBoundsException("File contains only: "+currentRowPosition+" rows.");

                if ( testingDataSetIndex >= testingDataSet.length ) 
                    throw new IllegalArgumentException("Attempting to load more rows than initially allocated for.");
                
                if ( currentRowPosition < fileAttributes.getTestingStartIndex() ) {
                    currentRowPosition++; 
                    continue;
                }
                testingDataSet[testingDataSetIndex] = getParsedRow(line);
                testingDataSetIndex++;
                currentRowPosition++; 
            } while ( fileAttributes.getTestingEndIndex() >= currentRowPosition );

        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return currentRowPosition;
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
