package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

import javax.swing.JDialog;

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

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

@SuppressWarnings("serial")
public class HoistryEnergy extends JDialog {
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();;
	private DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();;

	/**
	 * Launch the application.
	 */
	 
	@SuppressWarnings("serial")
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
	 * 
	 * @param title
	 *            the frame title.
	 */
	public HoistryEnergy() {
		createDataset();
		this.setSize(800, 700);
		JFreeChart chart = createChart(dataset, "节点生存时间");
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));

		JFreeChart chart1 = createChart(dataset1, "节点消耗能量");
		ChartPanel chartPanel1 = new ChartPanel(chart1);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		getContentPane().setLayout(new GridLayout(2, 0));
		getContentPane().add(chartPanel);
		getContentPane().add(chartPanel1);
	}

	/**
	 * Returns a 节点寿命 dataset.
	 *
	 * @return The dataset.
	 */
	private void createDataset() {

		IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		Connection conn = pool.getConnection();

		String sql = "select * from sumle";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				String life = rs.getString(3);
				long llife=Long.valueOf(life)/1000000000;
				dataset.addValue(llife, "S1", rs.getString(1));
				dataset1.addValue(rs.getInt(4),"S2", rs.getString(1));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param datasetthe
	 *            dataset.
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
