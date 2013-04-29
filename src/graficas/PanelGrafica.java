package graficas;

import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.SerialDate;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author david
 */
public class PanelGrafica {
    /**
     * Q'
     **/
    private double Q, minimo;
    private double inventarioMedio;
    /**
     * Titulo de la grafica
     **/
    private String titulo;
    /**
     * Titulo del eje Y
     **/
    private String tituloY;
    /**
     * Fecha de hoy
     **/
    private Day fechaHoy;
    /**
     * @param Q La Q' 
     * @param titulo Titulo de la grafica
     * @param tituloY Titulo del eje Y
     *
     **/
    public PanelGrafica(double Q, double minimo, String titulo, String tituloY) {
        this.Q = Q;
        this.minimo = minimo;
        this.inventarioMedio = (Q + minimo)/2;
        this.titulo = titulo;
        this.tituloY = tituloY;
        fechaHoy = new Day();
    }
    /**
     * @return Panel con la grafica 
     **/
    public JPanel getPanel(){
        XYDataset xydataset = createDatasetInventario("Demanda", Q, minimo, 
                fechaHoy);
        JFreeChart jfreechart = createChart(titulo, xydataset);
        
        /*SerialDate serialdate = fechaHoy.getSerialDate();
        SerialDate serialdate2 = SerialDate.addDays(-1, serialdate);
        SerialDate serialdate3 = SerialDate.addDays(5, serialdate);
        Day day2 = new Day(serialdate2);
        Day day3 = new Day(serialdate3);
        XYPlot xyplot2 = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis2 = (DateAxis) xyplot2.getDomainAxis();
        dateaxis2.setRange(day2.getStart(), day3.getEnd());*/
        
        ChartPanel chartpanel3 = new ChartPanel(jfreechart);
        return chartpanel3;
    }
    private JFreeChart createChart(String s, XYDataset xydataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(s, "Fecha", 
                tituloY, xydataset, true, true, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.setOrientation(PlotOrientation.VERTICAL);
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setRangeCrosshairValue(2D);
        XYItemRenderer r = xyplot.getRenderer();
        r.setSeriesPaint(0, Color.blue);
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }
        return chart;
    }

    /**
     * @param s Titulo de la serie de datos
     * @param maximo punto maximo de inventario, suele ser Q'
     * @param minimo punto minimo de la grafica, suele ser 0
     * @param rtp_fechaHoy fecha de hoy o cuando comienza el tiempo
     **/
    private XYDataset createDatasetInventario(String s, double maximo, 
            double minimo, RegularTimePeriod rtp_fechaHoy) {
        TimePeriodValues tpv = new TimePeriodValues(s);
        RegularTimePeriod rtp_variable = rtp_fechaHoy;
        
        tpv.add(rtp_variable, maximo);
        rtp_variable = rtp_variable.next();
        
        tpv.add(rtp_variable, minimo);
        tpv.add(rtp_variable, maximo);
        rtp_variable = rtp_variable.next();
        
        tpv.add(rtp_variable, minimo);
        tpv.add(rtp_variable, maximo);
        rtp_variable = rtp_variable.next();
        
        tpv.add(rtp_variable, minimo);
        tpv.add(rtp_variable, maximo);
        rtp_variable = rtp_variable.next();
        
        tpv.add(rtp_variable, minimo);
        
        TimePeriodValues tpv2 = new TimePeriodValues("Inventario Promedio");
        tpv2.add(rtp_fechaHoy, inventarioMedio);
        tpv2.add(rtp_variable, inventarioMedio);
        
        TimePeriodValuesCollection tpvc = new TimePeriodValuesCollection();
        tpvc.addSeries(tpv);
        tpvc.addSeries(tpv2);
        return tpvc;
    }
}
