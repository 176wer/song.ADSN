/**
 * Project Name:ADSN
 * File Name:NodeHistroy.java
 * Package Name:gui
 * Date:2016年1月3日下午9:13:29
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.RelativeDateFormat;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.ShapeUtilities;

import bean.NodeTime;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * ClassName:NodeHistroy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月3日 下午9:13:29 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * ClassName: NodeHistroy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年1月5日 上午10:09:24 <br/>
 *
 * @author Administrator
 * @version 
 * @since JDK 1.6
 */
/**
 * ClassName: NodeHistroy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年1月5日 上午10:09:31 <br/>
 *
 * @author Administrator
 * @version 
 * @since JDK 1.6
 */
public class NodeHistroy  {

	public JFrame frame;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	
	private String number;//实验的标识号
	private String addr;//节点地址

	/**
	 * Launch the application.
	 */
	 

	/**
	 * Create the application.
	 */
	public NodeHistroy(String number,String addr) {
	    this.number=number;
	    this.addr=addr;
		initialize();
	}

	 
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(new GridLayout(3, 3));
		frame.getContentPane().setLayout(new BorderLayout());
		
		panel =createTempChart();
		panel.setBackground(Color.MAGENTA);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		
//		panel_2 =createVoltageChart();
//		panel_2.setBackground(Color.CYAN);
//		frame.getContentPane().add(panel_2);
//		
//		panel_1 = createHumidityChart();
//		panel_1.setBackground(Color.YELLOW);
//		frame.getContentPane().add(panel_1);
//		
//		panel_3 =createvibrationChart();
//		panel_3.setBackground(Color.PINK);
//		frame.getContentPane().add(panel_3);
//		
//		panel_4 =createlightChart();
//		panel_4.setBackground(Color.GRAY);
//		frame.getContentPane().add(panel_4);
		 
	}
	/**  
	* @Description:创建一个节点温度随历史事件变化的图表 
	* @return JFreeChart    
	* @throws  
	*/
	private  JPanel createTempChart( ) {
		 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
	     Connection conn=pool.getConnection();
	     String sql="select temperature,time from temperature";
	     Statement stm = null;
	     ResultSet rs = null;
	 	//ceate a dataset
			TimeSeries timeseries = new TimeSeries("Heart Rate", Second.class);
			NodeTime nodetime=new NodeTime();
		try {
			stm=conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){ 
			nodetime.EdsTime(rs.getString(2)); 
			 
			timeseries.addOrUpdate(new Second(nodetime.getSecond(),nodetime.getMinute(),nodetime.getHours(),nodetime.getDay(),nodetime.getMouth(),nodetime.getYear()),Integer.valueOf(rs.getString(1)));
		 
			}
		} catch (SQLException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stm.close();
				pool.releaseConn(conn);
			} catch (SQLException e) {
				
			}
		}
	
	 
			
		 
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		 
		//創建一個圖表
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Temperature", "Elapsed Time", "`C",
				timeseriescollection, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		ValueAxis rangeAxis = xyplot.getRangeAxis();  
	        
	   rangeAxis.setRange(0.0D,30D);//设置Y轴的取值范围double		
		 
		
		 
		return new ChartPanel(jfreechart);
	}

	
	/**  
	* @Description:创建一个节点电压随历史事件变化的图表 
	* @return JFreeChart    
	* @throws  
	*/
	 
	 private  JPanel createVoltageChart() {
		 
		 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
	     Connection conn=pool.getConnection();
	     String sql="select voltage,time from voltage";
	     Statement stm = null;
	     ResultSet rs = null;
	 	//ceate a dataset
			TimeSeries timeseries = new TimeSeries("voltage", Second.class);
			NodeTime nodetime=new NodeTime();
		try {
			stm=conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){ 
			nodetime.EdsTime(rs.getString(2)); 
			timeseries.addOrUpdate(new Second(nodetime.getSecond(),nodetime.getMinute(),nodetime.getHours(),nodetime.getDay(),nodetime.getMouth(),nodetime.getYear()),Double.valueOf(rs.getString(1)));
		 
			}
		} catch (SQLException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stm.close();
				pool.releaseConn(conn);
			} catch (SQLException e) {
				
			}
		}
	
	 
			
		 
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		 
		//創建一個圖表
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Voltage", "Elapsed Time", "V",
				timeseriescollection, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		ValueAxis rangeAxis = xyplot.getRangeAxis();  
	        
	   rangeAxis.setRange(0.0D,8D);//设置Y轴的取值范围double		
		 
		
		 
		return new ChartPanel(jfreechart);
	    }
       
 /**  
* @Title: createHumidityChart  
* @Description: 画出节点湿度曲线图  
* @return JPanel    返回类型  
* @throws  
*/
private  JPanel createHumidityChart() {
		 
		 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
	     Connection conn=pool.getConnection();
	     String sql="select humidity,time from humidity";
	     Statement stm = null;
	     ResultSet rs = null;
	 	//ceate a dataset
			TimeSeries timeseries = new TimeSeries("humidity", Second.class);
			NodeTime nodetime=new NodeTime();
		try {
			stm=conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){ 
			nodetime.EdsTime(rs.getString(2)); 
			timeseries.addOrUpdate(new Second(nodetime.getSecond(),nodetime.getMinute(),nodetime.getHours(),nodetime.getDay(),nodetime.getMouth(),nodetime.getYear()),Integer.valueOf(rs.getString(1)));
		 
			}
		} catch (SQLException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stm.close();
				pool.releaseConn(conn);
			} catch (SQLException e) {
				
			}
		}
	
	 
			
		 
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		 
		//創建一個圖表
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Humidity", "Elapsed Time", "%",
				timeseriescollection, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		ValueAxis rangeAxis = xyplot.getRangeAxis();  
	        
	   rangeAxis.setRange(0.0D,8D);//设置Y轴的取值范围double		
		 
		
		 
		return new ChartPanel(jfreechart);
	    }

/**  
* @Title: createvibrationChart  
* @Description: 画出节点振动曲线图  
* @return JPanel    返回类型  
* @throws  
*/
private  JPanel createvibrationChart() {
		 
		 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
	     Connection conn=pool.getConnection();
	     String sql="select vibration,time from vibration";
	     Statement stm = null;
	     ResultSet rs = null;
	 	//ceate a dataset
			TimeSeries timeseries = new TimeSeries("vibration", Second.class);
			NodeTime nodetime=new NodeTime();
		try {
			stm=conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){ 
			nodetime.EdsTime(rs.getString(2)); 
			try{
				timeseries.addOrUpdate(new Second(nodetime.getSecond(),nodetime.getMinute(),nodetime.getHours(),nodetime.getDay(),nodetime.getMouth(),nodetime.getYear()),Integer.valueOf(rs.getString(1)));
				 
			}catch(Exception e){
				e.printStackTrace();
			}
		
			}
		} catch (SQLException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stm.close();
				pool.releaseConn(conn);
			} catch (SQLException e) {
				
			}
			
		}
	
	 
			
		 
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		 
		//創建一個圖表
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Vibration", "Elapsed Time", null,
				timeseriescollection, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		ValueAxis rangeAxis = xyplot.getRangeAxis();  
	        
	   rangeAxis.setRange(0.0D,8D);//设置Y轴的取值范围double		
		 
		
		 
		return new ChartPanel(jfreechart);
	    }
	

/**  
* @Title: createlightChart  
* @Description: 画出节点光照曲线图  
* @return JPanel    返回类型  
* @throws  
*/
private  JPanel createlightChart() {
	 
	 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
    Connection conn=pool.getConnection();
    String sql="select vibration,time from vibration";
    Statement stm = null;
    ResultSet rs = null;
	//ceate a dataset
		TimeSeries timeseries = new TimeSeries("vibration", Second.class);
		NodeTime nodetime=new NodeTime();
	try {
		stm=conn.createStatement();
		rs = stm.executeQuery(sql);
		while(rs.next()){ 
		nodetime.EdsTime(rs.getString(2)); 
		timeseries.addOrUpdate(new Second(nodetime.getSecond(),nodetime.getMinute(),nodetime.getHours(),nodetime.getDay(),nodetime.getMouth(),nodetime.getYear()),Integer.valueOf(rs.getString(1)));
	 
		}
	} catch (SQLException e1) {
		
		// TODO Auto-generated catch block
		e1.printStackTrace();
		
	}finally{
		try {
			rs.close();
			stm.close();
			pool.releaseConn(conn);
		} catch (SQLException e) {
			
		}
		
		}

		
	 
	TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
	timeseriescollection.addSeries(timeseries);
	 
	//創建一個圖表
	JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Vibration", "Elapsed Time", null,
			timeseriescollection, true, true, false);
	XYPlot xyplot = (XYPlot) jfreechart.getPlot();
	ValueAxis rangeAxis = xyplot.getRangeAxis();  
       
  rangeAxis.setRange(0.0D,8D);//设置Y轴的取值范围double		
	 
	
	 
	return new ChartPanel(jfreechart);
   }


}

