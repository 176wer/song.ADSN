package bean;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ProcessListItem {


        private long pid;
        private String user;
        private String host;
        private String db;
        private String command;
        private long time;
        private String state;
        private String info;


        public long getPid() {
            return pid;
        }
        public void setPid(long pid) {
            this.pid = pid;
        }
        public String getUser() {
            return user;
        }
        public void setUser(String user) {
            this.user = user;
        }
        public String getHost() {
            return host;
        }
        public void setHost(String host) {
            this.host = host;
        }
        public String getDb() {
            return db;
        }
        public void setDb(String db) {
            this.db = db;
        }
        public String getCommand() {
            return command;
        }
        public void setCommand(String command) {
            this.command = command;
        }
        public long getTime() {
            return time;
        }
        public void setTime(long time) {
            this.time = time;
        }
        public String getState() {
            return state;
        }
        public void setState(String state) {
            this.state = state;
        }
        public String getInfo() {
            return info;
        }
        public void setInfo(String info) {
            this.info = info;
        }

    }


