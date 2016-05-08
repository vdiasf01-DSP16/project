package app.model.dataSet;

import java.util.Arrays;
import java.util.Map;

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
	 * The complete data set source loaded and transformed.
	 */
	protected double[][] dataSet;
	
	/**
	 * The Output column map defines the mapping of the output vector
	 * in function of the input. This is only used on supervised learning
	 * Neural Networks only. For un-supervised Neural Networks, this mapping
	 * will be ignored.
	 * 
	 * The first Map integer corresponds to the input vector index where data
	 * comes from and is keyed to a Map with the integer index of the output
	 * vector and a transform function that will apply changes to the input
	 * value, if required. By default, will map input index value to output
	 * index value.
	 */
	protected Map<Integer, Map<Integer,MapTransform>> outputColumnMap;

	/**
	 * The Input column map defines the mapping of the inputs that are to be
	 * considered as part of the input vector. These are defined in function 
	 * of the data source input columns. 
	 * 
	 * The first Map integer corresponds to the source column index where data
	 * comes from and is keyed to a Map with the integer index of the input 
	 * vector and a transform function that will apply changes to the source
	 * value, if required. By default, will map source index value to input
	 * index value.
	 */
	protected Map<Integer, Map<Integer,MapTransform>> inputColumnMap;

	/**
	 * Loads the source into memory, erasing previous loads and taking any extra
	 * changes made to the column maps, header rows, etc.
	 * 
	 * This method should be implemented to centralise core functionality in one
	 * place and avoid future regrets.
	 */
	public void load() {
		throw new IllegalStateException("DataSet.load() must be implemented on the extending class.");
	};

    /**
     * The array of doubles for the output vector double row given by the index.
     * These will carry already transformed values given by the ideal transformation function
     * that was available at the time of load. 
     * 
     * @param index
     * @return double[]
     */
    public double[] getOutputRow(int index) { 
   		if ( dataSet == null ) throw new IllegalStateException("Data not yet loaded.");
       	if ( outputColumnMap == null ) {
    		return null;
       	}
    	verifyLoaded();
		return Arrays.copyOfRange(
				dataSet[index], 
				0,
				getNumberOfOutputColumns() 
		);
    };

    /**
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
	public double[] getInputRow(int index) { 
		verifyLoaded();
		
		// Input column map by default maps one-to-one
		if ( inputColumnMap == null ) {
			// If the output is not set, use all range
			if ( outputColumnMap == null ) {
				return dataSet[index];
			}
		}
		return Arrays.copyOfRange(
				dataSet[index], 
				getNumberOfOutputColumns(), 
				dataSet[index].length
		);
	};

	/**
	 * The output column mapping function.
	 * 
	 * First index of the map refers to the input vector index.
	 * Second map integer refers to the output index where the data will be stored.
	 * MapTransform will take the input value and transform it before storing into
	 * the output vector
	 *    
	 * @param outputColumns
	 */
    public void setOutputColumns(Map<Integer, Map<Integer, MapTransform>> outputColumns) {
		this.outputColumnMap = outputColumns;
    };

    /**
	 * The input columns mapping function.
	 * 
	 * First index of the map refers to the source vector index.
	 * Second map integer refers to the input index where the data will be stored.
	 * MapTransform will take the source value and transform it before storing into
	 * the input vector
	 *    
     * @param inputColumns
     */
    public void setInputColumns(Map<Integer, Map<Integer, MapTransform>> inputColumns) {
		this.inputColumnMap = inputColumns;
    };

	/**
	 * Define the number of rows from the original source that are to
	 * be used for training purposes. If not set, all data set will be used.
	 * 
	 * @param start row index.
	 * @param end row index.
	 */
	public void setTrainingRowRange(int start, int end) {
		throw new IllegalStateException("DataSet.setTrainingRowRange() must be implemented on the extending class.");
	};

	/**
	 * Define the number of rows from the original source that are to
	 * be used for testing. If not set, all data set will be used.
	 * 
	 * @param start row index.
	 * @param end row index.
	 */
	public void setTestingRowRange(int start, int end) {
		throw new IllegalStateException("DataSet.setTestingRowRange() must be implemented on the extending class.");
	};

	/**
	 * The total number of rows in source.
	 * 
	 * @return Integer
	 */
	public Integer getTotalNumberOfSourceRows() { 
		verifyLoaded();
		return dataSet.length; 
	};

	/**
	 * The total number of columns per row on source.
	 * 
	 * @return Integer
	 */	
	public Integer getTotalNumberOfSourceColumns() { 
		verifyLoaded();
		return dataSet[0].length; 
    };

	/**
	 * The total of input columns.
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfInputColumns() { 
		verifyLoaded();

		// outputColumnMap drives the business... the number of
		// input columns is the total columns minum the set output
		// columns through the map.
		if ( outputColumnMap == null ) {
			return dataSet[0].length;
		}
		
		return dataSet[0].length - outputColumnMap.size();
	};

	/**
	 * The total of ideal columns.
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfOutputColumns() { 
		verifyLoaded();
		// Output row may have less columns than the dataSet
		// and it will be ok to be null at this point. e.g.: For
		// un-supervised Neural Networks.
		if ( outputColumnMap != null ) 
    		return outputColumnMap.size();
		return 0;
	};

	/**
	 * Verify if the load has already happen.
	 */
	private void verifyLoaded() {
		if ( dataSet == null )    throw new IllegalStateException("DataSetSource was not yet loaded. Forgot to load()?");
		if ( dataSet[0] == null ) throw new IllegalStateException("DataSetSource was not yet loaded. Forgot to load()?");
	}
}
