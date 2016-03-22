/**
 * Project Name:ADSN
 * File Name:HoistryNode.java
 * Package Name:jfreechart
 * Date:2016年3月17日上午10:09:34
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package jfreechart;

import lib.Lib;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.sql.Connection;

/**
 * Function: 绘出历史节点数据
 * Date:     2016年3月17日 上午10:09:34 <br/>
 * @since    JDK 1.6
 * @see 	 
 */
public class HoistryNode extends JPanel  implements ChangeListener{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	private int viewC=200;//规定滚动显示数据个数
	private int SumCounts=0;//该节点下一共有多少数据


	private JSlider slider;
	private JFreeChart freeChart;
	private XYSeries xySeries1 ;
	private XYSeries xySeries2 ;
	private XYSeries xySeries3 ;
	private XYSeries xySeries4 ;
	private Connection conn;
	private String query;



	public HoistryNode(String query){
		super(new GridLayout());

		JSplitPane split=new JSplitPane();
		add(split ,BorderLayout.CENTER);
		JPanel rpanel =new  JPanel();
		rpanel.setLayout(new GridLayout(2,2));
		split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		split.setRightComponent(rpanel);


        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        conn = pool.getConnection();
        Lib lib = new Lib();
        SumCounts = lib.getNodeHistroyCount(query);

		slider = new JSlider(JSlider.VERTICAL, 0,SumCounts/viewC, 0);
		split.setLeftComponent(slider);
		slider.addChangeListener(this);


		//创建主题样式
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
		//设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		//设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));
		//设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));
		//应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		xySeries1 = new XYSeries("温度");
		xySeries2 = new XYSeries("湿度");
		xySeries3 = new XYSeries("振动");
		xySeries4 = new XYSeries("光强");

		XYSeriesCollection xyseriescollection1 = new XYSeriesCollection();
		xyseriescollection1.addSeries(xySeries1);
		XYSeriesCollection xyseriescollection2 = new XYSeriesCollection();
		xyseriescollection2.addSeries(xySeries2);
		XYSeriesCollection xyseriescollection3 = new XYSeriesCollection();
		xyseriescollection3.addSeries(xySeries3);
		XYSeriesCollection xyseriescollection4 = new XYSeriesCollection();
		xyseriescollection4.addSeries(xySeries4);

		JFreeChart freeChart1 = createChart("温度", xyseriescollection1, "time", "temprature");
		ChartPanel cp1 = new ChartPanel(freeChart1);
		cp1.setMouseWheelEnabled(true);
		rpanel.add(cp1);

		JFreeChart freeChart2 = createChart("湿度", xyseriescollection1, "time", "humdity");
		ChartPanel cp2 = new ChartPanel(freeChart2);
		cp2.setMouseWheelEnabled(true);
		rpanel.add(cp2);

		JFreeChart freeChart3 = createChart("光照", xyseriescollection1, "time", "light");
		ChartPanel cp3 = new ChartPanel(freeChart3);
		cp3.setMouseWheelEnabled(true);
		rpanel.add(cp3);

		JFreeChart freeChart4 = createChart("振动", xyseriescollection1, "time", "vibration");
		ChartPanel cp4 = new ChartPanel(freeChart4);
		cp4.setMouseWheelEnabled(true);
		rpanel.add(cp4);




	}



	private JFreeChart createChart(String title, XYDataset xyDataset, String x, String y) {
		JFreeChart jFreeChart = ChartFactory.createXYLineChart(title, x, y, xyDataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyPlot = (XYPlot) jFreeChart.getPlot();
		xyPlot.setDomainPannable(true);
		xyPlot.setRangePannable(true);
		XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
		xyLineAndShapeRenderer.setBaseLinesVisible(true);
		xyLineAndShapeRenderer.setBaseShapesFilled(true);
		NumberAxis numberAxis=(NumberAxis)xyPlot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jFreeChart;
	}


	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
            int a = source.getValue();
            xySeries1.clear();
            xySeries2.clear();
            xySeries3.clear();
            xySeries4.clear();





		}

	}

}

