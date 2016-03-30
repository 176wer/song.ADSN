package core;

/**
 * Created by Administrator on 2016/3/11.
 */
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;


// Referenced classes of package demo:
//			DemoPanel

public class DrawCurve extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4741135592891516363L;


	JFreeChart jfreechart;


    private TimeSeries series1;
    private TimeSeries series2;
    private TimeSeries series3;
    private TimeSeries series4;

    public DrawCurve() {
        setLayout(new BorderLayout());
        series1 = new TimeSeries("Temperature",Millisecond.class);
        series2 = new TimeSeries("Humdity",Millisecond.class);
        series3 = new TimeSeries("Light",Millisecond.class);
        series4 = new TimeSeries("vibration",Millisecond.class);
        TimeSeriesCollection timeseriescollection1 = new TimeSeriesCollection(series1);
        TimeSeriesCollection timeseriescollection2 = new TimeSeriesCollection(series2);
        TimeSeriesCollection timeseriescollection3 = new TimeSeriesCollection(series3);
        TimeSeriesCollection timeSeriesCollection4 = new TimeSeriesCollection(series4);


        jfreechart = ChartFactory.createTimeSeriesChart("实时参数变化", "Time", "Temperature", timeseriescollection1, true, true, false);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        ValueAxis valueaxis = xyplot.getRangeAxis();
        valueaxis.setRange(0, 40);


        xyplot.setDataset(2, timeseriescollection2);
        xyplot.setDataset(3, timeseriescollection3);
        xyplot.setDataset(4, timeSeriesCollection4);

        NumberAxis numberaxis1 = new NumberAxis("Light");
        numberaxis1.setAutoRangeIncludesZero(false);
        xyplot.setRenderer(1, new DefaultXYItemRenderer());
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.mapDatasetToRangeAxis(1, 1);
        numberaxis1.setRange(0,4);

        NumberAxis numberaxis2 = new NumberAxis("Humdity");
        numberaxis2.setAutoRangeIncludesZero(false);
        xyplot.setRenderer(2, new DefaultXYItemRenderer());
        xyplot.setRangeAxis(2, numberaxis2);
        xyplot.mapDatasetToRangeAxis(2, 2);
        numberaxis2.setRange(0.0D,40D);


        NumberAxis numberaxis3 = new NumberAxis("vibration");
        numberaxis3.setAutoRangeIncludesZero(false);
        xyplot.setRenderer(3, new DefaultXYItemRenderer());
        xyplot.setRangeAxis(3, numberaxis3);
        xyplot.mapDatasetToRangeAxis(3, 3);
        numberaxis3.setRange(0,40);


        ChartUtilities.applyCurrentTheme(jfreechart);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        add(chartpanel, BorderLayout.CENTER);
    }

    public void add(int v1, int v2, int v3, int v4) {
        series1.add(new Millisecond(), v1);
        series2.add(new Millisecond(), v2);
        series3.add(new Millisecond(), v3);
        series4.add(new Millisecond(), v4);
    }

    public void addTemp(int v1) {
        series1.add(new Millisecond(), v1);
    }

    public void addHumdity(int v2) {
        series2.add(new Millisecond(), v2);
    }

    public void addLight(int v3) {
        series3.add(new Millisecond(), v3);
    }

    public void addVibration(int v4) {
        series4.add(new Millisecond(), v4);
    }

    public JFreeChart getJfreechart() {
        return jfreechart;
    }

}
