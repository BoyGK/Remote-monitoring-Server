package bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperty {

    private IntegerProperty id;
    private StringProperty username;
    private StringProperty password;
    private StringProperty ip;
    private StringProperty lasttime;
    private IntegerProperty isadmin;

    public UserProperty(UserBean userBean) {
        this.id = new SimpleIntegerProperty(userBean.getId());
        this.username = new SimpleStringProperty(userBean.getUsername());
        this.password = new SimpleStringProperty(userBean.getPassword());
        this.ip = new SimpleStringProperty(userBean.getIp());
        this.lasttime = new SimpleStringProperty(userBean.getLasttime());
        this.isadmin = new SimpleIntegerProperty(userBean.getIsadmin());
    }

    public UserProperty() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getIp() {
        return ip.get();
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getLasttime() {
        return lasttime.get();
    }

    public StringProperty lasttimeProperty() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime.set(lasttime);
    }

    public int getIsadmin() {
        return isadmin.get();
    }

    public IntegerProperty isadminProperty() {
        return isadmin;
    }

    public void setIsadmin(int isadmin) {
        this.isadmin.set(isadmin);
    }
}
