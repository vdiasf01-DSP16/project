package app.core.encog;

import java.util.Iterator;

import org.encog.EncogError;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;

import app.core.dataSet.DataSet;

public class EncogMLDataSetAdaptor implements MLDataSet {

	private final DataSet t;
	private MLData idealMLData;
	
	public EncogMLDataSetAdaptor(DataSet t) {
		this.t = t;
	}

	@Override
	public Iterator<MLDataPair> iterator() { 
		return new EncogMLIteratorAdaptor(t);
	}
	
	@Override
	public MLDataPair get(int index) { 
		return new EncogMLDataPairAdaptor(t.getTrainingInputRow(index), t.getTrainingOutputRow(index));
	}
	@Override
	public int getIdealSize() { 
		System.out.println("getIdealSize() from MLDataSetAdaptor");
		return 0; 
	}
	
	@Override
	public int getInputSize() { 
		System.out.println("getIinputSize() from MLDataSetAdaptor");
		return 0; 
	}

	@Override
	public boolean isSupervised() {
		if ( idealMLData != null ) return true;
		return false;
	}

	@Override
	public long getRecordCount() {
		return t.getNumberOfTrainingRows();
	}

	@Override
	public void getRecord(long index, MLDataPair pair) {
//		if ( index%1000 == 0 ) processBar.increment(1000);
		final double[] rowIdeal = t.getTrainingOutputRow((int)index);
		final double[] rowInput = t.getTrainingInputRow((int)index);
		final MLDataPair source = new EncogMLDataPairAdaptor(rowInput,rowIdeal);
		pair.setInputArray(source.getInputArray());
		if (pair.getIdealArray() != null) {
			pair.setIdealArray(source.getIdealArray());
		}
	}

	@Override
	public MLDataSet openAdditional() {
		return new EncogMLDataSetAdaptor(t);
	}


	@Override
	public void add(MLData data1) {
		System.out.println("add(data)");
	}


	@Override
	public void add(MLData inputData, MLData idealData) {
		System.out.println("add(input,ideal)");
	}


	@Override
	public void add(MLDataPair inputData) {
		System.out.println("add(input)");
	}

	@Override
	public void close() {
//		processBar.close();
		System.out.println("close()");
	}

	@Override
	public int size() {
		System.out.println("size()");
		return 0;
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
		
		private DataSet t;

		public EncogMLIteratorAdaptor(DataSet t) {
			this.t = t;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public final boolean hasNext() {// TODO it was all rows..
			return this.currentIndex+1 < t.getNumberOfTrainingRows();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public final MLDataPair next() {
			if (!hasNext()) {
				return null;
			}
			currentIndex++;
			return new EncogMLDataPairAdaptor(t.getTrainingInputRow(currentIndex), t.getTrainingOutputRow(currentIndex));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public final void remove() {
			throw new EncogError("Called remove, unsupported operation.");
		}
	}
}
