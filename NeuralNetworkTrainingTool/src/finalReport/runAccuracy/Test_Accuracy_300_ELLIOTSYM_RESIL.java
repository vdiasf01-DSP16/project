package finalReport.runAccuracy;

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
 * Reporting on the total correct answers as a percentage 
 * over the total pool of samples.
 * 
 * @author Vasco
 *
 */
public class Test_Accuracy_300_ELLIOTSYM_RESIL {

    /**
     * The path to where we should be looking into for files 
     * to read and write.
     */
    private static final String PATH = "src/finalReport/runTest/test_300_ELLIOTSYM_RESIL/";

    /**
     * The base file name for the results to be stored into.
     */
    private static final String BASE_FILE_NAME = "test_Accuracy_300_ELLIOTSYM_RESIL";

    /**
     * The Encog file with all network details to load and test.
     */
    private static final String ENCOG_FILE_NAME = "test_300_ELLIOTSYM_RESIL.encog";
    
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
        
        int epoch = 0;
        // Files start from 100 and go to... the end.
        append("Preparing all files..");
        append("====================================================================================================");
        append("=                       Accuracy test percentage over all samples per epoch                        =");
        append("====================================================================================================");
        append("epoch,Correct Signal,Correct Background,Wrong Signal,Wrong Background,Total Signal Samples,"
        		+ "Total Background Samples,Total Samples,Signal Accuracy,Background Accuracy,Total Accuracy");

        for ( epoch = 100; true; epoch += 100 ) {
        	String prefix = epoch+"_";
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
        
        // The last file does not have a prefix.
        File encogFile = new File(PATH+ENCOG_FILE_NAME);
        
        // The one and only Basic Network where all files will load into.
        BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence.loadObject(encogFile);

        // Epoch is increased by 100 to get the last trained network in place.
        appendProcessedResult(network, dataSet, epoch);

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

        int totalCorrectSignalSamples     = 0;
        int totalWrongSignalSamples       = 0;
        int totalCorrectBackgroundSamples = 0;
        int totalWrongBackgroundSamples   = 0;
        int totalSamples                  = 0;
        int totalIdealSignalSamples       = 0;
        int totalIdealBackgroundSamples   = 0;
        
        // Go over the full testing set and check results.
        for ( MLDataPair pair : dataSetTestingAdapted ) {
            final MLData output = network.compute(pair.getInput());

            // This is specific to our case were we expect either one class or the other
            boolean netSignal      = output.getData(0) > output.getData(1) ? true : false;
            boolean idealSignal    = pair.getIdeal().getData(0) > 0.5 ? true : false;
            
            if (  idealSignal &&  netSignal ) totalCorrectSignalSamples++;
            if (  idealSignal && !netSignal ) totalWrongSignalSamples++;
            if ( !idealSignal &&  netSignal ) totalWrongBackgroundSamples++;
            if ( !idealSignal && !netSignal ) totalCorrectBackgroundSamples++;
            if (  idealSignal ) totalIdealSignalSamples++;
            if ( !idealSignal ) totalIdealBackgroundSamples++;

            totalSamples++;
        }

        double totalAccuracy = 100.0 * ( 
        		(double) ( totalCorrectSignalSamples + totalCorrectBackgroundSamples ) /
        		(double) totalSamples );

        double signalAccuracy = 100.0 * ( 
        		(double) totalCorrectSignalSamples /
        		(double) totalIdealSignalSamples  );

        double backgroundAccuracy = 100.0 * ( 
        		(double) totalCorrectBackgroundSamples /
        		(double) totalIdealBackgroundSamples );

    	append(epoch 
        		+ "," + totalCorrectSignalSamples
        		+ "," + totalCorrectBackgroundSamples
        		+ "," + totalWrongSignalSamples
        		+ "," + totalWrongBackgroundSamples
        		+ "," + totalIdealSignalSamples
        		+ "," + totalIdealBackgroundSamples
        		+ "," + totalSamples
        		+ "," + signalAccuracy
        		+ "," + backgroundAccuracy
        		+ "," + totalAccuracy
            );

    }
}