package core;

/**
 * Created by Administrator on 2015/12/25.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ServerManager {

    private static String configFile = "src/config/servers.xml";

    private Map<Integer, MysqlServer> servers = new HashMap<Integer, MysqlServer>();
    private Map<String, List<MysqlServer>> group2servers = new HashMap<String, List<MysqlServer>>();
    private int nextServerId = 0;

    private static ServerManager instance;


    private ServerManager() {

        File serverFile = new File(configFile);
        if (!serverFile.exists()) {
            FileWriter outFile = null;
            PrintWriter out = null;
            try {
                outFile = new FileWriter(serverFile);
                out = new PrintWriter(outFile);
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println();
                out.print("<servers />");
                out.close();
            } catch (IOException e) {
                //  Application.showError(e.getMessage());
            } finally {
                try {
                    if (out != null) out.close();
                    if (outFile != null) outFile.close();
                } catch (IOException e) {
                    //   Application.showError(e.getMessage());
                }
            }
        } else {
            try {
                checkXMLFile();
            } catch (Exception e) {
                //    Application.showError(e.getMessage());
            }
        }


        try {
            reload();
        } catch (Exception e) {
            //    Application.showError(e.getMessage());
        }
    }

    private void checkXMLFile() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(configFile);
        NodeList nodes = doc.getElementsByTagName("servergroup");
        boolean toSave = false;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            NodeList childNodes = n.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node c = childNodes.item(j);
                if (c.getNodeName().equals("server")) {
                    String code = c.getAttributes().getNamedItem("id").getTextContent();
                    Element el = doc.createElement("serverRef");
                    el.setAttribute("id", code);
                    n.removeChild(c);
                    n.appendChild(el);
                    toSave = true;
                }
            }
        }

        if (toSave)
            saveXML(doc);
    }

    private void reload() throws Exception {
        nextServerId = 0;
        servers.clear();
        group2servers.clear();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(configFile);
        NodeList nodes = doc.getElementsByTagName("server");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            int id = Integer.parseInt(getNodeChildValue(n, "id"));
            if (id > nextServerId) nextServerId = id;
            String name = getNodeChildValue(n, "name");
            String host = getNodeChildValue(n, "host");
            int port = Integer.parseInt(getNodeChildValue(n, "port"));
            String username = getNodeChildValue(n, "username");
            String password = getNodeChildValue(n, "password");
            String db=getNodeChildValue(n,"db");
            MysqlServer server = new MysqlServer(id, name, host, port, username, password,db);
            servers.put(id, server);
        }


        nextServerId++;


        NodeList serverGroupNodes = doc.getElementsByTagName("servergroup");
        for (int i = 0; i < serverGroupNodes.getLength(); i++) {
            Node n = serverGroupNodes.item(i);
            String groupName = n.getAttributes().getNamedItem("name").getTextContent();
            List<MysqlServer> groupServers = new ArrayList<MysqlServer>();
            NodeList serverRefs = n.getChildNodes();
            for (int j = 0; j < serverRefs.getLength(); j++) {
                Node serverRef = serverRefs.item(j);
                if (serverRef.getNodeName().equals("serverRef")) {
                    String nodeId = serverRef.getAttributes().getNamedItem("id").getTextContent();
                    MysqlServer server = servers.get(Integer.parseInt(nodeId));
                    if (server != null) {
                        groupServers.add(server);
                    }
                }
            }
            group2servers.put(groupName, groupServers);
        }
    }

    public static synchronized ServerManager getInstance() {
        if (instance == null) instance = new ServerManager();

        return instance;
    }

    public MysqlServer[] getAllServers() {
        Collection<MysqlServer> values = servers.values();
        if (values == null)
            return new MysqlServer[0];

        return values.toArray(new MysqlServer[0]);
    }



    public MysqlServer[] getServersByGroup(String serverGroup) {
        List<MysqlServer> list = group2servers.get(serverGroup);
        if (list == null)
            return new MysqlServer[0];

        return list.toArray(new MysqlServer[0]);
    }







    private String getNodeChildValue(Node n, String elementName) {
        NodeList list = n.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node c = list.item(i);
            if (!c.getNodeName().equals(elementName)) continue;
            return c.getTextContent();
        }
        return null;
    }

    private void saveXML(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(new FileWriter(configFile));
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        result.getWriter().flush();
    }

    public void removeServerGroup(String serverGroup) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(configFile);
        NodeList nodes = doc.getElementsByTagName("servergroup");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (!n.getAttributes().getNamedItem("name").getTextContent().equals(serverGroup)) continue;

            //servergroup found
            n.getParentNode().removeChild(n);

            saveXML(doc);

            reload();

            return;
        }
    }

    public void addServerGroup(String serverGroup, int[] serverRefs) throws Exception {
        if (group2servers.containsKey(serverGroup))
            throw new Exception("A server group called " + serverGroup + " has already been defined. Choose a different server group name");

        for (int i = 0; i < serverRefs.length; i++) {
            if (!servers.containsKey(serverRefs[i]))
                throw new Exception("The serverRef " + serverRefs[i] + " does not point to a valid server");
        }
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(configFile);
        Element el = doc.createElement("servergroup");
        el.setAttribute("name", serverGroup);
        for (int i : serverRefs) {
            Element sub = doc.createElement("serverRef");
            sub.setAttribute("id", String.valueOf(i));
            el.appendChild(sub);
        }
        doc.getFirstChild().appendChild(el);

        saveXML(doc);

        reload();
    }




}
