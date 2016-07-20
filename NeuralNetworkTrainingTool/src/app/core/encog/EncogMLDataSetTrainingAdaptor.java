package app.core.encog;

import java.util.Iterator;

import org.encog.EncogError;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;

import app.core.dataSet.DataSet;

/**
 * The Encog Adaptor for the training data set, adapted for the Encog API.
 * 
 * @author jheaton
 * @author Vasco
 *
 */
public class EncogMLDataSetTrainingAdaptor implements MLDataSet {
	
	/**
	 * The training dataset.
	 */
	private final DataSet trainingDataSet;
	
	/**
	 * The output dataset in MLData format.
	 */
	private MLData outputMLData;
	
	/**
	 * Constructing an adaptor for supplied training DataSet.
	 * 
	 * @param trainingDataSet DataSet
	 */
	public EncogMLDataSetTrainingAdaptor(DataSet trainingDataSet) {
		this.trainingDataSet = trainingDataSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<MLDataPair> iterator() { 
		return new EncogMLIteratorAdaptor(trainingDataSet);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override public MLDataPair get(int index) { 
		return new EncogMLDataPairAdaptor(
				trainingDataSet.getTrainingInputRow(index), 
				trainingDataSet.getTrainingOutputRow(index));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getIdealSize() { 
		return trainingDataSet.getNumberOfOutputColumns();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getInputSize() { 
		return trainingDataSet.getNumberOfTrainingRows();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupervised() {
		if ( outputMLData != null ) return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getRecordCount() {
		return trainingDataSet.getNumberOfTrainingRows();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getRecord(long index, MLDataPair pair) {
		final double[] rowIdeal = trainingDataSet.getTrainingOutputRow((int)index);
		final double[] rowInput = trainingDataSet.getTrainingInputRow((int)index);
		
		final MLDataPair source = new EncogMLDataPairAdaptor(rowInput,rowIdeal);

		pair.setInputArray(source.getInputArray());
		if (pair.getIdealArray() != null) {
			pair.setIdealArray(source.getIdealArray());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MLDataSet openAdditional() {
		return new EncogMLDataSetTrainingAdaptor(trainingDataSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(MLData data) {
		// Not allowed to add any extra data to the dataset through the adaptor.
		throw new IllegalArgumentException("Operation not supported by Encog Adaptor.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(MLData inputData, MLData idealData) {
		// Not allowed to add any extra data to the dataset through the adaptor.
		throw new IllegalArgumentException("Operation not supported by Encog Adaptor.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(MLDataPair inputData) {
		// Not allowed to add any extra data to the dataset through the adaptor.
		throw new IllegalArgumentException("Operation not supported by Encog Adaptor.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		// TODO
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return trainingDataSet.getNumberOfTrainingRows();
	}

	/**
	 * An iterator to be used with the BasicMLDataSet. 
	 * This iterator does not support removes.
	 * 
	 * @author jheaton
	 */
	public class EncogMLIteratorAdaptor implements Iterator<MLDataPair> {

		/**
		 * The index that the iterator is currently at.
		 */
		private int currentIndex = 0;

		/**
		 * The training set to be used.
		 */
		private DataSet trainingDataSet;

		/**
		 * Constructing a new Iterator Adaptor.
		 * 
		 * @param trainingDataSet DataSet
		 */
		public EncogMLIteratorAdaptor(DataSet trainingDataSet) {
			this.trainingDataSet = trainingDataSet;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final boolean hasNext() {
			return this.currentIndex+1 < trainingDataSet.getNumberOfTrainingRows();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final MLDataPair next() {
			if (!hasNext()) return null;

			currentIndex++;

			return new EncogMLDataPairAdaptor(
					trainingDataSet.getTrainingInputRow(currentIndex), 
					trainingDataSet.getTrainingOutputRow(currentIndex));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override public final void remove() {
			throw new EncogError("Called remove, unsupported operation.");
		}
	}
}
