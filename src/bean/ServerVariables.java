package bean;

import core.MysqlServer;

import java.util.Date;
import java.util.Map;


/**
 * Created by Administrator on 2015/12/25.
 */
public class ServerVariables {

    private Date date;
    private Map<String, String> statusVariables;
    private Map<String, String> serverVariables;
    private MysqlServer server;

    public ServerVariables(Map<String, String> statusVariables,
                           Map<String, String> serverVariables, MysqlServer server) {
        super();
        this.statusVariables = statusVariables;
        this.serverVariables = serverVariables;
        this.server = server;
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public Map<String, String> getStatusVariables() {
        return statusVariables;
    }

    public Map<String, String> getServerVariables() {
        return serverVariables;
    }

    public MysqlServer getServer() {
        return server;
    }




}
