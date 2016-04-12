package charts;

/**
 * Created by Administrator on 2015/12/26.
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.DataRecorder;
import core.MysqlServer;
import core.ServerStatusListener;
import core.ServerVariablesListener;
import gui.MainGUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import bean.ChartBean;
import bean.FormulaParser;
import bean.ServerStatus;
import bean.ServerVariables;

public class MysqlChart extends JPanel implements ServerVariablesListener, ServerStatusListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel globalPanel = new JPanel();

    private ChartBean bean;
    private MysqlServer server;
    private boolean sendDataToRecorder;

    private FormulaParser formula;
    private FormulaParser minFormula;
    private FormulaParser maxFormula;

    private static final int NUMBER_OF_DOTS = 900;
    private static final int CHART_WIDTH = 1000;
    private static final int CHART_HEIGHT = 80;
    private static final int COUNTERS_WIDTH = 100;

    private TimeSeries timeSerie;

    private double current;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double avg = 0;
    private long total = 0;

    private JLabel currentLabel = new JLabel("Current: 0");
    private JLabel minLabel = new JLabel("Min: 0");
    private JLabel maxLabel = new JLabel("Max: 0");
    private JLabel avgLabel = new JLabel("Avg: 0");

    private XYPlot plot;

    private JButton resetButton = new JButton("Reset");

    public MysqlChart(String title, ChartBean bean, MysqlServer server, boolean statusListener){

        super();

        this.bean = bean;
        this.server = server;
        sendDataToRecorder = statusListener;
        formula = new FormulaParser(bean.getFormula());
        if(bean.getMaxFormula()!=null && bean.getMinFormula()!=null){
            minFormula = new FormulaParser(bean.getMinFormula());
            maxFormula = new FormulaParser(bean.getMaxFormula());
        }

        timeSerie = new TimeSeries("TimeSerie");
        timeSerie.setNotify(false);

        long now = new Date().getTime();
        for (int i = 0; i < NUMBER_OF_DOTS; i++) {
            timeSerie.addOrUpdate(new Second(new Date(now-((i+1)*NUMBER_OF_DOTS))), 0);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(timeSerie);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                null,
                null,
                null,
                dataset,
                false,
                false,
                false
        );

        plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint( Color.black );

        Color gridColor = new Color(0,16,128);
        Color lineColor = new Color(0,160,255);

        plot.setDomainGridlinePaint(gridColor);
        plot.setDomainGridlineStroke(new BasicStroke(2));

        plot.setRangeGridlinePaint(gridColor);
        plot.setRangeGridlineStroke(new BasicStroke(2));
        //plot.getRangeAxis().setVisible(false);

        if(bean.getMaxFormula()!=null && bean.getMinFormula()!=null){
            plot.getRangeAxis().setAutoRange(false);
        }

        plot.getRenderer().setSeriesPaint(0, lineColor);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2));

        chart.setBackgroundPaint(getBackground());

        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension(CHART_WIDTH,CHART_HEIGHT));
        cp.setMouseZoomable(false);


        JPanel minmaxPanel = new JPanel();
        minmaxPanel.setLayout(new GridLayout(4,1));

        minmaxPanel.add(currentLabel);
        minmaxPanel.add(minLabel);
        minmaxPanel.add(maxLabel);
        minmaxPanel.add(avgLabel);
        minmaxPanel.setBorder(BorderFactory.createTitledBorder(""));


        JPanel statPanel = new JPanel();
        statPanel.setLayout(new BorderLayout());
        Dimension d = new Dimension(COUNTERS_WIDTH, CHART_HEIGHT);
        statPanel.setPreferredSize(d);
        statPanel.add(minmaxPanel, BorderLayout.CENTER);
        resetButton.addActionListener(this);
        statPanel.add(resetButton, BorderLayout.SOUTH);


        globalPanel.setLayout(new FlowLayout());
        globalPanel.add(cp);
        globalPanel.add(statPanel);

        add(globalPanel);

        setBorder(BorderFactory.createTitledBorder(title));

        server.addServerVariablesListener(this);
        if(statusListener){
            onServerStatusChange(server);
            server.addServerStatusListener(this);
        }
    }

    public void addDatum(final double n, final Date date) {

        Runnable updateData = new Runnable() {
            public void run() {
                try {
                    NumberFormat nf = NumberFormat.getInstance();
                    // timeSerie.add(new Second(), n);
                    TimeSeriesDataItem item = timeSerie.addOrUpdate(new Second(date), n);
                    if(item==null) timeSerie.delete(0, 0);
                    current = n;
                    if (total == 0) {
                        min = max = avg = n;
                    } else {
                        if (n < min)
                            min = n;
                        if (n > max)
                            max = n;
                        avg = ((avg * total) + n) / (total + 1);
                    }
                    total++;

                    currentLabel.setText("Current: " + nf.format(current));
                    minLabel.setText("Min: " + nf.format(min));
                    maxLabel.setText("Max: " + nf.format(max));
                    avgLabel.setText("Avg: " + nf.format(avg));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(sendDataToRecorder)
                    DataRecorder.getInstance().storeChartDatum(bean.getName(), server, n, date);

                if(isShowing()){
                    timeSerie.setNotify(true);
                    timeSerie.fireSeriesChanged();
                    timeSerie.setNotify(false);
                }
            }
        };

        SwingUtilities.invokeLater(updateData);

    }

    public void setRange(double min, double max){
        plot.getRangeAxis().setRange(min, max);
    }

    @Override
    public void onServerVariablesDownloaded(ServerVariables vars) {
        Map<String, String> values = new HashMap<String, String>();
        if(vars.getServerVariables()!=null)
            values.putAll(vars.getServerVariables());
        if(vars.getStatusVariables()!=null)
            values.putAll(vars.getStatusVariables());


        Double datum = formula.eval(values);
        if(datum!=null)
            addDatum(datum, vars.getDate());

        if(bean.getMaxFormula()!=null && bean.getMinFormula()!=null){
            Double minDatum = minFormula.eval(values);
            Double maxDatum = maxFormula.eval(values);

            if(minDatum!=null && maxDatum!=null)
                setRange(minDatum, maxDatum);
        }
    }

    @Override
    public void onServerStatusChange(MysqlServer server) {
        removeAll();

        if(server.getStatus() != ServerStatus.CONNECTED)
            add(new JLabel(MainGUI.errorMessage));
        else
            add(globalPanel);

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resetButton){
            min = Double.MAX_VALUE;
            max = Double.MIN_VALUE;
            avg = 0;
            total = 0;
        }
    }
}
