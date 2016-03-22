package gui;

import core.ZEllipse;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Administrator on 2015/11/1.
 */
public class TablePanel extends JPanel {
    private DefaultTableModel model;

    public TablePanel() {
        super(new BorderLayout());
        Object[] columnName = {"节点地址", "温度", "电压", "时间", "路由"};
        model = new DefaultTableModel();
        model.setDataVector(null, columnName);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void InserTable(ZEllipse node) {
        Object[] RowData = {node.getAddr(), node.getTemp(), node.getVoltage(), node.getTime(), node.getStatus()};
        if (model.getRowCount() > 5000) {
            model.setNumRows(0);
        }
        model.addRow(RowData);
    }


}
