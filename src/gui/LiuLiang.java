package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class LiuLiang extends JDialog   {
	private final JPanel contentPanel = new JPanel();
	private TimeSeries timeseries=null;
   
	/**
	 * Create the dialog.
	 */
	public LiuLiang() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
	    this.setVisible(false);
	    
		// ����������ʽ
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// ���ñ�������
		standardChartTheme.setExtraLargeFont(new Font("����", Font.BOLD, 20));
		// ����ͼ��������
		standardChartTheme.setRegularFont(new Font("����", Font.PLAIN, 15));
		// �������������
		standardChartTheme.setLargeFont(new Font("����", Font.PLAIN, 15));
		// Ӧ��������ʽ
		ChartFactory.setChartTheme(standardChartTheme);
		TimeSeriesCollection timeseriescollection=new TimeSeriesCollection();
		timeseries=new TimeSeries("");
		timeseries.setMaximumItemAge(3000);
		timeseriescollection.addSeries(timeseries);
		
		JFreeChart jfreechart=ChartFactory.createTimeSeriesChart("����", "Time", "", timeseriescollection,true,true,false);
		XYPlot xyplot=(XYPlot)jfreechart.getPlot();
//		   ValueAxis valueaxis = xyplot.getRangeAxis();
//		   valueaxis.setRange(0,200);
		XYAreaRenderer area=new XYAreaRenderer();
		xyplot.setRenderer(0,area);
	 
		ChartPanel chartpanel=new ChartPanel(jfreechart);
		chartpanel.setBackground(new Color(1,0,0));
		chartpanel.setMouseWheelEnabled(true);
		//chartpanel.setMouseWheelEnabled(true);
		contentPanel.add(chartpanel,BorderLayout.CENTER);
	}
	
	public void addData(int data){
		timeseries.add(new Millisecond(),data);
		System.out.println("dddddddd");
	}
	
	
	 

}
