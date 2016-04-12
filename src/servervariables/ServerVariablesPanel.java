package servervariables;

/**
 * Created by Administrator on 2015/12/26.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

import core.ServerManager;



public class ServerVariablesPanel extends JPanel implements MouseListener, KeyListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Boolean refreshing = false;

    private ServerVariablesTableModel tableModel;
    private JTable table;
    private JTextField filterText;

    public ServerVariablesPanel(String group, int type){
        super();
        setLayout(new BorderLayout());


        JButton refreshButton = new JButton("Refresh");
        refreshButton.addMouseListener(this);

        filterText = new JTextField();
        filterText.setColumns(50);
        filterText.addKeyListener(this);
        JLabel filterLable = new JLabel("Filter for ");


        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(filterLable);
        filterPanel.add(filterText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(filterPanel);
        bottomPanel.add(refreshButton);



        try {
            tableModel = new ServerVariablesTableModel(ServerManager.getInstance().getServersByGroup(group), type);
        } catch (Exception e) {
            e.printStackTrace();
            //Application.showError(e.getMessage());
        }

        table = new JTable(tableModel);
        JScrollPane jsp = new JScrollPane(table);

        table.setAutoCreateRowSorter(true);
        table.setRowHeight(table.getRowHeight()+4);

        table.setGridColor(Color.lightGray);

        add(jsp, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        doRefresh();

        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        table.getRowSorter().setSortKeys(sortKeys);
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

        tableModel.loadData();

        synchronized (refreshing) {
            refreshing = false;
        }
    }

    public void doFiltering(){
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {

            public boolean include(Entry<?,?> entry) {
                String search = filterText.getText();
                if(search.length()<=0) return true;

                String varName = (String) entry.getValue(0);
                return varName.toLowerCase().contains(search.toLowerCase());
            }
        };

        ((TableRowSorter<?>)table.getRowSorter()).setRowFilter(filter);
    }

    @Override
    public void keyPressed(KeyEvent e) {	}

    @Override
    public void keyReleased(KeyEvent e) {	}

    @Override
    public void keyTyped(KeyEvent e) {
        doFiltering();
    }
}

