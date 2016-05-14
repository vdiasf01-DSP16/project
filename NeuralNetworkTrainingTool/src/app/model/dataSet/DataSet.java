package app.model.dataSet;

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
     * Loads source into memory, erasing previous loads and taking any extra changes 
     * made to the column maps, header rows, etc. Loads only the selected source training
     * part for the trainingDataSet.
     * 
     * This method should be implemented to centralise core functionality in one
     * place and avoid future regrets.
     */
    public void loadTraining() {
        throw new IllegalStateException("DataSet.loadTraining() must be implemented on the extending class.");
    };

    /**
     * Loads source into memory, erasing previous loads and taking any extra changes 
     * made to the column maps, header rows, etc. Loads only the selected source testing
     * part for the testingDataSet.
     * 
     * This method should be implemented to centralise core functionality in one
     * place and avoid future regrets.
     */
    public void loadTesting() {
        throw new IllegalStateException("DataSet.loadTesting() must be implemented on the extending class.");
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
}