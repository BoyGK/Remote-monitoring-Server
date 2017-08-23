package bean;

import login.LoginManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

public class UserServes {

    UserDao dao;

    public UserServes() {
        dao = new UserDao();
    }

    public boolean login(String username, String password) {
        UserBean user = dao.find(new UserBean(username, password));
        if (user != null) {
            try {
                user.setIp(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            user.setLasttime(new Date().toString());
            dao.update(user);
            LoginManager.isAdmin = user.getIsadmin();
            return true;
        }
        return false;
    }

    public List<UserBean> getAllUsers(){
        return dao.findAll();
    }

    public boolean update(UserBean userBean){
        return dao.update(userBean);
    }

    public boolean delete(UserBean user){
        return dao.delete(user);
    }

    public boolean save(UserBean user){
        int id;
        List list = dao.findAll();
        if(list!=null&&list.size()!=0){
            id = list.size()+1;
        }else{
            id = 0;
        }
        user.setId(id);
        return dao.save(user);
    }

}
