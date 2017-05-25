//package miss;
//
//import javax.swing.JFrame;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PiePlot3D;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.general.PieDataset;
//import org.jfree.util.Rotation;
//
//
////wiecej informacji o bibliotece: http://www.vogella.com/tutorials/JFreeChart/article.html
//public class PieChart extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//
//    public PieChart(String applicationTitle, String chartTitle, CarStatistics carStatistics) {
//        super(applicationTitle);
//        PieDataset dataset = createDataset(carStatistics);
//        JFreeChart chart = createChart(dataset, chartTitle);
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        setContentPane(chartPanel);
//
//    }
//
//    private  PieDataset createDataset(CarStatistics carStatistics) {
//        DefaultPieDataset result = new DefaultPieDataset();
//        result.setValue("Bez zmiany predkosci", carStatistics.timeOfDrivingWithoutSpeedChange);
//        result.setValue("Przyspieszanie", carStatistics.timeOfSpeedingUp);
//        result.setValue("Zwalnianie", carStatistics.timeOfSlowingDown);
//        result.setValue("Zatrzymanie pojazdu", carStatistics.timeOfStopping);
//        return result;
//
//    }
//
//    private JFreeChart createChart(PieDataset dataset, String title) {
//
//        JFreeChart chart = ChartFactory.createPieChart3D(
//                title,
//                dataset,
//                true,
//                true,
//                false
//        );
//
//        PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//        return chart;
//
//    }
//}