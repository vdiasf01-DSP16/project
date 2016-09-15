package app.core.encog;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.util.kmeans.Centroid;

/**
 * The Encog MLDataPair Adaptor
 * 
 * @author Vasco
 *
 */
public class EncogMLDataPairAdaptor implements MLDataPair {

	/**
	 * The input double array.
	 */
	private double[] rowInput;
	
	/**
	 * The ideal double array.
	 */
	private double[] rowIdeal;

	/**
	 * Constructor for the adaptor accepting only the input data,
	 * not to be supervised.
	 *  
	 * @param row double[]
	 */
	public EncogMLDataPairAdaptor(double[] row) {
		this.rowInput = row;
		this.rowIdeal = null;
	}

	/**
	 * Constructor for the adaptor accepting the input and the ideal rows.
	 * 
	 * @param rowInput double[]
	 * @param rowIdeal double[]
	 */
	public EncogMLDataPairAdaptor(double[] rowInput, double[] rowIdeal) {
		this.rowInput = rowInput;
		this.rowIdeal = rowIdeal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Centroid<MLDataPair> createCentroid() {
		throw new IllegalStateException("createCentroid() Not implemented");
//		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public double[] getIdealArray() { 
		return rowIdeal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public double[] getInputArray() {
		return rowInput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setIdealArray(double[] data) {
		throw new IllegalStateException("setIdealArray(double[]) Not implemented");
//		this.rowIdeal = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setInputArray(double[] data) {
		throw new IllegalStateException("setInputArray(double[]) Not implemented");
//		this.rowInput = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public boolean isSupervised() {
		throw new IllegalStateException("isSupervised() Not implemented");
//		if ( rowIdeal != null ) return true;
//		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public MLData getIdeal() {
		return new EncogMLDataAdaptor(rowIdeal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public MLData getInput() {
		return new EncogMLDataAdaptor(rowInput);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public double getSignificance() {
		throw new IllegalStateException("getSignificance() Not implemented");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void setSignificance(double s) {
		throw new IllegalStateException("setSignificance(double) Not implemented");
	}
}
