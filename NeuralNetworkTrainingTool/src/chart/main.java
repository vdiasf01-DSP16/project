package chart;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class main {

	public static int x = 0;
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setTitle("Testing Online Graphs");
		window.setSize(600, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		XYSeries series = new XYSeries("Testing Graphs");
		XYSeriesCollection dataSet = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Graphs Test", "Seconds", "Value", dataSet);
		window.add(new ChartPanel(chart), BorderLayout.CENTER);
		
		Thread t = new Thread(){
			@Override public void run() {
				while(true) {
					series.add(x++, Math.random());
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		window.setVisible(true);
	}
}
//    public main( final String title ) {
//        super( title );         
//        final XYDataset dataset = createDataset( );         
//        final JFreeChart chart = createChart( dataset );         
//        final ChartPanel chartPanel = new ChartPanel( chart );         
//        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );         
//        chartPanel.setMouseZoomable( true , false );         
//        setContentPane( chartPanel );
//    }
//
//    private XYDataset createDataset( ) {
//        final TimeSeries series = new TimeSeries( "Random Data" );         
//        Second current = new Second( );         
//        double value = 100.0;         
//          for (int i = 0; i < 4000; i++)    
//          {
//             try 
//             {
//                value = value + Math.random( ) - 0.5;                 
//                series.add(current, new Double( value ) );                 
//                current = ( Second ) current.next( ); 
//             }
//             catch ( SeriesException e ) 
//             {
//                System.err.println("Error adding to series");
//             }
//          }
//
//          return new TimeSeriesCollection(series);
//       }     
//
//    private JFreeChart createChart( final XYDataset dataset ) {
//        return ChartFactory.createTimeSeriesChart(             
//            "Computing Test", 
//            "Seconds",              
//            "Value",              
//            dataset,             
//            false,              
//            false,              
//            false);
//    }
//
//    public static void main( final String[ ] args ) {
//        final String title = "Time Series Management";         
//        final main demo = new main( title );         
//        demo.pack( );         
//        RefineryUtilities.positionFrameRandomly( demo );         
//        demo.setVisible( true );
//        
//    }
//}   
//
//    public main( String applicationTitle , String chartTitle ) {
//      super(applicationTitle);
//      JFreeChart lineChart = ChartFactory.createLineChart(
//         chartTitle,
//         "Years","Number of Schools",
//         createDataset(),
//         PlotOrientation.VERTICAL,
//         true,true,false);
//         
//      ChartPanel chartPanel = new ChartPanel( lineChart );
//      chartPanel.setPreferredSize( new Dimension( 560 , 367 ) );
//      setContentPane( chartPanel );
//   }
//
//   private DefaultCategoryDataset createDataset( ) {
//      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
//      dataset.addValue( 15 , "schools" , "1970" );
//      dataset.addValue( 30 , "schools" , "1980" );
//      dataset.addValue( 60 , "schools" ,  "1990" );
//      dataset.addValue( 120 , "schools" , "2000" );
//      dataset.addValue( 240 , "schools" , "2010" );
//      dataset.addValue( 300 , "schools" , "2014" );
//      return dataset;
//   }
//
//   public static void main( String[] args ) {
//      main chart = new main( "School Vs Years", "Numer of Schools vs years");
//      chart.pack( );
//      RefineryUtilities.centerFrameOnScreen( chart );
//      chart.setVisible( true );
//   }
//}
