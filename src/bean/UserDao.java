package bean;

import jbdc.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private JdbcUtil jdbcUtil;
    private Connection connection;

    public UserDao() {
        jdbcUtil = JdbcUtil.getInstance();
    }

    /**
     * 条件语句执行操作
     *
     * @param sql
     * @return
     */
    public List<UserBean> query(String sql) {
        List<UserBean> stUsers = new ArrayList<>();
        try {
            connection = jdbcUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = null;
            if (sql.contains("select") || sql.contains("SELECT")) {
                rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    UserBean stuser = new UserBean();
                    stuser.setId(rs.getInt(1));
                    stuser.setUsername(rs.getString(2));
                    stuser.setPassword(rs.getString(3));
                    stuser.setIp(rs.getString(4));
                    stuser.setLasttime(rs.getString(5));
                    stuser.setIsadmin(rs.getInt(6));
                    stUsers.add(stuser);
                }
            } else {
                ps.executeUpdate();
            }
            jdbcUtil.closeConnection(rs, ps, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stUsers;
    }

    /**
     * 单条数据查询
     *
     * @param user
     * @return
     */
    public UserBean find(UserBean user) {
        if (user != null) {
            if (user.getUsername() != null && user.getPassword() != null) {
                if (!"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
                    List<UserBean> u = query("SELECT * FROM users WHERE"
                            + " username = '" + user.getUsername()
                            + "'AND password = '" + user.getPassword() + "'");
                    if (u != null && u.size() != 0) {
                        return u.get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return allUsers
     */
    public List<UserBean> findAll() {
        List<UserBean> u = query("SELECT * FROM users");
        if (u != null && u.size() != 0) {
            return u;
        }
        return null;
    }

    /**
     * update
     *
     * @param user
     */
    public boolean update(UserBean user) {
        if (user != null) {
            if (user.getUsername() != null && user.getPassword() != null) {
                if (!"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
                    List<UserBean> sTuserBean = query("SELECT * FROM users WHERE username = '" + user.getUsername() + "\'");
                    if (sTuserBean != null && sTuserBean.size() != 0) {
                        String sql = "UPDATE users SET " +
                                "password = '" + user.getPassword() + "'," +
                                "ip = '" + user.getIp() + "'," +
                                "lasttime = '" + user.getLasttime() + "'" +
                                " WHERE username = '" + user.getUsername() + "'";
                        query(sql);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * insert
     *
     * @param user
     */
    public boolean save(UserBean user) {
        if (user != null) {
            if (user.getUsername() != null && user.getPassword() != null) {
                List<UserBean> sTuserBean = query("SELECT * FROM users WHERE username = '" + user.getUsername() + "\'");
                if (sTuserBean == null || sTuserBean.size() == 0) {
                    if (!"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
                        String sql = "INSERT INTO users VALUES (" +
                                user.getId() + ",\"" +
                                user.getUsername() + "\",\"" +
                                user.getPassword() + "\",\"" +
                                user.getIp() + "\",\"" +
                                user.getLasttime() + "\"," +
                                user.getIsadmin() + ")";
                        query(sql);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * delete
     *
     * @param user
     * @return
     */
    public boolean delete(UserBean user) {
        if (user != null) {
            if (user.getUsername() != null) {
                if (!"".equals(user.getUsername())) {
                    List<UserBean> sTuserBean = query("SELECT * FROM users WHERE username = '" + user.getUsername() + "\'");
                    if (sTuserBean != null && sTuserBean.size() != 0) {
                        String sql = "DELETE FROM users WHERE username='" + user.getUsername() + "'";
                        query(sql);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}