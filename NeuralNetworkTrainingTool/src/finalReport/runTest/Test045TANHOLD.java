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
public class Test045TANHOLD {

	/**
	 * Building the path to where Encog file will be generated.
	 */
	private static final String PATH = "";//"src"+File.separator
//			+ "finalReport"+File.separator
//			+ "runTest"+File.separator;

	/**
	 * The Encog file where the training network will be saved into.
	 */
	private static final String ENCOG_FILE_NAME = "test_045_TANH.encog";
	
	/**
	 * The application output file where all output will be saved into.
	 */
	private static final String OUTPUT_FILE_NAME = "test_045_TANH.txt";
	
	/**
	 * The output buffered file handle.
	 */
	private static BufferedWriter outputFileBuffer = null;

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
				.getActivationFunction(ActivationFunctionKey.ActivationTANH)
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
		pattern.addHiddenLayer(0);
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
		dataSet.normalise();

		// Set the loaded data with the Encog API through the built Adaptor.
		MLDataSet dataSetTrainingAdapted = new EncogMLDataSetTrainingAdaptor(dataSet);
		
		// Use a Resilient Propagation training strategy.
		BasicTraining train = (BasicTraining) new ResilientPropagation(network, dataSetTrainingAdapted);
		
		// Let the API decide the best use of CPU
		((ResilientPropagation)train).setThreadCount(0);

		// Start the training iterations.
		append("Training...");
		int epoch = 0;
		do {
			train.iteration();
			epoch++;
			
			// Friendly message to user to let know about progress.
			if ( epoch % 10 == 0 ) {
				Result result = getError(network, dataSet);
				
				append("#"+epoch
				  + " NetError: "+train.getError()
				  + " total: " + result.getTotal()
				  + " correct: "+result.getCorrect()
				  + " => " +result.getResult() + "%");
			}

			// Too good to continue
			if ( train.getError() < 0.0001 ) break;
			
			// Enough epochs for today..
			if ( epoch > 2500 ) break;
			
		} while ( true );
		
		// The final error
		append("Error: "+train.getError());

		// Finalise the training.
		train.finishTraining();

		// One more final test to print results and we are done.
		append("Final testing...");
		Result result = getError(network, dataSet);
		append("TOTAL: "+result.getTotal()+" CORRECT: "+result.getCorrect()+" => "+result.getResult()+"%");
		
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

/** - running for 20 hours 
Loading file...
Training...
#10 Error: 0.6468645975024768 RError: 5.8452116904233815
#20 Error: 0.3921778618410256 RError: 16.871433742867488
#30 Error: 0.32975517036339136 RError: 25.882851765703528
#40 Error: 0.2958791590960926 RError: 28.89025778051556
#50 Error: 0.2815734161819014 RError: 32.03406406812814
#60 Error: 0.2725136525563304 RError: 36.01107202214404
#70 Error: 0.2664517094614679 RError: 37.37947475894951
#80 Error: 0.261009844620428 RError: 37.904675809351616
#90 Error: 0.2568214554963711 RError: 39.653479306958616
#100 Error: 0.25339768382321054 RError: 40.31748063496127
#110 Error: 0.24973562216412976 RError: 41.03648207296415
#120 Error: 0.24637186998417357 RError: 42.17148434296868
#130 Error: 0.24438652679017558 RError: 43.395686791373585
#140 Error: 0.24259441630025252 RError: 43.95808791617583
#150 Error: 0.24078976943362726 RError: 44.68508937017874
#160 Error: 0.2392529118613552 RError: 45.52249104498209
#170 Error: 0.23737761993693382 RError: 47.32289464578929
#180 Error: 0.23616634040705187 RError: 48.1486962973926
#190 Error: 0.23493530076907104 RError: 48.79549759099518
#200 Error: 0.23301703871238955 RError: 49.9248998497997
#210 Error: 0.23206282328650973 RError: 50.4509009018018
#220 Error: 0.2309738810428367 RError: 50.804901609803224
#230 Error: 0.22989122359286465 RError: 51.750903501807
#240 Error: 0.22901841993774705 RError: 52.066504133008266
#250 Error: 0.22799107486698814 RError: 52.679705359410725
#260 Error: 0.2271373808327415 RError: 53.28650657301315
#270 Error: 0.22577910232155193 RError: 53.91750783501566
#280 Error: 0.22462866996899516 RError: 54.600709201418404
#290 Error: 0.22372470877889966 RError: 55.13271026542053
#300 Error: 0.2229963423521016 RError: 55.59231118462237
#310 Error: 0.22254781605928597 RError: 55.51231102462205
#320 Error: 0.22194102423458337 RError: 55.84671169342339
#330 Error: 0.2212356506414129 RError: 56.12851225702451
#340 Error: 0.2203413378839014 RError: 56.54111308222617
#350 Error: 0.21951377876361122 RError: 57.19151438302876
#360 Error: 0.21871203837683365 RError: 57.46111492222984
#370 Error: 0.21785128296726275 RError: 58.01191602383204
#380 Error: 0.21670867055802667 RError: 58.786917573835154
#390 Error: 0.2162723519243771 RError: 58.818117636235264
#400 Error: 0.2158340255383315 RError: 58.916117832235656
#410 Error: 0.21540906380416747 RError: 58.99011798023596
#420 Error: 0.2150267540094603 RError: 59.255718511437024
#430 Error: 0.21460565124779177 RError: 59.45311890623781
#440 Error: 0.21418205428416048 RError: 59.68431936863874
#450 Error: 0.2137867183635126 RError: 59.91951983903968
#460 Error: 0.2134090699341581 RError: 60.03112006224013
#470 Error: 0.21291323590750694 RError: 60.181720363440725
#480 Error: 0.21264745671437782 RError: 60.36632073264146
#490 Error: 0.21233780152416482 RError: 60.5735211470423
#500 Error: 0.21193537962913414 RError: 60.91132182264365
#510 Error: 0.21175300767696115 RError: 60.748921497843
#520 Error: 0.21152982189981917 RError: 61.00772201544403
#530 Error: 0.21127247472177835 RError: 61.01752203504407
#540 Error: 0.21092025418788207 RError: 61.16372232744466
#550 Error: 0.2107096455152058 RError: 61.265722531445064
#560 Error: 0.21043374850922097 RError: 61.32232264464529
#570 Error: 0.21019812674401275 RError: 61.51112302224604
#580 Error: 0.20991656168419254 RError: 61.417322834645674
#590 Error: 0.20972092957186092 RError: 61.618323236646475
#600 Error: 0.20949530593059318 RError: 61.65992331984664
#610 Error: 0.20919526522464899 RError: 61.849123698247396
#620 Error: 0.20882572945947164 RError: 61.96712393424787
#630 Error: 0.20860039542297773 RError: 61.935523871047735
#640 Error: 0.20839722302626218 RError: 62.17772435544872
#650 Error: 0.2082259066970213 RError: 62.268324536649075
#660 Error: 0.2080360857385962 RError: 62.42012484024968
#670 Error: 0.20782583820853504 RError: 62.34712469424939
#680 Error: 0.20756963808437368 RError: 62.4277248554497
#690 Error: 0.20744067963042429 RError: 62.37392474784949
#700 Error: 0.20727697048673085 RError: 62.454924909849815
#710 Error: 0.207076428283692 RError: 62.61592523185047
#720 Error: 0.20693843873497367 RError: 62.690325380650755
#730 Error: 0.20680235447289996 RError: 62.610925221850444
#740 Error: 0.2065863056528295 RError: 62.73972547945096
#750 Error: 0.20642528075621852 RError: 62.791925583851175
#760 Error: 0.20626227967436253 RError: 62.832325664651336
#770 Error: 0.20613299375852168 RError: 62.95712591425183
#780 Error: 0.2059161032312466 RError: 62.86072572145144
#790 Error: 0.20583701045171984 RError: 62.959125918251836
#800 Error: 0.20564506342484998 RError: 63.10472620945242
#810 Error: 0.20557895170316773 RError: 63.13092626185253
#820 Error: 0.20547536512101838 RError: 63.29812659625319
#830 Error: 0.2053762425736301 RError: 63.050726101452206
#840 Error: 0.20527000344168758 RError: 63.2231264462529
#850 Error: 0.20518608389724824 RError: 63.23572647145295
#860 Error: 0.20508823370591428 RError: 63.35772671545343
#870 Error: 0.20495892155692724 RError: 63.38792677585355
#880 Error: 0.2048046398783639 RError: 63.387126774253545
#890 Error: 0.20473205026436955 RError: 63.47172694345389
#900 Error: 0.2046556908553021 RError: 63.51692703385407
#910 Error: 0.2045518899827459 RError: 63.55652711305423
#920 Error: 0.2044077379963823 RError: 63.67152734305469
#930 Error: 0.20433827989094955 RError: 63.54752709505419
#940 Error: 0.20420235482688634 RError: 63.67372734745469
#950 Error: 0.20411766780493285 RError: 63.694727389454776
#960 Error: 0.20396888436679156 RError: 63.72172744345489
#970 Error: 0.20394344147624285 RError: 63.68232736465473
#980 Error: 0.203915898877493 RError: 63.77032754065508
#990 Error: 0.2037744176698325 RError: 63.75612751225502
#1000 Error: 0.20366848744557048 RError: 63.844527689055376
#1010 Error: 0.2035416106798187 RError: 63.869727739455485
#1020 Error: 0.2034824756958244 RError: 63.83852767705535
#1030 Error: 0.20341303461498073 RError: 63.897927795855594
#1040 Error: 0.20334025672335593 RError: 64.04432808865617
#1050 Error: 0.20324149151240206 RError: 63.96152792305585
#1060 Error: 0.20315507538221564 RError: 63.96432792865586
#1070 Error: 0.2030807656629771 RError: 64.01952803905607
#1080 Error: 0.20300241261490737 RError: 64.18812837625674
#1090 Error: 0.20294595945885355 RError: 64.0477280954562
#1100 Error: 0.20289886525572517 RError: 64.13732827465654
#1110 Error: 0.20282219582852085 RError: 64.11372822745646
#1120 Error: 0.2027640638614403 RError: 64.08532817065634
#1130 Error: 0.20269107971023456 RError: 64.09112818225637
#1140 Error: 0.20264261001148104 RError: 64.16872833745667
#1150 Error: 0.20250616537240265 RError: 64.3009286018572
#1160 Error: 0.20246804443936697 RError: 64.23172846345693
#1170 Error: 0.202386963999037 RError: 64.31832863665727
#1180 Error: 0.20233800909936753 RError: 64.3497286994574
#1190 Error: 0.20227906544913615 RError: 64.39572879145759
#1200 Error: 0.20222259085298622 RError: 64.38892877785756
#1210 Error: 0.20215134934852713 RError: 64.4511289022578
#1220 Error: 0.2020917404839279 RError: 64.26592853185707
#1230 Error: 0.20204595648236987 RError: 64.46472892945786
#1240 Error: 0.20195506842134928 RError: 64.42512885025769
#1250 Error: 0.20189232464530452 RError: 64.38612877225755
#1260 Error: 0.2018519142842619 RError: 64.43092886185772
#1270 Error: 0.2017922930729479 RError: 64.47952895905792
#1280 Error: 0.20174714835762306 RError: 64.50932901865804
#1290 Error: 0.20169789522760828 RError: 64.57992915985832
#1300 Error: 0.20163748322346256 RError: 64.54532909065817
#1310 Error: 0.2015711639124559 RError: 64.61732923465847
#1320 Error: 0.20146064206921147 RError: 64.55372910745821
#1330 Error: 0.2014125396093533 RError: 64.5733291466583
#1340 Error: 0.20135638766188096 RError: 64.5751291502583
#1350 Error: 0.20128991857840475 RError: 64.68452936905874
#1360 Error: 0.20124569865635378 RError: 64.63512927025855
#1370 Error: 0.2012075425873893 RError: 64.61092922185844
#1380 Error: 0.20114613495223707 RError: 64.66552933105866
#1390 Error: 0.20104871738800284 RError: 64.67952935905872
#1400 Error: 0.20100265920514684 RError: 64.66432932865865
#1410 Error: 0.20095202445244212 RError: 64.66112932225865
#1420 Error: 0.2009043981809058 RError: 64.7999295998592
#1430 Error: 0.2008507818775519 RError: 64.69652939305878
#1440 Error: 0.20078830860838534 RError: 65.03613007226015
#1450 Error: 0.2007472951203024 RError: 64.7755295510591
#1460 Error: 0.20069419414964643 RError: 64.80692961385924
#1470 Error: 0.2006546159056995 RError: 64.7249294498589
#1480 Error: 0.20061918258016515 RError: 64.71092942185884
#1490 Error: 0.2005678491628941 RError: 64.6479292958586
#1500 Error: 0.20053049821175556 RError: 64.80172960345921
#1510 Error: 0.20049090942815617 RError: 64.8761297522595
#1520 Error: 0.20043418770468127 RError: 64.81372962745925
#1530 Error: 0.20040546194609127 RError: 64.81172962345924
#1540 Error: 0.20035509897033896 RError: 64.7983295966592
#1550 Error: 0.20031127698558412 RError: 64.87132974265948
#1560 Error: 0.20024202700359683 RError: 64.88092976185952
#1570 Error: 0.20020185670397184 RError: 64.85452970905942
#1580 Error: 0.20015340359953404 RError: 64.75512951025902
#1590 Error: 0.2001183023083837 RError: 64.8735297470595
#1600 Error: 0.20008006912334458 RError: 64.83712967425936
#1610 Error: 0.20004225719002952 RError: 64.9241298482597
#1620 Error: 0.19999784250289865 RError: 64.78852957705915
#1630 Error: 0.19994703799397906 RError: 64.9495298990598
#1640 Error: 0.1998906602721298 RError: 64.98092996185993
#1650 Error: 0.199839085883707 RError: 64.9517299034598
#1660 Error: 0.19980791936399434 RError: 64.98192996385993
#1670 Error: 0.19975743709322352 RError: 65.01153002306005
#1680 Error: 0.19968930420488487 RError: 65.07513015026029
#1690 Error: 0.1996401393050036 RError: 65.03693007386015
#1700 Error: 0.19957316522345342 RError: 65.04353008706018
#1710 Error: 0.1994918283902105 RError: 65.03613007226015
#1720 Error: 0.19943281175852767 RError: 65.05773011546023
#1730 Error: 0.19934780884412887 RError: 64.96512993025986
#1740 Error: 0.19930669108212049 RError: 65.0999301998604
#1750 Error: 0.19925078711524336 RError: 65.06673013346027
#1760 Error: 0.19919520670624943 RError: 65.15833031666062
#1770 Error: 0.19914796019186753 RError: 65.14853029706059
#1780 Error: 0.1991099323068616 RError: 65.08133016266032
#1790 Error: 0.19908045412682224 RError: 65.14713029426059
#1800 Error: 0.19902208260204024 RError: 65.25833051666103
#1810 Error: 0.19899112323990883 RError: 65.21473042946086
#1820 Error: 0.1989290683854394 RError: 65.28693057386114
#1830 Error: 0.19888443414950702 RError: 65.26653053306106
#1840 Error: 0.19884148854529127 RError: 65.24753049506099
#1850 Error: 0.1987881650614586 RError: 65.21113042226084
#1860 Error: 0.19873618368558332 RError: 65.26833053666108
#1870 Error: 0.19869469814730092 RError: 65.21353042706085
#1880 Error: 0.198673008800972 RError: 65.19673039346078
#1890 Error: 0.19859200763671364 RError: 65.35153070306141
#1900 Error: 0.19855201475750878 RError: 65.35493070986142
#1910 Error: 0.19850902561315137 RError: 65.1481302962606
#1920 Error: 0.19847702109782994 RError: 65.28753057506115
#1930 Error: 0.1984279701977625 RError: 65.29293058586117
#1940 Error: 0.19840031685117926 RError: 65.30893061786124
#1950 Error: 0.19836311621893543 RError: 65.32793065586131
#1960 Error: 0.19833068769015316 RError: 65.36673073346147
#1970 Error: 0.19827419382837824 RError: 65.18913037826076
#1980 Error: 0.19824683536303223 RError: 65.39393078786158
#1990 Error: 0.19820914036431114 RError: 65.40593081186162
#2000 Error: 0.1981595511874862 RError: 65.41713083426167
#2010 Error: 0.19813226112342572 RError: 65.37053074106149
#2020 Error: 0.19807514390157957 RError: 65.69633139266278
#2030 Error: 0.19804087567081063 RError: 65.58233116466234
#2040 Error: 0.19801201399017937 RError: 65.43233086466172
#2050 Error: 0.19798017673584242 RError: 65.47813095626192
#2060 Error: 0.1979361213498994 RError: 65.32653065306131
#2070 Error: 0.19787859558083915 RError: 65.44433088866177
#2080 Error: 0.19784629426943873 RError: 65.47913095826192
#2090 Error: 0.19779113677940474 RError: 65.4505309010618
#2100 Error: 0.1977644856294017 RError: 65.46613093226186
#2110 Error: 0.19772391913854803 RError: 65.49953099906199
#2120 Error: 0.19767876997226627 RError: 65.56173112346225
#2130 Error: 0.19763401465484512 RError: 65.51833103666208
#2140 Error: 0.19760133902956487 RError: 65.56293112586225
#2150 Error: 0.19757442823956325 RError: 65.57113114226229
#2160 Error: 0.19752230391439574 RError: 65.51773103546206
#2170 Error: 0.19749431288158142 RError: 65.54293108586218
#2180 Error: 0.19746779941529785 RError: 65.61613123226246
#2190 Error: 0.1974315798475514 RError: 65.60613121226243
#2200 Error: 0.19739447849129663 RError: 65.65553131106262
#2210 Error: 0.19736318400931674 RError: 65.65653131306263
#2220 Error: 0.1973341459942733 RError: 65.62133124266248
#2230 Error: 0.19730764660798053 RError: 65.57073114146228
#2240 Error: 0.19725817861964748 RError: 65.62233124466249
#2250 Error: 0.19721747349136642 RError: 65.64593129186258
#2260 Error: 0.19719223862453664 RError: 65.70573141146282
#2270 Error: 0.19715347578280581 RError: 65.61953123906248
#2280 Error: 0.1971242387708592 RError: 65.64133128266256
#2290 Error: 0.19709026749905603 RError: 65.72793145586292
#2300 Error: 0.19705004800838732 RError: 65.59113118226236
#2310 Error: 0.19702621750994667 RError: 65.7261314522629
#2320 Error: 0.19699155456107054 RError: 65.7239314478629
#2330 Error: 0.1969649115612764 RError: 65.80133160266321
#2340 Error: 0.1969346610433633 RError: 65.73013146026292
#2350 Error: 0.19690304559448313 RError: 65.7775315550631
#2360 Error: 0.19687964495296273 RError: 65.76113152226306
#2370 Error: 0.19684032070708882 RError: 65.89513179026358
#2380 Error: 0.19681942566472302 RError: 65.79453158906318
#2390 Error: 0.19677824938608976 RError: 65.76413152826306
#2400 Error: 0.19674918080527465 RError: 65.78813157626315
#2410 Error: 0.1967024704725042 RError: 65.85913171826344
#2420 Error: 0.1966675534511714 RError: 65.76913153826308
#2430 Error: 0.19662510517303938 RError: 65.78253156506312
#2440 Error: 0.19659760526389738 RError: 65.82433164866329
#2450 Error: 0.1965769526370713 RError: 65.8001316002632
#2460 Error: 0.19654192671022958 RError: 65.82073164146328
#2470 Error: 0.19650416552556726 RError: 65.83073166146333
#2480 Error: 0.1964735625957282 RError: 65.78533157066315
#2490 Error: 0.1964508353168558 RError: 65.80273160546322
#2500 Error: 0.19642122282885188 RError: 65.8483316966634
#2510 Error: 0.1963805156176655 RError: 65.71513143026286
#2520 Error: 0.19634259363340817 RError: 65.87033174066347
#2530 Error: 0.1963101046289919 RError: 65.91813183626367
#2540 Error: 0.1962900005776769 RError: 65.89473178946358
#2550 Error: 0.19626785428417298 RError: 65.92653185306371
#2560 Error: 0.19624505780326118 RError: 65.94393188786377
**/