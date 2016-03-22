package gui;

import charts.ChartsPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import processlist.ProcessListPanel;
import schema.SchemalPane;
import servervariables.ServerVariablesPanel;
import servervariables.ServerVariablesTableModel;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zgs on 2016/3/21.
 */
public class MainGUI extends JDialog {
    /**
     *
     */

    private static final String propertyFile = "src/config/property.xml";


    private JTabbedPane mainPanel;

    public static String errorMessage = "<html><center>The server is not properly connected. Please check the home tab.</center></html>";


    static {
        synchronized (MainGUI.class) {
            File serverFile = new File(propertyFile);
            if (!serverFile.exists()) {
                FileWriter outFile = null;
                PrintWriter out = null;
                try {
                    outFile = new FileWriter(serverFile);
                    out = new PrintWriter(outFile);
                    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    out.println();
                    out.print("<properties />");
                    out.close();
                } catch (IOException e) {
                    MainGUI.showError(e.getMessage());
                } finally {
                    try {
                        if (out != null) out.close();
                        if (outFile != null) outFile.close();
                    } catch (IOException e) {
                        MainGUI.showError(e.getMessage());
                    }
                }
            }
        }
    }


    public MainGUI() {
        super();



        setIconImage(new ImageIcon(ClassLoader.getSystemResource("image/icon.gif")).getImage());
        setSize(800, 600);
        setLocation(0, 0);
        setTitle("Mysql Monitoring");

        //setLayout(new BorderLayout());


        setVisible(true);


        String group = "df";
        if (group == null)
            System.exit(0);

        SchemalPane a = new SchemalPane(group);
        new Thread(a).start();
        mainPanel = new JTabbedPane(JTabbedPane.TOP);
        mainPanel.add("±Ì»∫", a);
        mainPanel.add("ProcessList", new ProcessListPanel(group));
        mainPanel.add("Charts", new ChartsPanel(group));
        mainPanel.add("Status Vars", new ServerVariablesPanel(group, ServerVariablesTableModel.STATUS_VARIABLES));
        mainPanel.add("Server Vars", new ServerVariablesPanel(group, ServerVariablesTableModel.SERVER_VARIABLES));

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(mainPanel, BorderLayout.CENTER);


        add(content);

        validate();

    }


    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public static String getProperty(String key, String defaultReturn) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(propertyFile);
        NodeList nodes = doc.getElementsByTagName("property");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.getAttributes().getNamedItem("name").getTextContent().equals(key)) continue;

            //property found
            return n.getTextContent();
        }

        return defaultReturn;
    }

    public static void setProperty(String key, String value) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(propertyFile);
        NodeList nodes = doc.getElementsByTagName("property");
        boolean found = false;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.getAttributes().getNamedItem("name").getTextContent().equals(key)) continue;

            //property found
            n.setTextContent(value);

            found = true;
            break;
        }


        if (!found) {
            Element el = doc.createElement("property");
            el.setAttribute("name", key);
            el.setTextContent(value);
            doc.getFirstChild().appendChild(el);
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(new FileWriter(propertyFile));
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        result.getWriter().flush();
    }



}

