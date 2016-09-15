package chart;

public class Chart {// extends ActionSupport {
	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5073180290164994208L;
//	private JFreeChart chart;
//
//	@SuppressWarnings("static-access")
//	public String execute() throws Exception {
//		System.out.println("Starting");
//        // chart creation logic...
//		Random r = new Random();
//        XYSeries dataSeries = new XYSeries(new Integer(1)); //pass a key for this serie
//        for (int i = 0; i <= 100; i++) {
//            dataSeries.add(i, r.nextInt());
//        }
//        XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);
// 
//        ValueAxis xAxis = new NumberAxis("Raw Marks");
//        ValueAxis yAxis = new NumberAxis("Moderated Marks");
//System.out.println("Got values"); 
//        // set my chart variable
//        chart =
//            new JFreeChart(
//                "Moderation Function",
//                JFreeChart.DEFAULT_TITLE_FONT,
//                new XYPlot(
//                    xyDataset,
//                    xAxis,
//                    yAxis,
//                    new StandardXYItemRenderer(StandardXYItemRenderer.LINES)),
//                false);
//        chart.setBackgroundPaint(java.awt.Color.white);
// 
//        return super.SUCCESS;
//    }
// 
//    public JFreeChart getChart() {
//        return chart;
//    }
}
