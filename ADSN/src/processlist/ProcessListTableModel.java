package processlist;

/**
 * Created by Administrator on 2015/12/26.
 */


import bean.ProcessList;
import bean.ProcessListItem;
import core.MysqlServer;
import core.ProcessListListener;


import javax.swing.table.AbstractTableModel;
import java.util.List;


public class ProcessListTableModel extends AbstractTableModel implements ProcessListListener {

    private static final long serialVersionUID = 1L;
    private static String[] header = {"Pid", "User", "Host", "DB", "Command", "Time", "State", "WT", "Info"};
    private MysqlServer server;
    private Class<?>[] headerClasses = {Integer.class, String.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, String.class};
    private Object[][] data;
    private boolean inAutoRefreshMode = false;

    public ProcessListTableModel(MysqlServer server) {
        this.server = server;
    }

    public int getColumnCount() {
        return header.length;
    }

    public int getRowCount() {
        if (data == null) return 0;

        return data.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public String getColumnName(int col) {
        return header[col];
    }

    public Class<?> getColumnClass(int col) {
        return headerClasses[col];
    }

    public void onProcessListGenerated(ProcessList processList) {
        if (!inAutoRefreshMode)
            server.removeProcessListListener(this);

        List<ProcessListItem> items = processList.getItems();
        data = new Object[items.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Object[header.length];
            ProcessListItem item = items.get(i);
            //{"Pid","User","Host","DB","Command","Time","State","Info"};
            data[i][0] = item.getPid();
            data[i][1] = item.getUser();
            data[i][2] = item.getHost();
            data[i][3] = item.getDb();
            data[i][4] = item.getCommand();
            data[i][5] = item.getTime();
            data[i][6] = item.getState();
            data[i][7] = (item.getInfo() != null) ? item.getTime() : 0;
            data[i][8] = item.getInfo();
        }
        fireTableDataChanged();
    }

    public Object[][] getData() {
        return data;
    }

    public void refreshData() {
        if (!inAutoRefreshMode)
            server.addProcessListListener(this);
    }

    public boolean isInAutoRefreshMode() {
        return inAutoRefreshMode;
    }

    public void setInAutoRefreshMode(boolean mode) {
        if (mode) {
            server.addProcessListListener(this);
            inAutoRefreshMode = true;
        } else {
            server.removeProcessListListener(this);
            inAutoRefreshMode = false;
        }
    }

}

