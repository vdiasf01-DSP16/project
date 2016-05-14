package app.model.dataSet;

/**
 * Maps the source indexes to the final vector indexes.
 * It also calculates the value the vector should get,
 * given source array values and MapFunction to apply.
 * 
 * @author Vasco
 *
 */
public class VectorMap {

	/**
	 * The transformation function to convert source data 
	 * into ready final data.
	 */
	private final MapTransform mapTransform;
	
	/**
	 * The source index this map corresponds to.
	 */
	private final int sourceIndex;
	
	/**
	 * The target index this map corresponds to.
	 */
	private final int targetIndex;
	
	/**
	 * The supplied source double array.
	 */
	private double[] source;
	
	/**
	 * The constructor.
	 * 
	 * @param sourceIndex int
	 * @param targetIndex int
	 * @param mapTransform MpaTransform
	 */
	public VectorMap(int sourceIndex, int targetIndex, MapTransform mapTransform) {
		this.mapTransform = mapTransform;
		this.sourceIndex = sourceIndex;
		this.targetIndex = targetIndex;
	}
	
	/**
	 * The source double array list.
	 * 
	 * @param source double[]
	 */
	public void setSource(double[] source) {
		this.source = source;
	}

	/**
	 * The target value processed, to be stored at the final vector.
	 * 
	 * @return double transformed value
	 */
	public double getTargetValue() {
		if ( source == null ) throw new IllegalArgumentException("Source values were not set.");
		if ( source.length < sourceIndex ) throw new IllegalAccessError("Supplied source index is out of range.");
		
		if ( mapTransform == null ) return source[sourceIndex];

		return mapTransform.apply(source[sourceIndex]);
	}

	/**
	 * Returns the index where the outcome should be stored into.
	 * 
	 * @return int target index
	 */
	public int getTargetIndex() {
		return targetIndex;
	}
	
}
