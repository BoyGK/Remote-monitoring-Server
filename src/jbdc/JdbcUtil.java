package jbdc;

import java.sql.*;

/**
 * Created by Gkpoter on 2017/5/31.
 */
public class JdbcUtil {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String dbName = "lstudy";
    private static final String passWord = "";
    private static final String userName = "root";
    private static final String url = "jdbc:mysql://localhost:3306/"
            + dbName + "?useUnicode=true&characterEncoding=UTF-8";

    private static JdbcUtil jdbcUtil = null;

    public static JdbcUtil getInstance() {
        if (jdbcUtil == null) {
            // ������� ��ֹ�̲߳���
            synchronized (JdbcUtil.class) {
                if (jdbcUtil == null) {
                    jdbcUtil = new JdbcUtil();
                }
            }
        }
        return jdbcUtil;
    }

    private JdbcUtil(){}

    // ͨ����̬�����ע�����ݿ���������֤ע��ִֻ��һ��
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, userName, passWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // �ر�����
    public void closeConnection(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
