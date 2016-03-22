package bean;

import core.MysqlServer;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ProcessList {
    private Date date = new Date();
    private List<ProcessListItem> items;
    private MysqlServer mysqlServer;

    public ProcessList(List<ProcessListItem> items, MysqlServer mysqlServer) {
        super();
        this.items = items;
        this.mysqlServer = mysqlServer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProcessListItem> getItems() {
        return items;
    }

    public void setItems(List<ProcessListItem> items) {
        this.items = items;
    }

    public MysqlServer getMysqlServer() {
        return mysqlServer;
    }

    public void setMysqlServer(MysqlServer mysqlServer) {
        this.mysqlServer = mysqlServer;
    }


}
