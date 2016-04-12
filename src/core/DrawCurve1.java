package core;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zgs on 2016/3/23.
 */
public class DrawCurve1 extends JPanel {
    JFreeChart chart;
    /**
     * Time series for total memory used.
     */
    public TimeSeries temp;
    /**
     * Time series for free memory.
     */
    public TimeSeries hum;
    public TimeSeries vibration;
    public TimeSeries light;


    public DrawCurve1() {
        super(new BorderLayout());
// create two series that automatically discard data more than 30


        this.temp = new TimeSeries("温度", Millisecond.class);
        this.temp.setMaximumItemAge(30000);
        this.hum = new TimeSeries("湿度", Millisecond.class);
        this.hum.setMaximumItemAge(30000);
        this.vibration=new TimeSeries("振动");
        this.vibration.setMaximumItemAge(30000);
        this.light = new TimeSeries("光强");
        this.light.setMaximumItemAge(30000);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.temp);
        dataset.addSeries(this.hum);
        dataset.addSeries(this.vibration);
        dataset.addSeries(this.light);

        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("参数");
//        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//        domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
//        range.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.green);

        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        //plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        domain.setAutoRange(true);
        domain.setLowerMargin(0.0);
        domain.setUpperMargin(0.0);
        domain.setTickLabelsVisible(true);
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart = new JFreeChart("参数实时变化",
                new Font("SansSerif", Font.BOLD, 24), plot, true);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(Color.black)));
        add(chartPanel);
    }

    public JFreeChart getChart(){
        return  chart;
    }

    public void add(int v1, int v2, int v3, int v4) {
        temp.add(new Millisecond(), v1);
        hum.add(new Millisecond(), v2);
        vibration.add(new Millisecond(), v3);
        light.add(new Millisecond(), v4);
    }
    public   void addTempObservation(double y) {
        try{
            this.temp.add(new Millisecond(), y);
        }catch(Exception e4){
            System.out.println(e4);
        }

    }

    public  void addHumObservation(double y) {
        this.hum.add(new Millisecond(), y);
    }


}
