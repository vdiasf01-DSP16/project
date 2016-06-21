package app.core.neuralNetwork;

/**
 * The Neural Network Topology abstract class for all the 
 * known implemented Topologies available.
 * 
 * @author Vasco
 *
 */
public abstract class NeuralNetworkPatternFactory {
	
	/**
	 * Get the matching Neural Network Pattern.
	 * 
	 * @param neuralNetworkPattern NeuralNetworkPatternKey
	 * @return NeuralNetworkPatternCore
	 */
	private static NeuralNetworkPatternCore<?> getNetworkPattern(NeuralNetworkPatternKey neuralNetworkPattern) {
		if ( NeuralNetworkPatternKey.FeedForwardPattern.equals(neuralNetworkPattern) ) {
			return new FeedForwardPatternAdaptor();
		}
		if ( NeuralNetworkPatternKey.ADALINEPattern.equals(neuralNetworkPattern) ) {
			return new ADALINEPatternAdaptor();
		}
		if ( NeuralNetworkPatternKey.HopfieldPattern.equals(neuralNetworkPattern) ) {
			return new HopfieldPatternAdaptor();
		}
		if ( NeuralNetworkPatternKey.ART1Pattern.equals(neuralNetworkPattern) ) {
			return new ART1PatternAdaptor();
		}
		return null;
	}

	/**
	 * The Description associated with the given topology.
	 * 
	 * @param neuralNetworkPattern
	 * @return String
	 */
	public static String getDescription(NeuralNetworkPatternKey neuralNetworkPattern) {
		return getNetworkPattern(neuralNetworkPattern).getDescription();
	}
	
	/**
	 * Returning the name of the Pattern given.
	 * 
	 * @param neuralNetworkPattern
	 * @return String
	 */
	public static String getName(NeuralNetworkPatternKey neuralNetworkPattern) {
		return getNetworkPattern(neuralNetworkPattern).getName();
		
	}
	
	
	//	public NeuralNetworkPattern getADALINEPattern() {
//		return new ADALINEPattern();
//	}
//
//	public NeuralNetworkPattern getART1Pattern() {
//		return new ART1Pattern();
//	}
//	
//	public NeuralNetworkPattern getBAMPattern() {
//		return new BAMPattern();
//	}
//	
//	public NeuralNetworkPattern getBoltzmannPattern() {
//		return new BoltzmannPattern();
//	}
//	
//	public NeuralNetworkPattern getCPNPattern() { 
//		return new CPNPattern();
//	}
//	
//	public NeuralNetworkPattern getElmanPattern() {
//		return new ElmanPattern();
//	}
//	
//	public NeuralNetworkPattern getFeedForwardPattern() {
//		return new FeedForwardPattern();
//	}
//	
//	public NeuralNetworkPattern getHopfieldPattern() {
//		return new HopfieldPattern();
//	}
//
//	public NeuralNetworkPattern get
//	return new JordanPattern.class
//	public NeuralNetworkPattern get
//	return new NeuralNetworkPattern.class
//	public NeuralNetworkPattern get
//	return new PatternError.class
//	public NeuralNetworkPattern get
//	return new PNNPattern.class
//	public NeuralNetworkPattern get
//	return new RadialBasisPattern.class
//	public NeuralNetworkPattern get
//	return new SOMPattern.class
//	public NeuralNetworkPattern get
//	return new SVMPattern.class
//	public NeuralNetworkPattern getFeedForwardTopology() {
//		return new FeedForwardPattern();
//	}
}
