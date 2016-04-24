package core;/**
 * Created by zgs on 2016/4/23.
 */

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
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 2016/4/23 20:27 <br/>
 *
 * @author Mr.zhao
 * @since JDK 1.8
 */
public class DrawCurve3 extends JPanel {
    public JFreeChart chart;
    public TimeSeries temp;
    private  String title;
    public DrawCurve3(String title) {
        super(new BorderLayout());
        this.title = title;
// create two series that automatically discard data more than 30


        this.temp = new TimeSeries(title, Millisecond.class);
        this.temp.setMaximumItemAge(30000);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.temp);

        DateAxis domain = new DateAxis("");
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
        chart = new JFreeChart("",
                new Font("SansSerif", Font.BOLD, 24), plot, true);
        chart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(Color.black)));
        add(chartPanel);
    }


    public   void add(double y) {
        try{
            this.temp.add(new Millisecond(), y);
        }catch(Exception e4){
            System.out.println(e4);
        }

    }

}
