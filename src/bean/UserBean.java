package bean;


import java.net.InetAddress;
import java.util.Date;

public class UserBean {

    private int id;
    private String username;
    private String password;
    private String ip;
    private String lasttime;
    private int isadmin;

    public UserBean(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isadmin = 0;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.lasttime = new Date().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
        this.isadmin = 0;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.lasttime = new Date().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public int getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(int isadmin) {
        this.isadmin = isadmin;
    }
}
