package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ParameterPanel extends JPanel {
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private JLabel label_4;


    /**
     * Create the panel.
     */
    public ParameterPanel() {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("温度");
        lblNewLabel.setBounds(10, 30, 36, 15);
        add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("湿度");
        lblNewLabel_1.setBounds(10, 54, 54, 15);
        add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("光强");
        lblNewLabel_2.setBounds(10, 79, 54, 15);
        add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("振动");
        lblNewLabel_3.setBounds(10, 104, 54, 15);
        add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("RSSI");
        lblNewLabel_4.setBounds(10, 129, 54, 15);
        add(lblNewLabel_4);

        label = new JLabel("");
        label.setBounds(56, 30, 42, 15);
        add(label);

        label_1 = new JLabel("");
        label_1.setBounds(56, 54, 36, 15);
        add(label_1);

        label_2 = new JLabel("");
        label_2.setBounds(56, 79, 36, 15);
        add(label_2);

        label_3 = new JLabel("");
        label_3.setBounds(56, 104, 36, 15);
        add(label_3);

        label_4 = new JLabel("");
        label_4.setBounds(56, 129, 36, 15);
        add(label_4);

        JLabel lbc = new JLabel("°C");
        lbc.setBounds(96, 30, 54, 15);
        add(lbc);

        JLabel lblrh = new JLabel("%RH");
        lblrh.setBounds(96, 54, 54, 15);
        add(lblrh);

        JLabel lblCd = new JLabel("CD");
        lblCd.setBounds(96, 79, 29, 15);
        add(lblCd);

        JLabel lblNewLabel_6 = new JLabel("mm/s");
        lblNewLabel_6.setBounds(96, 104, 54, 15);
        add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setBounds(96, 129, 54, 15);
        add(lblNewLabel_7);

    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JLabel getLabel_1() {
        return label_1;
    }

    public void setLabel_1(JLabel label_1) {
        this.label_1 = label_1;
    }

    public JLabel getLabel_2() {
        return label_2;
    }

    public void setLabel_2(JLabel label_2) {
        this.label_2 = label_2;
    }

    public JLabel getLabel_3() {
        return label_3;
    }

    public void setLabel_3(JLabel label_3) {
        this.label_3 = label_3;
    }

    public JLabel getLabel_4() {
        return label_4;
    }

    public void setLabel_4(JLabel label_4) {
        this.label_4 = label_4;
    }
}
