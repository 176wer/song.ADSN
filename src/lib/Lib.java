package lib;

import bean.NodeMark;
import core.ZEllipse;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.util.Enumeration;

/**
 * Created by Administrator on 2015/10/3.
 */

/**
 * ClassName: Lib <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(��ѡ). <br/>
 * date: 2016��3��9�� ����10:47:28 <br/>
 *
 * @author Administrator
 * @since JDK 1.6
 */
public class Lib {

    public void insertData(ZEllipse nodeBean) {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        Statement state = null;

        String sql = "insert into temp(number,zhentou,zhenlength,command,addr,word,datalength,temperature,voltage,rout,verification,time,rssi,light,humdity,vibration) values ("
                + NodeMark.mark + ",'" + nodeBean.getZhentou() + "','" + nodeBean.getLength() + "','"
                + nodeBean.getType() + "','" + nodeBean.getAddr() + "','" + nodeBean.getWord() + "', '"
                + nodeBean.getDatalength() + "','" + nodeBean.getTemp() + "','" + nodeBean.getVoltage() + "','"
                + nodeBean.getRoute() + "','" + nodeBean.getCheck() + "','" + nodeBean.getTime() + "',"
                + nodeBean.getRssi() + ",'" + nodeBean.getLight() + "','" + nodeBean.getHumidity() + "','"
                + nodeBean.getVibration() + "')";

        try {
            state = conn.createStatement();

            state.execute(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                state.close();
                pool.releaseConn(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // �γ���ʷ��������
    public void insertTopo(int i, DefaultMutableTreeNode rootNode) {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();

        String sql = "insert into topo values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            Enumeration enumeration = rootNode.breadthFirstEnumeration();
            while (enumeration.hasMoreElements()) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumeration.nextElement();
                ZEllipse zel = (ZEllipse) child.getUserObject();
                ps.setString(1, zel.getTime());
                ps.setInt(2, i);
                ps.setString(3, zel.getAddr());
                ps.setString(4, zel.getRoute());
                ps.setString(5, zel.getTemp());
                ps.setString(6, zel.getVoltage());
                ps.setString(7, zel.getLight());
                ps.setString(8, zel.getHumidity());
                ps.setString(9, zel.getVibration());
                ps.setInt(10, zel.getStatus());
                ps.addBatch();

            }

            ps.executeBatch();

        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            try {
                ps.close();
                pool.releaseConn(conn);
            } catch (SQLException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        }

    }

    
    public int getTopoMaxID() {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        String sql = "select * from topo order by number desc  limit 0,1";
        Statement stm = null;
        ResultSet rs = null;
        int MAX = 0;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                MAX = rs.getInt(2);
            }


        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            try {
                rs.close();
                stm.close();
                pool.releaseConn(conn);
            } catch (SQLException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }

        return MAX;

    }


    public void insertExistNode(int id, String NodeName) {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        String sql = "insert into na values ('" + id + "','" + NodeName + "')";
        try {
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate(sql);
            stm.close();
            pool.releaseConn(conn);
        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }

    // 获取记录数，总共分层
    public int getCount() {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        String sql = "select count( *) from temp";
        int count = 0;
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);

            while (rs.next()) {
                count = rs.getInt(1);

            }
            state.close();
            pool.releaseConn(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //
    public int getNodeHistroyCount(String NodeAddr) {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        String sql = "select count( *) from temperature where addr='"+NodeAddr+"'";
        int counts=0;
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                counts = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stm.close();
                pool.releaseConn(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return counts;

    }


}
