package processlist;

/**
 * Created by Administrator on 2015/12/26.
 */
import core.MysqlServer;
import core.ServerManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class ProcessListPanel extends JPanel implements MouseListener, ActionListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JButton refreshButton;
    private JCheckBox refreshCheckBox;
    private JTabbedPane tabbedPane;
    private ServerManager serversManager = ServerManager.getInstance();
    private ProcessListInstancePanel[] instancePanels;
    private Boolean refreshing = false;
    private boolean autoRefresh = false;

    public ProcessListPanel(String group){
        super();
        setLayout(new BorderLayout());


        refreshButton = new JButton("Refresh");
        refreshButton.addMouseListener(this);

        refreshCheckBox = new JCheckBox("Auto refresh");
        refreshCheckBox.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshCheckBox);
        buttonPanel.add(refreshButton);

        tabbedPane = new JTabbedPane();
        MysqlServer[] servers = serversManager.getServersByGroup(group);
        instancePanels = new ProcessListInstancePanel[servers.length];
        for (int i = 0; i < servers.length; i++) {
            MysqlServer server = servers[i];
            ProcessListInstancePanel plPanel = new ProcessListInstancePanel(server);
            instancePanels[i] = plPanel;
            tabbedPane.add(server.getName(), plPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        doRefresh();
    }

    public boolean isAutoRefresh(){
        return autoRefresh;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        doRefresh();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void doRefresh(){

        synchronized (refreshing) {
            if(refreshing) return;
            refreshing = true;
        }

        Thread[] threads = new Thread[instancePanels.length];

        for (int i = 0; i < instancePanels.length; i++) {
            final int panel = i;
            Runnable r = new Runnable(){
                @Override
                public void run() {
                    instancePanels[panel].popolateTable();
                }
            };

            threads[i] = new Thread(r);
            threads[i].start();

        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
               // Application.showError(e.getMessage());
            }
        }

        synchronized (refreshing) {
            refreshing = false;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(refreshCheckBox.isSelected()){
            autoRefresh = true;
            refreshButton.setVisible(false);
            setAllAutoRefresh(true);
        }
        else{
            autoRefresh = false;
            refreshButton.setVisible(true);
            setAllAutoRefresh(false);
        }
    }

    private void setAllAutoRefresh(boolean autorefresh){
        for (ProcessListInstancePanel panel : instancePanels) {
            panel.setAutoRefreshMode(autorefresh);
        }
    }
}
