package show;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import login.LoginManager;
import serves.SocketManager;
import settnig.Setting;

import javax.swing.*;
import java.io.IOException;

public class Show {

    private ImageView imageView;
    private Button button,setting;

    public void start() {
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("show.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("监控");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        initView(primaryStage);

        imageView = (ImageView) root.lookup("#imageview");
        setting= (Button) root.lookup("#setting");
        setting.setOnAction(event -> {
            if(LoginManager.isAdmin!=0) {
                primaryStage.hide();
                new Setting().start(primaryStage);
            }else {
                JOptionPane.showMessageDialog(null,"当前账号不允许管理人员！！！");
            }
        });
        button = (Button) root.lookup("#signout");
        button.setOnAction(event ->
                Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST )));

        primaryStage.setOnCloseRequest(event -> {
            SocketManager.close();
            System.exit(0);
        });

    }

    public void initView(Stage primaryStage) {
        primaryStage.widthProperty().addListener((ov, width, t1) -> imageView.setFitWidth(width.doubleValue() - 205));
        primaryStage.heightProperty().addListener((ov, height, t1) -> imageView.setFitHeight(height.doubleValue()));
    }
}
