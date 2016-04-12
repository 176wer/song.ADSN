package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
/*
 * 如果端口被其他应用占用
 * 做出相应处理
 */

public class PortRequestedDialog extends JDialog implements ActionListener {

    private MainFrame frame;


    public PortRequestedDialog(MainFrame parent) {
        frame = parent;
        String lineOne = "Your port has been requested";
        String lineTwo = "by an other application.";
        String lineThree = "Do you want to give up your port?";
        Panel labelPanel = new Panel();
        labelPanel.setLayout(new GridLayout(3, 1));
        labelPanel.add(new Label(lineOne, Label.CENTER));
        labelPanel.add(new Label(lineTwo, Label.CENTER));
        labelPanel.add(new Label(lineThree, Label.CENTER));
        add(labelPanel, "Center");

        Panel buttonPanel = new Panel();
        Button yesButton = new Button("Yes");
        yesButton.addActionListener(this);
        buttonPanel.add(yesButton);
        Button noButton = new Button("No");
        noButton.addActionListener(this);
        buttonPanel.add(noButton);
        add(buttonPanel, "South");

        FontMetrics fm = getFontMetrics(getFont());
        int width = Math.max(fm.stringWidth(lineOne),
                Math.max(fm.stringWidth(lineTwo), fm.stringWidth(lineThree)));

        setSize(width + 40, 150);
        setLocation(frame.getFrame().getLocationOnScreen().x + 30,
                frame.getFrame().getLocationOnScreen().y + 30);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Yes")) {
            frame.portClosed(); 
        }

        setVisible(false);
        dispose();
    }
}
