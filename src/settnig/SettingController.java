package settnig;

import bean.UserBean;
import bean.UserProperty;
import bean.UserServes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    TableView<UserProperty> tableview;
    @FXML
    TableColumn<UserProperty, Integer> user_id;
    @FXML
    TableColumn<UserProperty, String> user_name;
    @FXML
    TableColumn<UserProperty, String> user_pass;
    @FXML
    TableColumn<UserProperty, String> ip;
    @FXML
    TableColumn<UserProperty, String> time;
    @FXML
    TableColumn<UserProperty, Integer> isadmin;

    @FXML
    TextField user;
    @FXML
    PasswordField pass;
    @FXML
    PasswordField re_pass;
    @FXML
    Button addUser;
    @FXML
    Button delUser;
    @FXML
    Button chPass;

    private UserServes serves;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_id.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        user_name.setCellValueFactory(param -> param.getValue().usernameProperty());
        user_pass.setCellValueFactory(param -> param.getValue().passwordProperty());
        ip.setCellValueFactory(param -> param.getValue().ipProperty());
        time.setCellValueFactory(param -> param.getValue().lasttimeProperty());
        isadmin.setCellValueFactory(param -> param.getValue().isadminProperty().asObject());

        serves = new UserServes();

        initView();

        onClick();
    }

    private void onClick() {
        tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                user.setText(newValue.getUsername());
                pass.setText(newValue.getPassword());
            }
        });
        addUser.setOnAction(event -> addUser());
        delUser.setOnAction(event -> delUser());
        chPass.setOnAction(event -> chPass());
    }

    /**
     * 更改密码
     */
    private void chPass() {
        if (!"".equals(user.getText()) && !"".equals(pass.getText()) && !"".equals(re_pass.getText())) {
            if (pass.getText().equals(re_pass.getText())) {
                if (!serves.update(new UserBean(user.getText(), pass.getText()))) {
                    JOptionPane.showMessageDialog(null, "用户名不存在!!!");
                } else {
                    initView();
                    user.setText("");
                    pass.setText("");
                    re_pass.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "密码不一致!!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "不能为空!!!");
        }
    }

    /**
     * 删除用户
     */
    private void delUser() {
        if (!"".equals(user.getText())) {
            if (!serves.delete(new UserBean(user.getText(), ""))) {
                JOptionPane.showMessageDialog(null, "用户名不存在!!!");
            } else {
                initView();
                user.setText("");
                pass.setText("");
                re_pass.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择用户!!!");
        }
    }

    /**
     * 添加用户
     */
    private void addUser() {
        if (!"".equals(user.getText()) && !"".equals(pass.getText()) && !"".equals(re_pass.getText())) {
            if (pass.getText().equals(re_pass.getText())) {
                if (!serves.save(new UserBean(user.getText(), pass.getText()))) {
                    JOptionPane.showMessageDialog(null, "用户名存在!!!");
                } else {
                    initView();
                    user.setText("");
                    pass.setText("");
                    re_pass.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "密码不一致!!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "不能为空!!!");
        }
    }

    /**
     * 初始用户列表
     */
    private void initView() {
        ObservableList<UserProperty> list = FXCollections.observableArrayList();

        for (UserBean userBean : serves.getAllUsers()) {
            if (userBean != null) {
                UserProperty user = new UserProperty(userBean);
                list.add(user);
            } else {
                break;
            }
        }
        tableview.setItems(list);
    }
}
