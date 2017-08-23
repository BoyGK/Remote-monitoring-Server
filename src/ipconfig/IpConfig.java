package ipconfig;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import serves.SocketManager;
import show.Show;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpConfig {

    Button any, some;
    TextField begin, end;
    Text ip;

    public void start() {
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ipconfig.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("设置");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        initView(root);
        try {
            ip.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        any.setOnAction(event ->
                Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        some.setOnAction(event -> {
            if ("".equals(begin.getText()) || "".equals(end.getText())) {
                JOptionPane.showMessageDialog(null, "不能为空！！！");
            } else {
                if (isIp(begin.getText()) && isIp(end.getText())) {
                    String b[] = begin.getText().split("\\.");
                    SocketManager.BEGIN_URL = Integer.valueOf(b[b.length - 1]);
                    String e[] = end.getText().split("\\.");
                    SocketManager.END_URL = Integer.valueOf(e[e.length - 1]);
                    Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
                } else {
                    JOptionPane.showMessageDialog(null, "ip填写错误！！！");
                }
            }
        });

        primaryStage.setOnCloseRequest(event -> new Show().start());

    }

    private void initView(Parent root) {
        any = (Button) root.lookup("#any");
        some = (Button) root.lookup("#some");
        begin = (TextField) root.lookup("#begin");
        end = (TextField) root.lookup("#end");
        ip = (Text) root.lookup("#ip");
    }

    private boolean isIp(String ipAddress) {
        if (ipAddress.length() < 7 || ipAddress.length() > 15 || !ipAddress.contains(".")) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String IP[] = ipAddress.split("\\.");
        if(IP.length!=4){
            return false;
        }
        for(int i=0;i<4;i++){
            if(Integer.parseInt(IP[i])<0||Integer.parseInt(IP[i])>255){
                return false;
            }
        }
        return true;
    }
}
