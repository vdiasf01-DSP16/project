package app.core.encog;

import java.util.Iterator;

import org.encog.EncogError;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;

import app.core.dataSet.DataSet;

/**
 * The Encog MLDataSet Testing Adaptor.
 * 
 * @author jheaton
 * @author Vasco
 *
 */
public class EncogMLDataSetTestingAdaptor implements MLDataSet {

	/**
	 * The Testing dataset.
	 */
	private final DataSet dataSet;

	/**
	 * Constructor receiving the full data set.
	 * 
	 * @param dataSet DataSet
	 */
	public EncogMLDataSetTestingAdaptor(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Iterator<MLDataPair> iterator() { 
		return new EncogMLIteratorAdaptor(dataSet);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override public MLDataPair get(int index) { 
		return new EncogMLDataPairAdaptor(dataSet.getTestingInputRow(index), dataSet.getTestingOutputRow(index));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int getIdealSize() { 
		return dataSet.getNumberOfOutputColumns(); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override public int getInputSize() { 
		return dataSet.getNumberOfInputColumns(); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public boolean isSupervised() {
		if ( dataSet.getNumberOfOutputColumns() > 0 ) return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public long getRecordCount() {
		return dataSet.getNumberOfTestingRows();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void getRecord(long index, MLDataPair pair) {
		final double[] rowIdeal = dataSet.getTestingOutputRow((int)index);
		final double[] rowInput = dataSet.getTestingInputRow((int)index);
		final MLDataPair source = new EncogMLDataPairAdaptor(rowInput,rowIdeal);

		pair.setInputArray(source.getInputArray());
		
		if (pair.getIdealArray() != null) {
			pair.setIdealArray(source.getIdealArray());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public MLDataSet openAdditional() {
		return new EncogMLDataSetTestingAdaptor(dataSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void add(MLData data) {
		throw new EncogError("Called add(MLData), unsupported operation.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void add(MLData inputData, MLData idealData) {
		throw new EncogError("Called add(MLData input, MLData ideal), unsupported operation.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void add(MLDataPair inputData) {
		throw new EncogError("Called add(MLDataPair), unsupported operation.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void close() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public int size() {
		return dataSet.getNumberOfTestingRows();
	}

	/**
	 * An iterator to be used with the BasicMLDataSet. This iterator does not
	 * support removes.
	 * 
	 * @author jheaton
	 */
	public class EncogMLIteratorAdaptor implements Iterator<MLDataPair> {

		/**
		 * The index that the iterator is currently at.
		 */
		private int currentIndex = 0;

		/**
		 * Data set.
		 */
		private DataSet dataSet;

		/**
		 * Constructor.
		 * 
		 * @param dataSet
		 */
		public EncogMLIteratorAdaptor(DataSet dataSet) {
			this.dataSet = dataSet;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final boolean hasNext() {
			return this.currentIndex+1 < dataSet.getNumberOfTestingRows();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final MLDataPair next() {
			if (!hasNext()) {
				return null;
			}
			currentIndex++;

			return new EncogMLDataPairAdaptor(dataSet.getTestingInputRow(currentIndex), dataSet.getTestingOutputRow(currentIndex));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final void remove() {
			throw new EncogError("Called remove, unsupported operation.");
		}
	}
}