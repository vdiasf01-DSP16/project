package finalReport.runTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationFunction;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.train.BasicTraining;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.NeuralNetworkPattern;
import org.encog.persist.EncogDirectoryPersistence;

import app.core.activationFunction.ActivationFunctionFactory;
import app.core.activationFunction.ActivationFunctionKey;
import app.core.dataSet.DataSet;
import app.core.dataSet.FileDataSet;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.dataSet.VectorMap;
import app.core.encog.EncogMLDataSetTestingAdaptor;
import app.core.encog.EncogMLDataSetTrainingAdaptor;
import app.core.neuralNetwork.NeuralNetworkPatternCore;
import app.core.neuralNetwork.NeuralNetworkPatternFactory;
import app.core.neuralNetwork.NeuralNetworkPatternKey;
import app.model.serializable.DataSetFileAttributes;
import app.model.serializable.FileAttributes;

/**
 * First test to analyse the range required and efficiency of this type 
 * of neural network configuration.
 * 
 * @author Vasco
 *
 */
public class Test300TANH_x3 {

	/**
	 * Building the path to where Encog file will be generated.
	 */
	private static final String PATH = "";

	/**
	 * The base file name.
	 */
	private static final String BASE_FILE_NAME = "test_300_TANH_x3";

	/**
	 * The Encog file where the training network will be saved into.
	 */
	private static final String ENCOG_FILE_NAME = BASE_FILE_NAME+".encog";
	
	/**
	 * The application output file where all output will be saved into.
	 */
	private static final String OUTPUT_FILE_NAME = BASE_FILE_NAME+".txt";
	
	/**
	 * The output buffered file handle.
	 */
	private static BufferedWriter outputFileBuffer = null;

	/**
	 * The Activation Function to use.
	 */
	private static final ActivationFunctionKey ACTIVATION_FUNCTION_KEY = ActivationFunctionKey.ActivationTANH;

	/*************************************************************************
	 * 
	 *                         NETWORK CONFIGURATION
	 */
	private static final int INPUT_NEURONS = 28;
	private static final int OUTPUT_NEURONS = 2;
	/**
	 * 
	 *************************************************************************/
	
	/**
	 * The main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		
		// Prepare the file to load.
		FileAttributes fileAttributes = new DataSetFileAttributes();
		fileAttributes.setFilename("HIGGS.csv");
		fileAttributes.setHeaderRows(0);
		fileAttributes.setSeparator(",");
		fileAttributes.setTrainingRangeIndex(1, 10500000);
		fileAttributes.setTestingRangeIndex(    10500001, 11000000);

		// Feed the file into a File DataSet
		DataSet dataSet = new FileDataSet(fileAttributes);

		// Prepare the input map.
		List<VectorMap> inputList = new LinkedList<>();
		for ( int i = 1 ; i < 29; i++ ) {
			inputList.add(new VectorMap(i, null));
		}
		dataSet.setInputColumns(inputList);

		// Prepare the output map.
		List<VectorMap> outputList = new LinkedList<>();
		outputList.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.BIN)));
		outputList.add(new VectorMap(0, MathOperatorFactory.getMathOperation(MathOperatorKey.INV)));
		dataSet.setOutputColumns(outputList);

		// Set the activation function required and neural network final attributes.
		ActivationFunction af = (ActivationFunction)ActivationFunctionFactory
				.getActivationFunction(ACTIVATION_FUNCTION_KEY)
				.getActivationFunction();
		
		// The Network Pattern to use.
		NeuralNetworkPatternCore<?> patternCore = NeuralNetworkPatternFactory
				.getNetworkPattern(NeuralNetworkPatternKey.FeedForwardPattern);
		NeuralNetworkPattern pattern = (NeuralNetworkPattern) patternCore.getPattern();
		pattern.setActivationFunction(af);
		pattern.setInputNeurons(INPUT_NEURONS);
		/**
		 * Hidden layers
		 */
		pattern.addHiddenLayer(300);
		pattern.addHiddenLayer(300);
		pattern.addHiddenLayer(300);
		int hiddenLayers = 3;
		String hiddenLayersSpec = "[ 300 , 300 , 300 ]";
		pattern.setOutputNeurons(OUTPUT_NEURONS);
		BasicNetwork network = (BasicNetwork) pattern.generate();

		// Loading the network to file if exists..
		if ( (new File(PATH+ENCOG_FILE_NAME)).exists() ) 
			network = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(PATH+ENCOG_FILE_NAME));

		// Prepare the output file to append all output.
		prepareOutputFile();
		
		// Load and normalise the loaded data.
		append("Loading file");
		dataSet.load();
		Long loadTime = System.currentTimeMillis();
		dataSet.normalise();
		Long normaliseTime = System.currentTimeMillis();
		
		// Set the loaded data with the Encog API through the built Adaptor.
		MLDataSet dataSetTrainingAdapted = new EncogMLDataSetTrainingAdaptor(dataSet);
		
		// Use a Resilient Propagation training strategy.
		BasicTraining train = (BasicTraining) new ResilientPropagation(network, dataSetTrainingAdapted);
		
		// Let the API decide the best use of CPU
		((ResilientPropagation)train).setThreadCount(0);

		// Start the training iterations.
		append("Training...");
		Long startTrainingTime = System.currentTimeMillis();
		int epoch = 0;
		append("Epoch Number,Network Error,Total Rows,Correct Rows,Accuracy Percent");
		do {
			train.iteration();
			epoch++;
			
			// Friendly message to user to let know about progress.
			if ( epoch % 10 == 0 ) {
				Result result = getError(network, dataSet);
				
				append(epoch + ","+train.getError() + "," + result.getTotal()
				  + ","+result.getCorrect() + "," +result.getResult());
			}

			// Too good to continue
			if ( train.getError() < 0.0001 ) break;
			
			// Enough epochs for today..
			if ( epoch > 2500 ) break;
			
		} while ( true );
		Long endTrainingTime = System.currentTimeMillis();
		
		// Finalise the training.
		train.finishTraining();

		// One more final test to print results and we are done.
		Result result = getError(network, dataSet);
		append("");
		append("====================================================");
		append("                      DETAILS");
		append("----------------------------------------------------");
		append("Encog Trained File: " + ENCOG_FILE_NAME);
		append("Input neurons:      " + INPUT_NEURONS);
		append("Ouput neurons:      " + OUTPUT_NEURONS);
		append("Hidden Layers:      " + hiddenLayers + " " + hiddenLayersSpec);
		append("Pattern Used:       " + patternCore.getName());
		append("ActivationFunction: " + ActivationFunctionFactory.getName(ACTIVATION_FUNCTION_KEY));
		append("====================================================");
		append("                      RESULTS");
		append("----------------------------------------------------");
		append("Final Encog Error:  " + train.getError());
		append("Total rows:         " + (int)result.getTotal());
		append("Corrent answers:    " + (int)result.getCorrect());
		append("Accuracy:           " + result.getResult() + "%");
		append("====================================================");
		append("                    TIME SPENT:");
		append("----------------------------------------------------");
		append("Startup Time:   " + (loadTime - startTime)/1000.0 + "s");
		append("Load Time:      " + (normaliseTime - loadTime)/1000.0 + "s");
		append("Normalise Time: " + (startTrainingTime - normaliseTime)/1000.0 + "s");
		append("Training Time:  " + (endTrainingTime - startTrainingTime)/1000.0 + "s");
		append("----------------------------------------------------");
		append("Total Time:     " + (endTrainingTime - startTime)/1000.0 + "s");
		append("====================================================");
		
		// Saving the network to file for next time..
		EncogDirectoryPersistence.saveObject(new File(PATH+ENCOG_FILE_NAME), network);
		
		Encog.getInstance().shutdown();
		closeFile();
	}

	/**
	 * Given a network and a dataSet, calculate the error by going over all 
	 * set testing range and check what the network says comparing with the 
	 * dataSet expectations.
	 * 
	 * @param network BasicNetwork
	 * @param dataSet DataSet
	 * @return accuracy percent double
	 */
	public static Result getError(BasicNetwork network, DataSet dataSet) {
		// Prepare the Encog ML DataSet.
		MLDataSet dataSetTestingAdapted = new EncogMLDataSetTestingAdaptor(dataSet);
		
		// Final values.
		double correct = 0.0;
		double total = 0.0;
		double result = 0.0;

		// Go over the full testing set and check results.
		for ( MLDataPair pair : dataSetTestingAdapted ) {
	        final MLData output = network.compute(pair.getInput());

	        // This is specific to our case were we expect either one class or the other
	        boolean netSignal      = output.getData(0)          > 0.5 ? true : false;
	        boolean netBackground  = output.getData(1)          > 0.5 ? true : false;
	        boolean fileSignal     = pair.getIdeal().getData(0) > 0.5 ? true : false;
	        boolean fileBackground = pair.getIdeal().getData(1) > 0.5 ? true : false;

	        total++;

	        if ( netSignal == fileSignal && netBackground == fileBackground ) correct++;
		}

		result = ((correct/total)*100.0);
		
		return new Result(correct, total, result);
	}

	/**
	 * Prepare the buffer to write to file.
	 */
	private static void prepareOutputFile() {
		try {
			outputFileBuffer = new BufferedWriter(new FileWriter(PATH+OUTPUT_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Append to the opened output file the given text.
	 * 
	 * @param text String
	 */
	private static void append(String text) {
		// Print the same text to STDOUT
		System.out.println(text);

		try {
			outputFileBuffer.write(text+"\n");
			outputFileBuffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the output file.
	 */
	private static void closeFile() {
		try {
			outputFileBuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Simple Result class.
	 * 
	 * @author Vasco
	 *
	 */
	public static class Result {
		private final double correct;
		private final double total;
		private final double result;

		/**
		 * Constructor.
		 * 
		 * @param correct
		 * @param total
		 * @param result
		 */
		public Result(double correct, double total, double result) {
			this.correct = correct;
			this.total = total;
			this.result = result;
		}
		
		public double getCorrect() { return correct; }
		public double getTotal() { return total; }
		public double getResult() { return result; }
	}
}