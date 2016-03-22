package charts;

/**
 * Created by Administrator on 2015/12/26.
 */
import core.MysqlServer;
import core.ServerStatusListener;
import gui.MainGUI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bean.ChartBean;
import bean.ServerStatus;



public class InstanceChartsPanel extends JPanel implements ServerStatusListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private JPanel serverPanel;
    private Set<MysqlChart> charts = new HashSet<MysqlChart>();

    public InstanceChartsPanel(MysqlServer server, List<ChartBean> beans) {
        serverPanel = new JPanel();
        serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.PAGE_AXIS));

        for (int j = 0; j < beans.size(); j++) {
            ChartBean bean = beans.get(j);
            MysqlChart mc = new MysqlChart(bean.getName(), bean, server, false);

            serverPanel.add(mc);
            charts.add(mc);
        }

        add(serverPanel);

        server.addServerStatusListener(this);
        onServerStatusChange(server);
    }

    @Override
    public void onServerStatusChange(MysqlServer server) {
        removeAll();

        if(server.getStatus() != ServerStatus.CONNECTED)
            add(new JLabel(MainGUI.errorMessage));
        else
            add(serverPanel);

        revalidate();
        repaint();
    }

}
