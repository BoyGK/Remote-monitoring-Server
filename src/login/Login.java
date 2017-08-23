package login;

import bean.UserServes;
import ipconfig.IpConfig;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import show.Show;

import java.io.IOException;

public class Login extends Application {

    private Button login, logout;
    private Text hint;
    private TextField user;
    private PasswordField pass;
    private CheckBox checkBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("µÇÂ¼");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        initView(root);

        signClick(primaryStage);
    }

    private void initView(Parent root) {
        login = (Button) root.lookup("#login");
        logout = (Button) root.lookup("#logout");

        user = (TextField) root.lookup("#username");
        pass = (PasswordField) root.lookup("#passw");

        hint = (Text) root.lookup("#hint");
        checkBox = (CheckBox) root.lookup("#cb");
    }

    private void signClick(Stage primaryStage) {
        UserServes serves = new UserServes();
        login.setOnAction(event -> {
            if (serves.login(user.getText(), pass.getText())) {
                Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
                if (!checkBox.isSelected()) {
                    new Show().start();
                } else {
                    new IpConfig().start();
                }
            } else {
                hint.setText("login Error. \nUserName or PassWord Wr.");
            }
        });

        logout.setOnAction(event -> Event.fireEvent(primaryStage,
                new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
