package finalReport.runAUC;

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
 * Template to calculate the AUC for a given trained neural network.
 * 
 * Calculations are taking the last 50k rows from the HIGGS dataset
 * between 1_050_001 and 1_100_000 row. Then find the signal from background
 * at 100 different threshold levels between 0 and 1 (values the output
 * will range from). To the outcomes, count anything below 0.5 as false and
 * above 0.5 as true for both outputs. The total TP, TN, FP and FN
 * will be stored against each of the 100 thresholds to then calculate the
 * specificity and sensitivity referring to the Signal versus background rejection.
 * 
 * @author Vasco
 *
 */
public class Test_AUC_Template {

    /**
     * The path to where we should be looking into for files 
     * to read and write.
     */
    private static final String PATH = "";//"src/finalReport/runTest/results/";

    /**
     * The base file name for the results to be stored into.
     */
    private static final String BASE_FILE_NAME = "test_AUC_045_TANH_RESIL2";

    /**
     * The Encog file with all network details to load and test.
     */
    private static final String ENCOG_FILE_NAME = "test_045_TANH_RESIL2.encog";
    
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
     * The final plot with 100 plots.
     */
    public static List<ThresholdResult> finalPlot;
    
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
//        fileAttributes.setTestingRangeIndex(    10_500_001, 11_000_000);
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

        // Loading the network to file to test.
        BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence
                .loadObject(new File(PATH+ENCOG_FILE_NAME));

        // Prepare the output file to append all output.
        prepareOutputFile();
        
        // Load and normalise the loaded data.
        append("Loading..");
        dataSet.load();

        append("Normalising..");
        dataSet.normalise();
        
        finalPlot = new LinkedList<ThresholdResult>();

        // Go from threshold 0.01 to 1.00 and collect the for the TP, TN, FP, FN
        append("Calculating thresholds..");
        for ( double threshold = 0.0; threshold < 1.0; threshold += 0.01 ) {
            // GroupId samples added and ready to be processed.
            ThresholdResult thresholdResult = getProcessedThreshold(network, dataSet, threshold);
            finalPlot.add(thresholdResult);
        }
        
        append("TP,TN,FP,FN,Positive Rate,Negative Rate,Threshold step");
        for( ThresholdResult result : finalPlot ) {
            append(result.getTP() + "," + result.getTN()
                    + "," + result.getFP() + "," + result.getFN()
                    + "," + result.getPositiveRate()
                    + "," + result.getNegativeRate()
                    + "," + result.getThreshold());
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

    /**
     * Calculates the total amount of true positives and negatives, and also
     * the total of false positives and negatives around the given threshold.
     * 
     * The ThresholdResult class will carry the totals values calculated and
     * will also return the true rate for the respective recall and precision.
     * 
     * @param network BasicNetwork
     * @param dataSet DataSet
     * @param threshold double
     * @return ThresholdResult
     */
    public static ThresholdResult getProcessedThreshold(BasicNetwork network, DataSet dataSet, double threshold) {
        // Count the number of:
        // TP: Expected signal and got signal
        int tp = 0;
        // FP: Expected background and got signal
        int fp = 0;
        // FN: Expected signal and got background
        int fn = 0;
        // TN: Expected background and got background
        // Not needed for final calculation, only added for completeness.
        int tn = 0; 

        // Prepare the Encog ML DataSet.
        MLDataSet dataSetTestingAdapted = new EncogMLDataSetTestingAdaptor(dataSet);
        
        // Go over the full testing set and check results.
        for ( MLDataPair pair : dataSetTestingAdapted ) {
            final MLData output = network.compute(pair.getInput());

            // This is specific to our case were we expect either one class or the other
            boolean netSignal      = output.getData(0)          > threshold ? true : false;
            boolean netBackground  = output.getData(1)          > threshold ? true : false;
            boolean fileSignal     = pair.getIdeal().getData(0) > threshold ? true : false;
            boolean fileBackground = pair.getIdeal().getData(1) > threshold ? true : false;

            if ( netSignal == fileSignal ) tp++;
            if ( netBackground == fileBackground ) tn++;
            if ( netSignal == fileBackground ) fp++;
            if ( netBackground == fileSignal ) fn++;
        }

        // Add values to the vector to be returned.
        return new ThresholdResult(threshold, tp, tn, fn, fp);
    }
    
    /**
     * Simple Threshold result class.
     * 
     * @author Vasco
     *
     */
    public static class ThresholdResult {

        private final double threshold;
        public final int truePositive;
        public final int trueNegative;
        public final int falsePositive;
        public final int falseNegative;

        /**
         * Constructor.
         * 
         * @param threshold
         * @param tp
         * @param tn
         * @param fn
         * @param fp
         */
        public ThresholdResult(double threshold, int tp, int tn, int fn, int fp) {
            this.threshold = threshold;
            this.truePositive = tp;
            this.trueNegative = tn;
            this.falsePositive = fp;
            this.falseNegative = fn;
        }
        
        public double getThreshold() { return threshold; }
        public double getTP() { return truePositive;     } 
        public double getTN() { return trueNegative;     }
        public double getFP() { return falsePositive;    }
        public double getFN() { return falseNegative;    }
        // Calculate precision
        public double getPositiveRate() { return truePositive / ( truePositive + falsePositive ); }
        // Calculate recall
        public double getNegativeRate() { return truePositive / ( truePositive + falseNegative ); }
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
        public double getTotal()   { return total;   }
        public double getResult()  { return result;  }
    }
}