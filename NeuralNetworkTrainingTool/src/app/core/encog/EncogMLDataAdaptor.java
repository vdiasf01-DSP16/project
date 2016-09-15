package app.core.encog;

import org.encog.ml.data.MLData;
import org.encog.util.kmeans.Centroid;

public class EncogMLDataAdaptor implements MLData {

	private double[] data;
	
	public EncogMLDataAdaptor(double[] data) {
		this.data = data;
	}

	@Override
	public Centroid<MLData> createCentroid() {
		System.out.println("createCentroid()");
		return null;
	}

	@Override
	public void add(int index, double value) { }

	@Override
	public void clear() { }

	@Override
	public MLData clone() {
		return this.clone();
	}

	@Override
	public double[] getData() {
		return data;
	}

	@Override
	public double getData(int index) {
		return data[index];
	}

	@Override
	public void setData(double[] data) { 
		this.data = data;
	}

	@Override
	public void setData(int index, double d) {
		data[index] = d;
	}

	@Override
	public int size() {
		return data.length;
	}
}
