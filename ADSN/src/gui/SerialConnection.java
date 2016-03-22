package gui;

import bean.NodeMark;
import control.*;
import core.DrawCurve;
import core.ZEllipse;
import lib.Lib;

import javax.comm.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class SerialConnection extends Thread implements SerialPortEventListener, CommPortOwnershipListener {


    private MainFrame frame;
    private SerialParameters parameters;
    private OutputStream os;
    private InputStream is;
    private DrawCurve drawCurve;
    private DynamicTree tree;
    private CommPortIdentifier portId;
    private SerialPort sPort;
    private int recorder = 0;//��¼���ݸ���
    private int topoID = 1;//��ʷ���˱�ʶ


    private JLabel nodeLabel;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Lib lib = new Lib();
    private boolean open;

    private TablePanel tablePanel;
    private HashSet<String> hashSet2 = new HashSet<String>();
    //�����Ҫɾ����setֵ
    private List<String> deletStr = new ArrayList<String>();
//    private NodeLog nodeLog = new NodeLog();
    //�̵�ַΪkey����ѹΪvalue
    private HashMap<String, String> hashMap = new HashMap<String, String>();
    private HashMap<String, String> chashMapnew = new HashMap<String, String>();
    //�����ڵ�������
    private HashMap<String, NodeLife> lifeMap = null;
    //�����ڵ��������������յ���������Ϊ1�����͵���������Ϊ2
    private HashMap<String, Integer> energy = null;


    public SerialConnection(MainFrame frame) {


        this.parameters = frame.getSerialParameters();
        drawCurve = frame.getDrawCurve();
        this.frame = frame;
        this.tree = frame.getDynamicTree();

        this.tablePanel = frame.getTablePane();
        lifeMap = frame.getLifeMap();
        energy = frame.getEnergy();
        open = false;
        nodeLabel = frame.getNodeLabel();
        start();

    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(PublicContent.getfreshtime());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            nodeLabel.setText(String.valueOf(tree.getNodeCounts()));
            if (tree.getNodeCounts() >= 2) {
                lib.insertTopo(topoID, tree.rootNode);
                topoID++;
            }

            tree.clear();
            tree.setNodeCounts();

            JTextArea logArea = frame.getLogArea();
            HashSet<String> hashSet0 = tree.getSet();

            HashSet<String> hashSet3 = (HashSet<String>) hashSet0.clone();

            if (hashSet3.size() > hashSet2.size()) {
                for (String set3 : hashSet3) {
                    if (hashSet2.add(set3)) {
                        String infor = set3 + "�ѳɹ�����������\n";
                        //�ڵ��������ʱ����¼����־��
                        logArea.append(TimeServer.getTime() + ": " + infor);
                      //  nodeLog.addLog(infor);
                        //
                        lib.insertExistNode(NodeMark.mark, set3.trim());
                        if (lifeMap.containsKey(set3)) {
                            NodeLife life = lifeMap.get(set3);
                            life.setSumIdleTime();
                            life.setDead(false);

                        } else {
                            NodeLife life = new NodeLife();
                            lifeMap.put(set3, life);
                        }

                    }
                }
            }


            if (hashSet3.size() < hashSet2.size()) {
                for (String set2 : hashSet2) {
                    if (hashSet3.add(set2)) {
                        deletStr.add(set2);
                        String infor2 = set2 + "�ѵ���";

                        logArea.append(TimeServer.getTime() + ": " + infor2 + "  �ڵ��ѹΪ��" + chashMapnew.get(set2) + "\n");


                    //    nodeLog.addLog(infor2 + "  �ڵ��ѹΪ��" + hashMap.get(set2) + "\n");

                        NodeLife life = lifeMap.get(set2);
                        life.setNowTime();
                        life.setDead(true);

                    }
                }

            }


            for (String str : deletStr) {
                hashSet2.remove(str);
            }

            deletStr.clear();
            chashMapnew = (HashMap<String, String>) hashMap.clone();
            hashMap.clear();
            tree.getSet().clear();


        }


    }

    public void openConnection() throws SerialConnectionException {


        try {
            portId =
                    CommPortIdentifier.getPortIdentifier(parameters.getPortName());
        } catch (NoSuchPortException e) {
            throw new SerialConnectionException(e.getMessage());
        }


        try {
            sPort = (SerialPort) portId.open("SerialDemo", 30000);
        } catch (PortInUseException e) {
            throw new SerialConnectionException(e.getMessage());
        }


        try {
            setConnectionParameters();
        } catch (SerialConnectionException e) {
            sPort.close();
            throw e;
        }

        try {
            os = sPort.getOutputStream();
            is = sPort.getInputStream();
        } catch (IOException e) {
            sPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }


        try {
            /*ע��һ��SerialPortEventListener�¼������������¼�*/
            sPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            sPort.close();
            throw new SerialConnectionException("too many listeners added");
        }

        //���ô����������¼�true��Ч��falsΪ��Ч
        sPort.notifyOnDataAvailable(true);

        //�����ж�ʱ��true��Ч��falseΪ��Ч
        sPort.notifyOnBreakInterrupt(true);


        try {
            sPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
        }


        portId.addPortOwnershipListener(this);

        open = true;
    }


    public void setConnectionParameters() throws SerialConnectionException {

        //  �ڶ�serialport����֮ǰ����serialport�������б���
        int oldBaudRate = sPort.getBaudRate();
        int oldDatabits = sPort.getDataBits();
        int oldStopbits = sPort.getStopBits();
        int oldParity = sPort.getParity();
        int oldFlowControl = sPort.getFlowControlMode();

        // ���ô������Ӳ������������ʧ�ܣ��򷵻ص�ԭ����״̬

        try {
            sPort.setSerialPortParams(parameters.getBaudRate(),
                    parameters.getDatabits(),
                    parameters.getStopbits(),
                    parameters.getParity());
        } catch (UnsupportedCommOperationException e) {
            parameters.setBaudRate(oldBaudRate);
            parameters.setDatabits(oldDatabits);
            parameters.setStopbits(oldStopbits);
            parameters.setParity(oldParity);
            throw new SerialConnectionException("Unsupported parameter");
        }

        // ���� flow ����.
        try {
            sPort.setFlowControlMode(parameters.getFlowControlIn()
                    | parameters.getFlowControlOut());
        } catch (UnsupportedCommOperationException e) {
            throw new SerialConnectionException("Unsupported flow control");
        }
    }

    /**
     * �رն˿ڣ����������Դ
     */
    public void closeConnection() {
        if (!open) {
            return;
        }

        if (sPort != null) {
            try {

                os.close();
                is.close();
            } catch (IOException e) {
                System.err.println(e);
            }
            sPort.close();
            portId.removePortOwnershipListener(this);
        }
        open = false;
    }

    /**
     * ����һ�� break signal.
     */
    public void sendBreak() {
        sPort.sendBreak(1000);
    }

    /**
     * @return true ������ڴ򿪣�����true������Ϊfalse.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * ʹ���¼�֪ͨ�����������ݶ�ȡ��д��
     */

    public void serialEvent(SerialPortEvent e) {

        StringBuffer inputBuffer = new StringBuffer();
        int newData = 0;
        byte[] readB = new byte[19];

        switch (e.getEventType()) {


            case SerialPortEvent.DATA_AVAILABLE:
                String[] stres = null;
                try {

                    while (is.available() > 0) {
                        is.read(readB);

                        stres = StringToHex.Hex(readB);
                        ZEllipse node = new ZEllipse(stres);
                        String w = node.getTemp();

                        tree.addObject(node);

                        //

                        hashMap.put(node.getAddr(), node.getVoltage());


                        // System.out.println(node.getAddr());
                        Date date = new Date();
                        String time = format.format(date);
                        node.setTime(time);

                        PublicNode.setNodeBean(node);
                        int temp = Integer.valueOf(node.getTemp());
                        lib.insertData(node);
                        if (temp != 255) {


                            // int hum=Integer.valueOf(node.getHumidity(),16);
                            //��ӳ�������������¶ȱ仯
                         //   drawCurve.addTemp(temp);
                            //    wenpane.addHumObservation(hum);
                            drawCurve.add(temp, Integer.valueOf(node.getHumidity()),Integer.valueOf(node.getLight()) , Integer.valueOf(node.getVibration()));
                            System.out.println("ʪ��" + node.getHumidity());
                            System.out.println("����" + node.getLight());
                            System.out.println("��" + node.getVibration());
                        }

                        //����¶�
//                        if(temp>Param.getTemp()&&!node.isRoute()){
//                        	String infor="�ڵ��ַΪ��"+node.getAddr()+"�¶�Ϊ��"+node.getTemp();
//                        	try{
//                        		PromptTemp p=new PromptTemp(infor);
//                        		p.setVisible(true);
//                        	}catch(Exception er){
//                        		
//                        	}
//                        }
                        //������������
                        String routeAddr = node.getRoute();
                        String selfAddr = node.getAddr();
                        Integer freq = energy.get(selfAddr);
                        energy.put(selfAddr, freq == null ? 1 : freq + 1);

                        Integer freq2 = energy.get(routeAddr);
                        energy.put(routeAddr, freq2 == null ? 1 : freq2 + 2);

                        if (PublicNode.getWenpane() != null && PublicNode.getAddr().equals(node.getAddr())) {
                            try {
                                PublicNode.getWenpane().add(temp, Integer.valueOf(node.getHumidity()),Integer.valueOf(node.getLight()) , Integer.valueOf(node.getVibration()));
                            } catch (Exception e3) {
                                System.out.println(e3);
                            }

                        }

                        //������ӵ���׼���ݱ����
                        tablePanel.InserTable(node);
                        //������ӵ�ԭʼ���ݱ����
                        DefaultTableModel model = (DefaultTableModel) frame.getJTable().getModel();
                        if (model.getRowCount() > 2000) {
                            model.setNumRows(0);
                        }
                        Object[] tdata = {ArrayToString.ToString(stres) + "\n"};
                        model.addRow(tdata);

                    }
                    recorder++;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                //messageAreaIn.append(ArrayToString.ToString(stres)+"\n");
                break;


            case SerialPortEvent.BI:
                // messageAreaIn.append("\n--- BREAK RECEIVED ---\n");
        }

    }


    public void ownershipChange(int type) {
        if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
            PortRequestedDialog prd = new PortRequestedDialog(frame);
        }
    }


    class KeyHandler extends KeyAdapter {
        OutputStream os;

        public KeyHandler(OutputStream os) {
            super();
            this.os = os;
        }


        public void keyTyped(KeyEvent evt) {
            char newCharacter = evt.getKeyChar();
            try {
                os.write((int) newCharacter);
            } catch (IOException e) {
                System.err.println("OutputStream write error: " + e);
            }
        }
    }

    public int getRecorder() {
        return recorder;
    }
}
