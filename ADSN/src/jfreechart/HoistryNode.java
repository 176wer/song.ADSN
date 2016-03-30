/**
 * Project Name:ADSN
 * File Name:HoistryNode.java
 * Package Name:jfreechart
 * Date:2016��3��17������10:09:34
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package jfreechart;

import core.DrawData;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Function: �����ʷ�ڵ����� Date: 2016��3��17�� ����10:09:34 <br/>
 *
 * @see
 * @since JDK 1.6
 */
public class HoistryNode extends JPanel implements ChangeListener {
	/**
	 * serialVersionUID:TODO(��һ�仰�������������ʾʲô).
	 */
	private static final long serialVersionUID = 1L;
	private int viewC = 200;// �涨������ʾ���ݸ���
	private int SumCounts = 0;// �ýڵ���һ���ж�������
	private ArrayList<String> funNode = new ArrayList<String>();// ��Ź������Ľڵ�

	private JSlider slider;
	private JFreeChart freeChart;
	private XYSeries xySeries1;
	private XYSeries xySeries2;
	private XYSeries xySeries3;
	private XYSeries xySeries4;
	private Connection conn;
	private String query;
	private String startTime;// ��ѯ�ڵ����ݵ���ʼʱ��
	private String endTime;// ��ѯ�ڵ�Ľ���ʱ��
	private String pickFunc;
	private DrawData draw;



	public HoistryNode() {
		super(new GridLayout());

		IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		conn = pool.getConnection();

		JSplitPane split = new JSplitPane();
		add(split, BorderLayout.CENTER);
		JPanel rpanel = new JPanel();
		rpanel.setLayout(new GridLayout(4, 1));
		split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		split.setRightComponent(rpanel);


		slider = new JSlider(JSlider.VERTICAL, 0, SumCounts / viewC, 0);
		split.setLeftComponent(slider);
		slider.addChangeListener(this);

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

		xySeries1 = new XYSeries("�¶�");

		xySeries2 = new XYSeries("ʪ��");
		xySeries3 = new XYSeries("��");
		xySeries4 = new XYSeries("��ǿ");

		XYSeriesCollection xyseriescollection1 = new XYSeriesCollection();
		xyseriescollection1.addSeries(xySeries1);
		xyseriescollection1.setAutoWidth(true);
		XYSeriesCollection xyseriescollection2 = new XYSeriesCollection();
		xyseriescollection2.addSeries(xySeries2);
		XYSeriesCollection xyseriescollection3 = new XYSeriesCollection();
		xyseriescollection3.addSeries(xySeries3);
		XYSeriesCollection xyseriescollection4 = new XYSeriesCollection();
		xyseriescollection4.addSeries(xySeries4);

		JFreeChart freeChart1 = createChart("�¶�", xyseriescollection1, "time", "temprature");
		ChartPanel cp1 = new ChartPanel(freeChart1);
		cp1.setMouseWheelEnabled(true);
		rpanel.add(cp1);
//		draw=new DrawData();
//		rpanel.add(draw);

		JFreeChart freeChart2 = createChart("ʪ��", xyseriescollection2, "time", "humdity");
		ChartPanel cp2 = new ChartPanel(freeChart2);
		cp2.setMouseWheelEnabled(true);
		rpanel.add(cp2);

		JFreeChart freeChart3 = createChart("����", xyseriescollection3, "time", "light");
		ChartPanel cp3 = new ChartPanel(freeChart3);
		cp3.setMouseWheelEnabled(true);
		rpanel.add(cp3);

		JFreeChart freeChart4 = createChart("��", xyseriescollection4, "time", "vibration");
		ChartPanel cp4 = new ChartPanel(freeChart4);
		cp4.setMouseWheelEnabled(true);
		rpanel.add(cp4);

	}

	private JFreeChart createChart(String title, XYDataset xyDataset, String x, String y) {
		JFreeChart jFreeChart = ChartFactory.createXYLineChart(title, x, y, xyDataset, PlotOrientation.VERTICAL, true,
				true, false);
		XYPlot xyPlot = (XYPlot) jFreeChart.getPlot();
		xyPlot.setDomainPannable(true);
		xyPlot.setRangePannable(true);
		XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
		xyLineAndShapeRenderer.setBaseLinesVisible(true);
		xyLineAndShapeRenderer.setBaseShapesFilled(true);
		NumberAxis numberAxis = (NumberAxis) xyPlot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jFreeChart;
	}

	public void stateChanged(ChangeEvent e) {

		Lib lib = new Lib();
		SumCounts = lib.getNodeHistroyCount(query);
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int a = source.getValue();
			xySeries1.clear();
			xySeries2.clear();
			xySeries3.clear();
			xySeries4.clear();

		}

	}

	public void setQueryValue(String startTime, String endTime, String pickFunc) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.pickFunc = pickFunc;
		startDraw();
	}

	public void startDraw() {
		IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		Connection conn = pool.getConnection();
		Statement stm1 = null;
		ResultSet rs1 = null;
		Statement stm2 = null;
		ResultSet rs2;
		String sql = "select sensor_id from funcation where node_funcation='" + pickFunc + "'";//���ݹ������г����нڵ�


		try {
			stm1 = conn.createStatement();
			rs1 = stm1.executeQuery(sql);
			while (rs1.next()) {
				funNode.add(rs1.getString(1));
				System.out.println(rs1.getString(1));
			}

			String addr = funNode.get(0);
			System.out.println(addr);
			String sql2 = "select  temperature , humdity , vibration ,light from  ntime where addr='"+addr+"' and ndate>'"+startTime+"' and ndate<'"+endTime+"'" ;
			System.out.println(sql2);
			stm2 = conn.createStatement();

			rs2 = stm2.executeQuery(sql2);
			int i=0;
			while(rs2.next()){
				double v1 = rs2.getInt(1);
				double v2 = i;
				xySeries1.add(v2,v1);

				v1 = rs2.getInt(2);
				xySeries2.add(v2, v1);
				v1 = rs2.getInt(3);
				xySeries3.add(v2, v1);
				v1 = rs2.getInt(4);
				xySeries4.add(v2, v1);
				i=i+1;
			}

			rs1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
