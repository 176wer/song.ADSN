package charts;

/**
 * Created by Administrator on 2015/12/26.
 */
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import core.MysqlServer;
import core.ServerManager;
import gui.MainGUI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import bean.ChartBean;

public class ChartsPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String configFile = "src/config/charts.xml";

    public ChartsPanel(String group){
        List<ChartBean> beans = getCharts();
        MysqlServer[] servers = ServerManager.getInstance().getServersByGroup(group);

        //costruisco la gui
        setLayout(new BorderLayout());


        //setto i grafici per ogni istanza
        JTabbedPane instancesPane = new JTabbedPane();
        for (int i=0; i<servers.length; i++) {
            MysqlServer server = servers[i];
            InstanceChartsPanel serverPanel = new InstanceChartsPanel(server, beans);

            JScrollPane jsp = new JScrollPane(serverPanel);
            jsp.getVerticalScrollBar().setUnitIncrement(5);
            jsp.getHorizontalScrollBar().setUnitIncrement(5);

            instancesPane.add(server.getName(), jsp);
        }

        try {
        } catch (Exception e) {
            MainGUI.showError("Unable to get user settings for plot refresh time: "+e.getMessage());
        }





        JTabbedPane choicePane = new JTabbedPane();
        choicePane.add("Show charts per instance", instancesPane);


        add(choicePane, BorderLayout.CENTER);
    }


    private List<ChartBean> getCharts(){
        List<ChartBean> charts = new ArrayList<ChartBean>();
        try{

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(configFile);
            NodeList nodes = doc.getElementsByTagName("chart");
            for(int i=0; i<nodes.getLength(); i++){
                NodeList childNodes = nodes.item(i).getChildNodes();
                Long id = null;
                String name = null;
                String formula = null;
                String minFormula = null;
                String maxFormula = null;

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node c = childNodes.item(j);
                    if(c.getNodeName().equals("id"))
                        id = Long.parseLong(c.getTextContent());
                    else if(c.getNodeName().equals("name"))
                        name = c.getTextContent();
                    else if(c.getNodeName().equals("formula"))
                        formula = c.getTextContent();
                    else if(c.getNodeName().equals("minFormula"))
                        minFormula = c.getTextContent();
                    else if(c.getNodeName().equals("maxFormula"))
                        maxFormula = c.getTextContent();
                }

                if(id == null || name == null || formula == null){
                    MainGUI.showError("Unable to load chart correctly. Error in definition xml");
                    continue;
                }

                if(!name.matches("[\\w _]*")){
                    MainGUI.showError("The chart name '"+name+"' is not valid. Only alphanumeric characters, spaces and underscores are allowed");
                    continue;
                }

                ChartBean bean = new ChartBean(id, name, formula);

                bean.setMinFormula(minFormula);
                bean.setMaxFormula(maxFormula);

                charts.add(bean);
            }

        }
        catch(Exception e){
            System.err.println(e);
        }
        return charts;
    }
}
