package core;

import bean.ServerVariables;

/**
 * Created by Administrator on 2015/12/25.
 */
public interface ServerVariablesListener {

    public void onServerVariablesDownloaded(ServerVariables vars);
}
