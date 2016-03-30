package gui;

import control.MyLogger;
import control.NodeLEList;
import control.NodeLife;
import control.SerialConnectionException;
import core.DrawCurve1;
import core.ImitateDraw;
import core.ImitateSend;
import core.Surface;
import gui.toolbar.QueryNode;
import gui.toolbar.ViewExper;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MainFrame extends Thread {

    private JFrame frame;
    private SerialParameters parameters;
    private DrawCurve1 drawCurve;
    private Surface NodeTopo;
    private SerialConnection SerCon;
    private JTabbedPane tabbedPane;
    private TablePanel tablePanel;
    private DynamicTree treePanel;
    private JButton gatherButton;
    private JButton CloseButton;
    private ConfDialog conf;
    private JTable table;
    private long startTime = System.nanoTime();
    private JLabel timelabel;
    private JLabel recorderLabel;
    private JLabel nodeCount;// 记录节点数
    private JTextArea logArea;
    private boolean isLife = false;// 判断节点是否开始发送数据
    // 测量节点寿命用
    private HashMap<String, NodeLife> lifeMap = new HashMap<String, NodeLife>();
    // 测量节点消耗能量，接收的能量设置为1，发送的能量设置为2
    private HashMap<String, Integer> energy = new HashMap<String, Integer>();
    private JMenu menu_2;
    private JMenuItem mntmNewMenuItem_2;
    private JCheckBox chckbxNewCheckBox;
    private boolean paintAll = true;// true,对整个拓扑进行重绘，false对选中的点进行重绘
	private JMenu add;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private LogDialog logDialog;
	private  ImitateSend imi;
	private ImitateDraw imidraw;
    private DrawHoistry dialog;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                 
                  
                    
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame window = new MainFrame();
                    window.frame.setVisible(true);
                    window.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public MainFrame() {
        parameters = new SerialParameters();

        initialize();
        SerCon = new SerialConnection(this);

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 1408, 877);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("蓝豹实验室");
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 1304, 41);
        frame.getContentPane().add(toolBar);

        gatherButton = new JButton("采集数据");
        gatherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Cursor previousCursor = frame.getCursor();
                if (conf == null) {
                    JOptionPane.showMessageDialog(frame, "请先进行参数设置", "提示", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(MainFrame.class.getResource("/image/error.png")));

                    return;
                }
                gatherButton.setEnabled(false);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                conf.setParameters();
                try {
                    SerCon.openConnection();
                    isLife = true;
                    System.out.println("数据完成");
                } catch (SerialConnectionException e2) {
                    AlertDialog ad = new AlertDialog(frame, "Error Opening Port!", "Error opening port,",
                            e2.getMessage() + ".", "Select new settings, try again.");
                    gatherButton.setEnabled(true);
                    frame.setCursor(previousCursor);
                    return;
                }
                portOpened();
                frame.setCursor(previousCursor);
            }
        });
        gatherButton.setIcon(new ImageIcon(MainFrame.class.getResource("/image/card.png")));
        toolBar.add(gatherButton);
        
                CloseButton = new JButton("关闭串口");
                CloseButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        portClosed();
                        isLife = false;
                    }
                });
                
        CloseButton.setIcon(new ImageIcon(MainFrame.class.getResource("/image/serial.png")));
        toolBar.add(CloseButton);
        
                JButton SetparamButton = new JButton("参数设置");
                SetparamButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        conf = new ConfDialog(frame, parameters, SerCon);
                        conf.setSize(500, 300);
                        conf.setVisible(true);

                    }
                });
                SetparamButton.setIcon(new ImageIcon(MainFrame.class.getResource("/image/gear.png")));
                toolBar.add(SetparamButton);

        JButton LocatButton = new JButton("位置拓扑");
        LocatButton.setIcon(new ImageIcon(MainFrame.class.getResource("/image/internet.png")));
        toolBar.add(LocatButton);

        JButton NetButton = new JButton("网络拓扑");
        NetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HistryTopo window = new HistryTopo();


            }
        });
        NetButton.setIcon(new ImageIcon(MainFrame.class.getResource("/image/node.png")));
        toolBar.add(NetButton);

        JButton button = new JButton("刷新");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FreshDialog dialog = new FreshDialog(SerCon);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);

                } catch (Exception en) {
                    en.printStackTrace();
                }
            }
        });
        button.setIcon(new ImageIcon(MainFrame.class.getResource("/image/refresh.png")));
        toolBar.add(button);

        ParameterPanel panel = new ParameterPanel();
        panel.setBorder(new TitledBorder(null, "数据显示", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 571, 173, 207);
        frame.getContentPane().add(panel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 72, 173, 483);
        frame.getContentPane().add(scrollPane);

        JScrollPane scrolTopo = new JScrollPane();
        NodeTopo = new Surface();
        scrolTopo.setViewportView(NodeTopo);

        treePanel = new DynamicTree(panel, NodeTopo);
        treePanel.tree.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "树形节点",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        scrollPane.setViewportView(treePanel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(193, 51, 1199, 504);
        frame.getContentPane().add(tabbedPane);


        tabbedPane.addTab("节点拓扑", null, scrolTopo, null);
        treePanel.setTable(tabbedPane);
        // 画参数曲线面板
         drawCurve= new DrawCurve1();
        tabbedPane.addTab("参数曲线", null, drawCurve, null);
        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setBounds(193, 565, 1199, 207);
        frame.getContentPane().add(tabbedPane_1);

        tablePanel = new TablePanel();
        tabbedPane_1.addTab("标准数据", null, tablePanel, null);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"16进制原始数据"}));

        JScrollPane scrollpanel = new JScrollPane(table);
        tabbedPane_1.addTab("原始数据", null, scrollpanel, null);

        JLabel lblNewLabel = new JLabel("运行时间");
        lblNewLabel.setBounds(20, 788, 72, 15);
        frame.getContentPane().add(lblNewLabel);

        timelabel = new JLabel("");
        timelabel.setBounds(93, 788, 72, 15);
        frame.getContentPane().add(timelabel);

        JLabel label_1 = new JLabel("记录数");
        label_1.setBounds(175, 788, 48, 15);
        frame.getContentPane().add(label_1);

        recorderLabel = new JLabel("");
        recorderLabel.setBounds(237, 788, 54, 15);
        frame.getContentPane().add(recorderLabel);

        JLabel lblNewLabel_1 = new JLabel("节点数");
        lblNewLabel_1.setBounds(304, 788, 48, 15);
        frame.getContentPane().add(lblNewLabel_1);

        nodeCount = new JLabel("");
        nodeCount.setBounds(363, 788, 54, 15);
        frame.getContentPane().add(nodeCount);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1
                .setViewportBorder(new TitledBorder(null, "日志", TitledBorder.LEADING, TitledBorder.TOP, null, null)); 
        logDialog=new LogDialog(scrollPane_1);
        

        logArea = new JTextArea();
        scrollPane_1.setViewportView(logArea);

        chckbxNewCheckBox = new JCheckBox("New check box");
        chckbxNewCheckBox.setBounds(428, 487, 103, 23);
        frame.getContentPane().add(chckbxNewCheckBox);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu = new JMenu("\u6587\u4EF6");
        menuBar.add(menu);
        
        JMenuItem mntmOpenFile = new JMenuItem("Open File");
        menu.add(mntmOpenFile);
        
        JMenuItem mntmClose = new JMenuItem("Close");
        menu.add(mntmClose);

        JMenu menu_1 = new JMenu("\u8BBE\u7F6E");
        menuBar.add(menu_1);

        JMenuItem mntmNewMenuItem = new JMenuItem("开启日志");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyLogger Mylog = new MyLogger();
                Mylog.start();
            }
        });
        menu_1.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("快照设置");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CameraDialog camera = new CameraDialog(drawCurve, NodeTopo);
                camera.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                camera.setVisible(true);
            }
        });
        menu_1.add(mntmNewMenuItem_1);

        menu_2 = new JMenu("查看");
        
        
        menuBar.add(menu_2);

        mntmNewMenuItem_2 = new JMenuItem("实验");
        mntmNewMenuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewExper dialog = new ViewExper();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        
        
        menu_2.add(mntmNewMenuItem_2); 
        
        JMenuItem menuItem_2 = new JMenuItem("节点状态");
        menuItem_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   ItemLabelDemo1 demo = new ItemLabelDemo1("Item Label Demo 1", lifeMap, energy);
                   demo.pack();
                   RefineryUtilities.centerFrameOnScreen(demo);
                   demo.setVisible(true);
        	}
        });
        menu_2.add(menuItem_2);
        
        JMenuItem menuItem_3 = new JMenuItem("日志");
        menuItem_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 logDialog.setVisible(true);
        	}
        });
        menu_2.add(menuItem_3);
        
        JMenu menu_3=new JMenu("工具");
        menuBar.add(menu_3);

        JMenuItem menuItem_12 = new JMenuItem("数据库后台");
        menuItem_12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        menu_3.add(menuItem_12);
        JMenuItem item3=new JMenuItem("数据库监控");
        menu_3.add(item3);
        item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));
        item3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
                MainGUI gui = new MainGUI();

            }
        	
        });
        
        JSeparator separator_3 = new JSeparator();
        menu_3.add(separator_3);
        
        menuItem_1 = new JMenuItem("参数监控");
        menuItem_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		  Monitor dialog = new Monitor();
                  dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                  dialog.setVisible(true);
        	}
        });
        menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        menu_3.add(menuItem_1);
        
        JSeparator separator_2 = new JSeparator();
        menu_3.add(separator_2);
        
        JMenu menu_4 = new JMenu("\u8C03\u8BD5");
        menuBar.add(menu_4);
        
        JMenuItem menuItem_4 = new JMenuItem("\u8C03\u8BD5\u8282\u70B9");
        menu_4.add(menuItem_4);
        
        JMenuItem menuItem_5 = new JMenuItem("调试程序");
        menuItem_5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		    imi=new ImitateSend(treePanel);
        	        imi.start();
        	        imidraw=new ImitateDraw(drawCurve);
        	        imidraw.start();
        	}
        });
        menu_4.add(menuItem_5);
        
        JSeparator separator = new JSeparator();
        menu_4.add(separator);
        
        JMenuItem menuItem_6 = new JMenuItem("\u6682\u505C");
        menu_4.add(menuItem_6);
        
        JMenuItem menuItem_7 = new JMenuItem("\u7EE7\u7EED");
        menu_4.add(menuItem_7);
        
        JMenuItem menuItem_9 = new JMenuItem("停止");
        menuItem_9.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		imi.setFlag(false);
        		imidraw.setFlag(false);
        	}
        });
        menu_4.add(menuItem_9);
        
        JSeparator separator_1 = new JSeparator();
        menu_4.add(separator_1);
        
        JMenuItem menuItem_8 = new JMenuItem("\u65B0\u5EFA\u8282\u70B9\u76D1\u89C6\u70B9");
        menu_4.add(menuItem_8);

        JMenu menu_5 = new JMenu("\u5206\u6790");
        menuBar.add(menu_5);

        JMenuItem menuItem_13 = new JMenuItem("\u7F51\u7EDC\u5206\u6790");
        menuItem_13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        menu_5.add(menuItem_13);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog = new DrawHoistry();
            }
        });
        JMenuItem menuItem_10 = new JMenuItem("历史数据");
        menuItem_10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               dialog.setVisible(true);

            }
        });
        menuItem_10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        menu_5.add(menuItem_10);

        JSeparator separator_5 = new JSeparator();
        menu_5.add(separator_5);

        JMenuItem menuItem_14 = new JMenuItem("\u8282\u70B9\u8D28\u91CF\u8BC4\u4EF7");
        menuItem_14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        menu_5.add(menuItem_14);

        JMenuItem menuItem_15 = new JMenuItem("节点能量消耗");
        menuItem_15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                HoistryEnergy dialog = new HoistryEnergy();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        menuItem_15.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        menu_5.add(menuItem_15);

        JSeparator separator_4 = new JSeparator();
        menu_5.add(separator_4);
        
        JMenu mnSearch = new JMenu("Search");
        menuBar.add(mnSearch);
        
        menuItem = new JMenuItem("查询节点");
        menuItem.setIcon(new ImageIcon(MainFrame.class.getResource("/image/search.png")));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mnSearch.add(menuItem);
        menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				  QueryNode dialog = new QueryNode();
	                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	                dialog.setVisible(true);			
			}
        	
        });
        
     
        
        
        
    }

    public void portClosed() {
        SerCon.closeConnection();
        gatherButton.setEnabled(true);
        CloseButton.setEnabled(false);

    }

    public void portOpened() {
        gatherButton.setEnabled(false);
        CloseButton.setEnabled(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    /*
     * 返回参数reference for 参数面板
     */
    public DrawCurve1 getDrawCurve() {
        return  drawCurve;
    }

    /*
     * 返回reference for SerialParameters
     */
    public SerialParameters getSerialParameters() {
        return parameters;
    }

    /*
     * 返回reference for DynamicTree
     */
    public DynamicTree getDynamicTree() {
        return treePanel;
    }

    /*
     * 返回reference for Topo
     */
    public Surface getTopo() {
        return NodeTopo;
    }

    /*
     * 返回reference for tablepane
     */
    public TablePanel getTablePane() {
        return tablePanel;
    }

    public JTable getJTable() {
        return table;
    }

    public JLabel getNodeLabel() {
        return nodeCount;
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    /*
     * 计算程序运行时长
     */
    public void run() {
        try {
            while (true) {
                Thread.sleep(2000);
                long endTime = (System.nanoTime() - startTime) / 1000000000;
                String rtime = Long.toString(endTime);
                timelabel.setText(rtime);
                recorderLabel.setText(String.valueOf(SerCon.getRecorder()));
                if (isLife) {
                    new NodeLEList(energy, lifeMap);
                }

            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the lifeMap
     */
    public HashMap<String, NodeLife> getLifeMap() {
        return lifeMap;
    }

    /**
     * lifeMap.
     *
     * @param lifeMap the lifeMap to set
     */
    public void setLifeMap(HashMap<String, NodeLife> lifeMap) {
        this.lifeMap = lifeMap;
    }

    /**
     * @return the energy
     */
    public HashMap<String, Integer> getEnergy() {
        return energy;
    }

    /**
     * energy.
     *
     * @param energy the energy to set
     */
    public void setEnergy(HashMap<String, Integer> energy) {
        this.energy = energy;
    }

    public SerialConnection getConnecton() {
        return SerCon;
    }
}
