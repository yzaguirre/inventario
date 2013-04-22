package pruebas;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYPlotChart extends JFrame {

    public static void main(String[] args) {
        XYPlotChart demo = new XYPlotChart("Prueba graficar XY", "P-R curve");
        demo.pack();
        demo.setVisible(true);
    }
    private static final long serialVersionUID = 1L;

    public XYPlotChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset 
        XYDataset dataset = crearXYDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createXYChart(dataset, chartTitle);
     
        try {
            ChartUtilities.saveChartAsPNG(new File("test.png"), chart, 500, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static class Dot {

        double x;
        double y;
    }

    /**
     * Creates a sample dataset
     */
    private XYDataset crearXYDataset() {
        List<Dot> dots = new ArrayList<Dot>();
        double xdata1[] = {10, 20, 30, 40, 50};
        double ydata1[] = {20, 50, 40, 70, 80};
        for (int i = 0; i < xdata1.length; i++) {
            Dot d = new Dot();
            d.x = xdata1[i];
            d.y = ydata1[i];
            dots.add(d);
        }
        return (XYDataset) createData("component", dots);
    }

    private XYSeriesCollection createData(String componentId, List<Dot> dots) {
        XYSeriesCollection data = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(componentId);
        for (int i = 0; i < dots.size(); i++) {
            series1.add(dots.get(i).y, dots.get(i).x);
        }
        data.addSeries(series1);
        return data;
    }
    /*
     * Ver link:
     * http://code.google.com/p/project-hello-world/source/browse/trunk/src/main/java/com/gmail/hidekishima/jfreechart/XYPlotSample.java?r=4
     */

    private JFreeChart createXYChart(XYDataset data, String titulo) {
        JFreeChart chart =
                ChartFactory.createXYLineChart(titulo, "Recall", "Precision",
                data, PlotOrientation.VERTICAL,
                true, true, false);
        XYPlot plot = chart.getXYPlot();
//    StandardXYItemRenderer renderer = (StandardXYItemRenderer) plot.getRenderer();
        Font font = new Font("Meiryo", Font.PLAIN, 12);
        Font font2 = new Font("Meiryo", Font.PLAIN, 8);
        chart.getLegend().setItemFont(font);
        chart.getTitle().setFont(font);
        XYPlot xyp = chart.getXYPlot();
        xyp.getDomainAxis().setLabelFont(font); // X
        xyp.getRangeAxis().setLabelFont(font); // Y
        xyp.getDomainAxis().setRange(0, 100);
        xyp.getRangeAxis().setRange(0, 60);
        xyp.getDomainAxis().setTickLabelFont(font2);
        xyp.getRangeAxis().setTickLabelFont(font2);
        xyp.getDomainAxis().setVerticalTickLabels(true);
        xyp.getDomainAxis().setFixedAutoRange(100);
        xyp.getRangeAxis().setFixedAutoRange(100);

        // fill and outline
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setSeriesOutlinePaint(0, Color.RED);
        r.setSeriesOutlinePaint(1, Color.BLUE);
        r.setSeriesShapesFilled(0, false);
        r.setSeriesShapesFilled(1, false);

        return chart;
    }
}
