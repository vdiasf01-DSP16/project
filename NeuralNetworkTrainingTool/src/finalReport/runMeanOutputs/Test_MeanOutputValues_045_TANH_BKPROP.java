package finalReport.runMeanOutputs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import app.core.dataSet.DataSet;
import app.core.dataSet.FileDataSet;
import app.core.dataSet.MathOperatorFactory;
import app.core.dataSet.MathOperatorKey;
import app.core.dataSet.VectorMap;
import app.core.encog.EncogMLDataSetTestingAdaptor;
import app.model.serializable.DataSetFileAttributes;
import app.model.serializable.FileAttributes;

/**
 * Reporting on the output neuron values progress as training happens.
 * 
 * @author Vasco
 *
 */
public class Test_MeanOutputValues_045_TANH_BKPROP {

    /**
     * The path to where we should be looking into for files 
     * to read and write.
     */
    private static final String PATH = "src/finalReport/runTest/test_045_TANH_BKPROP/";

    /**
     * The base file name for the results to be stored into.
     */
    private static final String BASE_FILE_NAME = "test_MeanOutputValues_045_TANH_BKPROP";

    /**
     * The Encog file with all network details to load and test.
     */
    private static final String ENCOG_FILE_NAME = "test_045_TANH_BKPROP.encog";
    
    /**
     * The application output file where all output will be saved into.
     */
    private static final String OUTPUT_FILE_NAME = BASE_FILE_NAME+".txt";
    
    /**
     * The output buffered file handle.
     */
    private static BufferedWriter outputFileBuffer = null;

    /**
     * The file where results will be stored into.
     */
    public static File file = null;
    
    /**
     * The main method.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Prepare the file to load.
        FileAttributes fileAttributes = new DataSetFileAttributes();
        fileAttributes.setFilename("HIGGS.csv");
        fileAttributes.setHeaderRows(0);
        fileAttributes.setSeparator(",");

        // Same range used for testing on all trained networks.
        fileAttributes.setTrainingRangeIndex(           1,  1_050_000);
        fileAttributes.setTestingRangeIndex(    1_050_001,  1_100_000);

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

        // Prepare the output file to append all output.
        prepareOutputFile();
        
        // Load and normalise the loaded data.
        append("Loading..");
        dataSet.load();

        append("Normalising..");
        dataSet.normalise();
        
        // Files start from 100 and go to... the end.
        append("Preparing all files..");
        append("====================================================================================================");
        append("=                         Signal and Background average values per epoch                           =");
        append("====================================================================================================");
        append("epoch,Tsignal0,Tbackground0,Fsignal0,Fbackground0,Tsignal1,Tbackground1,Fsignal1,Fbackground1");
        for ( int epoch = 0; true; epoch += 100 ) {
        	String prefix = "";
        	if ( epoch > 0 )  prefix = epoch+"_";
            File encogFile = new File(PATH+prefix+ENCOG_FILE_NAME);
            
            // Reached the end of the file chain.
            if ( ! encogFile.exists() ) {
            	System.out.println(encogFile+" does not exist");
            	break;
            }
            
            // The one and only Basic Network where all files will load into.
            BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence.loadObject(encogFile);

            appendProcessedResult(network, dataSet, epoch);
        }

        closeAll();
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
     * Close the output file and Encog shutdown.
     */
    private static void closeAll() {
        try {
            outputFileBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Encog.getInstance().shutdown();
    }

    public static void appendProcessedResult(BasicNetwork network, DataSet dataSet, int epoch) {
        // Prepare the Encog ML DataSet.
        MLDataSet dataSetTestingAdapted = new EncogMLDataSetTestingAdaptor(dataSet);

        int totalCorrectSignalSamples0     = 0;
        int totalWrongSignalSamples0       = 0;
        int totalCorrectBackgroundSamples0 = 0;
        int totalWrongBackgroundSamples0   = 0;
        int totalCorrectSignalSamples1     = 0;
        int totalWrongSignalSamples1       = 0;
        int totalCorrectBackgroundSamples1 = 0;
        int totalWrongBackgroundSamples1   = 0;
        
        double correctSignalSum0      = 0;
        double wrongSignalSum0        = 0;
        double correctBackgroundSum0  = 0;
        double wrongBackgroundSum0    = 0;
        double correctSignalSum1      = 0;
        double wrongSignalSum1        = 0;
        double correctBackgroundSum1  = 0;
        double wrongBackgroundSum1    = 0;

        // Go over the full testing set and check results.
        for ( MLDataPair pair : dataSetTestingAdapted ) {
            final MLData output = network.compute(pair.getInput());

            // This is specific to our case were we expect either one class or the other
            double netSignal      = output.getData(0);
            double netBackground  = output.getData(1);
            double idealSignal    = pair.getIdeal().getData(0);

            // Output neuron 0
            if ( idealSignal > 0.5 && netSignal >= 0.5 ) {
            	correctSignalSum0 += netSignal;
            	totalCorrectSignalSamples0++;
            } else if ( idealSignal > 0.5 && netSignal < 0.5 ) {
            	wrongSignalSum0 += netSignal;
            	totalWrongSignalSamples0++;
            } else if ( idealSignal < 0.5 && netSignal < 0.5 ) {
            	wrongBackgroundSum0 += netSignal;
            	totalWrongBackgroundSamples0++;
            } else if ( idealSignal < 0.5 && netSignal >= 0.5 ) {
            	correctBackgroundSum0 += netSignal;
            	totalCorrectBackgroundSamples0++;
            }
            // Output neuron 1
            if ( idealSignal > 0.5 && netSignal >= 0.5 ) {
            	correctSignalSum1 += netSignal;
            	totalCorrectSignalSamples1++;
            } else if ( idealSignal > 0.5 && netSignal < 0.5 ) {
            	wrongSignalSum1 += netSignal;
            	totalWrongSignalSamples1++;
            } else if ( idealSignal < 0.5 && netBackground < 0.5 ) {
            	wrongBackgroundSum1 += netBackground;
            	totalWrongBackgroundSamples1++;
            } else if ( idealSignal < 0.5 && netBackground >= 0.5 ) {
            	correctBackgroundSum1 += netBackground;
            	totalCorrectBackgroundSamples1++;
            }
        }

        double correctSignalMeanValue0     = ( (double) correctSignalSum0 / (double) totalCorrectSignalSamples0 );
        double correctBackgroundMeanValue0 = ( (double) correctBackgroundSum0 / (double) totalCorrectBackgroundSamples0 );
        double wrongSignalMeanValue0       = ( (double) wrongSignalSum0 / (double) totalWrongSignalSamples0 );
        double wrongBackgroundMeanValue0   = ( (double) wrongBackgroundSum0 / (double) totalWrongBackgroundSamples0 );
        double correctSignalMeanValue1     = ( (double) correctSignalSum1 / (double) totalCorrectSignalSamples1 );
        double correctBackgroundMeanValue1 = ( (double) correctBackgroundSum1 / (double) totalCorrectBackgroundSamples1 );
        double wrongSignalMeanValue1       = ( (double) wrongSignalSum1 / (double) totalWrongSignalSamples1 );
        double wrongBackgroundMeanValue1   = ( (double) wrongBackgroundSum1 / (double) totalWrongBackgroundSamples1 );

    	append(epoch 
        		+ "," + correctSignalMeanValue0
        		+ "," + correctBackgroundMeanValue0
        		+ "," + wrongSignalMeanValue0
        		+ "," + wrongBackgroundMeanValue0
        		+ "," + correctSignalMeanValue1
        		+ "," + correctBackgroundMeanValue1
        		+ "," + wrongSignalMeanValue1
        		+ "," + wrongBackgroundMeanValue1
            );

    }
}