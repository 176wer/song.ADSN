package gui;

/**
 * Created by Administrator on 2015/12/24.
 */
/**
 * Project Name:ADSN
 * File Name:ChartsTest.java
 * Package Name:gui
 * Date:2015年12月24日下午3:32:55
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

import java.awt.BorderLayout;
/**
 * ClassName:ChartsTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年12月24日 下午3:32:55 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.6
 * @see
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import control.NodeLife;
public class ItemLabelDemo1 extends JDialog {
	// 测量节点寿命用
	private HashMap<String, NodeLife> lifeMap = null;
	// 测量节点消耗能量，接收的能量设置为1，发送的能量设置为2
	private HashMap<String, Integer> energy = null;
	class LabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
		private double threshold;
		public LabelGenerator(double threshold) {
			super("", NumberFormat.getInstance());
			this.threshold = threshold;
		}

		public String generateLabel(CategoryDataset dataset, int series, int category) {
			String result = null;
			Number value = dataset.getValue(series, category);
			if (value != null) {
				double v = value.doubleValue();
				if (v > this.threshold) {
					result = value.toString(); // could apply formatting here
				}
			}
			return result;
		}
	}

	/**
	 * Creates a new demo instance.
	 * @param title  the frame title.
	 */
	public ItemLabelDemo1(String title, HashMap<String, NodeLife> lifeMap, HashMap<String, Integer> energy) {
		this.lifeMap = lifeMap;
		this.energy = energy;
		CategoryDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "节点生存时间");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		CategoryDataset dataset1 = createDataset1();
		JFreeChart chart1 = createChart(dataset1, "节点消耗能量");
		ChartPanel chartPanel1 = new ChartPanel(chart1);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setLayout(new GridLayout(2, 0));
		add(chartPanel);
		add(chartPanel1);
	}

	/**
	 * Returns a 节点寿命 dataset.
	 *
	 * @return The dataset.
	 */
	private CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		Set<String> keyLife = lifeMap.keySet();
		for (String keyl : keyLife) {
			NodeLife node = lifeMap.get(keyl);
			dataset.addValue(node.getLife() / 1000000000, "S1", keyl);
		}
		return dataset;
	}
	private CategoryDataset createDataset1() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Set<String> keyEnergy = energy.keySet();
		for (String key2 : keyEnergy) {
			if (!key2.equals("0000"))
				dataset.addValue(energy.get(key2), "S3", key2);
		}

		return dataset;
	}

	/**
	 * Creates a sample chart.
	 * @param datasetthe dataset.
	 * @return the chart.
	 */
	@SuppressWarnings("deprecation")
	private JFreeChart createChart(CategoryDataset dataset, String title) {
		// create the chart...
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart chart = ChartFactory.createBarChart(title, // chart title
				"节点地址", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips?
				false // URLs?
		);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.15);
		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setItemLabelGenerator(new LabelGenerator(50.0));
		renderer.setItemLabelFont(new Font("Serif", Font.PLAIN, 20));
		renderer.setItemLabelsVisible(true);
		return chart;
	}

}
