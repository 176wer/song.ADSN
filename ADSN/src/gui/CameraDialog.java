package gui;
import control.ImageThread;
import core.DrawCurve;
import core.Surface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
public class CameraDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private DrawCurve drawCurve;
    private Surface topo;
    /**
     * Create the dialog.
     */
    public CameraDialog(final DrawCurve drawCurve, final Surface topo) {
        this.drawCurve=drawCurve;
        this.topo=topo;
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JRadioButton caiji = new JRadioButton("自动采集");
        caiji.setBounds(44, 54, 103, 23);
        contentPanel.add(caiji);
        caiji.setMnemonic(KeyEvent.VK_B);
        caiji.setSelected(true);
        caiji.setActionCommand("caiji");

        JLabel label = new JLabel("周期");
        label.setBounds(156, 58, 33, 15);
        contentPanel.add(label);

        textField = new JTextField();
        textField.setBounds(199, 55, 33, 21);
        contentPanel.add(textField);
        textField.setColumns(10);

        JRadioButton checkBox = new JRadioButton("手动采集");
        checkBox.setBounds(44, 96, 103, 23);
        checkBox.setMnemonic(KeyEvent.VK_B);
        checkBox.setActionCommand("shoudong");
        contentPanel.add(checkBox);
        ButtonGroup group = new ButtonGroup();
        group.add(checkBox);
        group.add(caiji);


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int time= Integer.parseInt(textField.getText().trim());
                ImageThread image=new ImageThread(time,drawCurve, topo);
                image.start();
                setVisible(false);
                dispose();
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);



        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);



    }
}
