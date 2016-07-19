package app.core.neuralNetwork;

import app.core.neuralNetwork.pattern.ADALINEPatternAdaptor;
import app.core.neuralNetwork.pattern.ART1PatternAdaptor;
import app.core.neuralNetwork.pattern.BAMPatternAdaptor;
import app.core.neuralNetwork.pattern.BoltzmannPatternAdaptor;
import app.core.neuralNetwork.pattern.CPNPatternAdaptor;
import app.core.neuralNetwork.pattern.ElmanPatternAdaptor;
import app.core.neuralNetwork.pattern.FeedForwardPatternAdaptor;
import app.core.neuralNetwork.pattern.HopfieldPatternAdaptor;
import app.core.neuralNetwork.pattern.JordanPatternAdaptor;
import app.core.neuralNetwork.pattern.PNNPatternAdaptor;
import app.core.neuralNetwork.pattern.RadialBasisPatternAdaptor;
import app.core.neuralNetwork.pattern.SVMPatternAdaptor;
import app.core.neuralNetwork.pattern.SomPatternAdaptor;

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
        if ( NeuralNetworkPatternKey.FeedForwardPattern.equals(neuralNetworkPattern) ) 
            return new FeedForwardPatternAdaptor();
        if ( NeuralNetworkPatternKey.ADALINEPattern.equals(neuralNetworkPattern) ) 
            return new ADALINEPatternAdaptor();
        if ( NeuralNetworkPatternKey.HopfieldPattern.equals(neuralNetworkPattern) ) 
            return new HopfieldPatternAdaptor();
        if ( NeuralNetworkPatternKey.ART1Pattern.equals(neuralNetworkPattern) )
            return new ART1PatternAdaptor();
        if ( NeuralNetworkPatternKey.BAMPattern.equals(neuralNetworkPattern) )
            return new BAMPatternAdaptor();
        if ( NeuralNetworkPatternKey.BoltzmannPattern.equals(neuralNetworkPattern) )
            return new BoltzmannPatternAdaptor();
        if ( NeuralNetworkPatternKey.CPNPattern.equals(neuralNetworkPattern) )
            return new CPNPatternAdaptor();
        if ( NeuralNetworkPatternKey.ElmanPattern.equals(neuralNetworkPattern) )
            return new ElmanPatternAdaptor();
        if ( NeuralNetworkPatternKey.JordanPattern.equals(neuralNetworkPattern) )
            return new JordanPatternAdaptor();
        if ( NeuralNetworkPatternKey.PNNPattern.equals(neuralNetworkPattern) )
            return new PNNPatternAdaptor();
        if ( NeuralNetworkPatternKey.RadialBasisPattern.equals(neuralNetworkPattern) )
            return new RadialBasisPatternAdaptor();
        if ( NeuralNetworkPatternKey.SOMPattern.equals(neuralNetworkPattern) )
            return new SomPatternAdaptor();
        if ( NeuralNetworkPatternKey.SVMPattern.equals(neuralNetworkPattern) )
            return new SVMPatternAdaptor();
        return null;
    }

    /**
     * Get the matching Neural Network Pattern.
     * 
     * @param neuralNetworkPattern NeuralNetworkPatternKey
     * @return NeuralNetworkPatternCore
     */
    public static NeuralNetworkPatternCore<?> getNetworkPattern(String neuralNetworkPattern) {
        if ( getName(NeuralNetworkPatternKey.FeedForwardPattern).equals(neuralNetworkPattern) ) 
            return new FeedForwardPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.ADALINEPattern).equals(neuralNetworkPattern) ) 
            return new ADALINEPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.HopfieldPattern).equals(neuralNetworkPattern) ) 
            return new HopfieldPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.ART1Pattern).equals(neuralNetworkPattern) )
            return new ART1PatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.BAMPattern).equals(neuralNetworkPattern) )
            return new BAMPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.BoltzmannPattern).equals(neuralNetworkPattern) )
            return new BoltzmannPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.CPNPattern).equals(neuralNetworkPattern) )
            return new CPNPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.ElmanPattern).equals(neuralNetworkPattern) )
            return new ElmanPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.JordanPattern).equals(neuralNetworkPattern) )
            return new JordanPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.PNNPattern).equals(neuralNetworkPattern) )
            return new PNNPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.RadialBasisPattern).equals(neuralNetworkPattern) )
            return new RadialBasisPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.SOMPattern).equals(neuralNetworkPattern) )
            return new SomPatternAdaptor();
        if ( getName(NeuralNetworkPatternKey.SVMPattern).equals(neuralNetworkPattern) )
            return new SVMPatternAdaptor();
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
}
