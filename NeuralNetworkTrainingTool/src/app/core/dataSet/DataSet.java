package app.core.dataSet;

import java.util.Arrays;
import java.util.List;

/**
 * DataSet abstract class basic implementation for any data set source 
 * requiring to extend it at the data source's needs. Any neural network 
 * can then be trained, tested or simply turned online for an already 
 * trained neural network, accessing the source data through these methods.
 * 
 * @author Vasco
 *
 */
public abstract class DataSet {

    /**
     * The complete training data set source loaded and transformed for both 
     * supervised and un-supervised training.
     */
    protected double[][] trainingDataSet;
    
    /**
     * The complete test data set source loaded and transformed only
     * for supervised training.
     */
    protected double[][] testingDataSet;

    /**
     * The minimum values found per column across the training
     * and the testing sets.
     */
    protected double[] minValues;
    
    /**
     * The maximum values found per column across the training
     * and the testing sets.
     */
    protected double[] maxValues;
    
    /**
     * The Output column map defines the mapping of the output vector
     * in function of the input. This is only used on supervised learning
     * Neural Networks only. For un-supervised Neural Networks, this mapping
     * will be ignored.
     * 
     * Each element of the map will carry the source index and is in sequence
     * for the target index, also carrying any transformation required.
     */
    protected List<VectorMap> outputColumnMap;

    /**
     * The Input column map defines the mapping of the inputs that are to be
     * considered as part of the input vector. 
     * 
     * Each element of the map will carry the source index and is in sequence
     * for the target index, also carrying any transformation required.
     */
    protected List<VectorMap> inputColumnMap;

    /**
     * Loads the source into memory, erasing previous loads and taking any extra
     * changes made to the column maps, header rows, etc. Both sets will be updated
     * by this call: trainingDataSet and testingDataSet.
     * 
     * This method should be implemented to centralise core functionality in one
     * place and avoid future regrets.
     */
    public void load() {
        throw new IllegalStateException("DataSet.load() must be implemented on the extending class.");
    };

    /**
     * The training data set output row.
     * 
     * The array of doubles for the output vector double row given by the index.
     * These will carry already transformed values given by the ideal transformation function
     * that was available at the time of load. 
     * 
     * @param index
     * @return double[]
     */
    public double[] getTrainingOutputRow(int index) { 
        if ( trainingDataSet == null ) throw new IllegalStateException("Data not yet loaded.");
        if ( outputColumnMap == null ) throw new IllegalStateException("No output map defined.");

        return Arrays.copyOfRange(
                trainingDataSet[index], 
                0,
                getNumberOfOutputColumns() 
        );
    };

    /**
     * The training data set input row.<p>
     * 
     * The array of doubles for the input vector double row given by the index.
     * These will carry already transformed values given by the transformation 
     * function after load() was called. <p>
     * 
     * The Source[0..X] -> dataSet[index][input0 .. inputN]<p>
     * 
     * The input and output Maps are only used at loading time. Once data has 
     * been loaded, these will not be used. Depending on the inputMap outputMap, 
     * the input vector may differ.<p>
     * 
     *  - By default, if inputMap and outputMap are not set, the input will
     *    be the full array of doubles stored from source.<p>
     *    
     *  - If outputMap is set and input isn't, the input map will take remaining
     *    available columns in order as input vector by default<p>
     *  
     *  - If inputMap is set, will respect it regardless of the output map
     * 
     * @param index
     * @return double[]
     */
    public double[] getTrainingInputRow(int index) { 
        if ( trainingDataSet == null ) throw new IllegalStateException("Data not yet loaded.");
        if ( inputColumnMap == null ) throw new IllegalStateException("No input map defined.");

        int inputStartingIndex = 0;
        if ( outputColumnMap != null ) inputStartingIndex = outputColumnMap.size();
        
        return Arrays.copyOfRange(
                trainingDataSet[index], 
                inputStartingIndex,
                trainingDataSet[index].length
        );
    };
    
    /**
     * The testing data set output row.
     * 
     * The array of doubles for the output vector double row given by the index.
     * These will carry already transformed values given by the ideal transformation function
     * that was available at the time of load. 
     * 
     * @param index
     * @return double[]
     */
    public double[] getTestingOutputRow(int index) { 
        if ( testingDataSet == null ) throw new IllegalStateException("Data not yet loaded.");
        if ( outputColumnMap == null ) throw new IllegalStateException("No output map defined.");

        return Arrays.copyOfRange(
                testingDataSet[index], 
                0,
                getNumberOfOutputColumns() 
        );
    };

    /**
     * The testing data set input row.
     * 
     * The array of doubles for the input vector double row given by the index.
     * These will carry already transformed values given by the transformation 
     * function after load() was called. 
     * 
     * The Source[0..X] -> dataSet[index][input0 .. inputN]
     * 
     * The input and output Maps are only used at loading time. Once data has been
     * loaded, these will not be used, only to Depending on the inputMap outputMap, the input vector may differ.
     *  - By default, if inputMap and outputMap are not set, the input will
     *    be the full array of doubles stored from source.
     *    
     *  - If outputMap is set and input isn't, the input map will take remaining
     *    available columns in order as input vector by default
     *  
     *  - If inputMap is set, will respect it regardless of the output map
     * 
     * @param index
     * @return double[]
     */
    public double[] getTestingInputRow(int index) { 
        if ( testingDataSet == null ) throw new IllegalStateException("Data not yet loaded.");
        if ( inputColumnMap == null ) throw new IllegalStateException("input map not defined.");

        int inputStartingIndex = 0;
        if ( outputColumnMap != null ) inputStartingIndex = outputColumnMap.size();
        
        return Arrays.copyOfRange(
                testingDataSet[index], 
                inputStartingIndex, 
                testingDataSet[index].length
        );
    };

    /**
     * The output column mapping function.
     * 
     * Each element of the list will contain the source and the target index
     * towards the final vector. 
     *    
     * @param outputColumns
     */
    public void setOutputColumns(List<VectorMap> outputColumns) {
        this.outputColumnMap = outputColumns;
    };

    /**
     * The input columns mapping function.
     * 
     * Each element of the list will contain the source and the target index
     * towards the final vector. 
     *    
     * @param inputColumns
     */
    public void setInputColumns(List<VectorMap> inputColumns) {
        this.inputColumnMap = inputColumns;
    };

    /**
     * The total of input columns.
     * 
     * @return Integer
     */
    public Integer getNumberOfInputColumns() { 
        if ( inputColumnMap == null ) throw new IllegalStateException("input map not defined.");
        return inputColumnMap.size();
    };

    /**
     * The total of output columns.
     * 
     * @return Integer
     */
    public Integer getNumberOfOutputColumns() { 
        if ( outputColumnMap == null ) throw new IllegalStateException("No output map defined.");
        return outputColumnMap.size();
    };

    /**
     * The total number of training rows loaded.
     * 
     * @return Integer
     */
    public Integer getNumberOfTrainingRows() {
        return trainingDataSet.length;
    }

    /**
     * The total number of testing rows loaded.
     * 
     * @return Integer
     */
    public Integer getNumberOfTestingRows() {
        return testingDataSet.length;
    }
    
    /**
     * Normalise testing and training sets by first calculate what
     * are the minimum and maximum values for each column, and then 
     * normalise all values accordingly.
     */
    public void normalise() {
    	int numberOfColumns;
    	if ( trainingDataSet != null ) {
    		numberOfColumns = trainingDataSet[0].length;
    	} 
    	else if ( testingDataSet != null ) {
    		numberOfColumns = testingDataSet[0].length;
    	}
    	else {
    		// No data to normalise and no exception.
    		return;
    	}
    	
    	// Initialise the minimum and maximum values array.
    	minValues = new double[numberOfColumns]; 
    	maxValues = new double[numberOfColumns]; 

    	// Set minValues and maxValues initial values with their opposites for unbiased outcome.
        for( int index = 0 ; index < numberOfColumns ; index ++ ) {
            maxValues[index] = Double.NEGATIVE_INFINITY;
            minValues[index] = Double.POSITIVE_INFINITY;
        }

    	// Start with the training set to collect minimum and maximum values
    	if ( trainingDataSet != null ) {
        	for( int row = 0; row < trainingDataSet.length; row++ ) {
                for(int column = 0; column < trainingDataSet[row].length; column++ ) {
                    if ( minValues[column] > trainingDataSet[row][column] ) minValues[column] = trainingDataSet[row][column];
                    if ( maxValues[column] < trainingDataSet[row][column] ) maxValues[column] = trainingDataSet[row][column];
                }
        	}
    	}

    	// Check testing set and collect minimum and maximum values
    	if ( testingDataSet != null ) {
        	for( int row = 0; row < testingDataSet.length; row++ ) {
                for(int column = 0; column < testingDataSet[row].length; column++ ) {
                    if ( minValues[column] > testingDataSet[row][column] ) minValues[column] = testingDataSet[row][column];
                    if ( maxValues[column] < testingDataSet[row][column] ) maxValues[column] = testingDataSet[row][column];
                }
        	}
    	}
    	
    	// Minimum and maximum values calculated per column. Now normalise it all...
    	if ( trainingDataSet != null ) {
        	for( int row = 0; row < trainingDataSet.length; row++ ) {
                for(int column = 0; column < trainingDataSet[row].length; column++ ) {
                    double value = trainingDataSet[row][column];
                    Normalize norm = new Normalize(minValues[column], maxValues[column]);
                    trainingDataSet[row][column] = norm.apply(value);
                }
        	}
    	}

    	// Check testing set and collect minimum and maximum values
    	if ( testingDataSet != null ) {
        	for( int row = 0; row < testingDataSet.length; row++ ) {
                for(int column = 0; column < testingDataSet[row].length; column++ ) {
                    double value = testingDataSet[row][column];
                    Normalize norm = new Normalize(minValues[column], maxValues[column]);
                    testingDataSet[row][column] = norm.apply(value);
                }
        	}
    	}
    }

    /**
     * DeNormalise testing and training sets to their original values
     * by using the initially saved minimum and maximum values per column.
     * In case these are not yet saved, nothing will be done.
     */
    public void deNormalise() {
    	if ( minValues == null | maxValues == null ) return;

    	// Minimum and maximum values calculated per column. DeNormalise it all...
    	if ( trainingDataSet != null ) {
        	for( int row = 0; row < trainingDataSet.length; row++ ) {
                for(int column = 0; column < trainingDataSet[row].length; column++ ) {
                    double value = trainingDataSet[row][column];
                    DeNormalize deNorm = new DeNormalize(minValues[column], maxValues[column]); 
                    trainingDataSet[row][column] = deNorm.apply(value);
                }
        	}
    	}

    	// Check testing set and collect minimum and maximum values
    	if ( testingDataSet != null ) {
        	for( int row = 0; row < testingDataSet.length; row++ ) {
                for(int column = 0; column < testingDataSet[row].length; column++ ) {
                    double value = testingDataSet[row][column];
                    DeNormalize deNorm = new DeNormalize(minValues[column], maxValues[column]);
                    testingDataSet[row][column] = deNorm.apply(value);
                }
        	}
    	}
    }
}
