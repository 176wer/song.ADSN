package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


import org.jfree.chart.plot.PiePlot;

public class PacketProportion extends JPanel{
	
	public PacketProportion(){
		setLayout(new BorderLayout());
		add(createDemoPanel(),BorderLayout.CENTER);
	}

	class Rotator extends Timer implements ActionListener{

	private PiePlot plot;
	private int angle;

	public void actionPerformed(ActionEvent actionevent){
		plot.setStartAngle(angle);
		angle = angle + 1;
		if (angle == 360)
			angle = 0;
	}

	Rotator(PiePlot pieplot){
		super(50, null);
		angle = 270;
		plot = pieplot;
		addActionListener(this);
	}
}
	
	private  PieDataset createDataset()
	{
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		 
			defaultpiedataset.setValue("�㲥 ", 10);
			defaultpiedataset.setValue("IGMP" , 5);
			defaultpiedataset.setValue("ICMP" , 20);
			defaultpiedataset.setValue("data" , 20); 
		return defaultpiedataset;
	}
	public  JPanel createDemoPanel()
	{

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

		PieDataset piedataset = createDataset();
		JFreeChart jfreechart = ChartFactory.createPieChart("Packet �ֲ�����", piedataset, false, true, false);
		PiePlot pieplot = (PiePlot)jfreechart.getPlot();
		pieplot.setCircular(true);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		pieplot.setNoDataMessage("No data available");
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(500, 270));
		Rotator rotator = new Rotator(pieplot);
		rotator.start();
		return chartpanel;
	}
}
