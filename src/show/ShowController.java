package show;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import serves.ServerSocketTCP;
import serves.ServerSocketThread;
import serves.SocketManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowController implements Initializable {

    @FXML
    ImageView imageview;

    @FXML
    ListView<String> listview;

    @FXML
    Button jg;

    @FXML
    Button setting;

    Socket socket;

    ObservableList<String> strList = FXCollections.observableArrayList();

    private ServerSocketTCP serverSocketTCP;
    private ServerSocketThread socketThread;

    private static boolean FIRST = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClick();
        init();
    }

    /**
     * 开启服务
     */
    private void init() {
        socketThread = new ServerSocketThread(sockets -> {
            Platform.runLater(() -> {
                strList.add(sockets.get(sockets.size() - 1).getInetAddress().getHostAddress());
                listview.setItems(strList);      //刷新listView
            });
            if (FIRST) {
                openSocket(sockets.get(0));      //开启首次连接的监控
                this.socket = sockets.get(0);
                FIRST = false;
            }
        });
        socketThread.start();
    }

    /**
     * listView 监听事件
     */
    private void onClick() {
        listview.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    System.out.println(newValue);
                    serverSocketTCP.close();                                                    //关闭现有链接
                    for (Socket socket : SocketManager.sockets) {                            //遍历当前所有连接
                        if (socket != null) {
                            if (socket.getInetAddress().getHostAddress().equals(newValue)) { //判断点击的socket
                                openSocket(socket);                                          //开启监控
                                this.socket = socket;
                            }
                        }
                    }
                });
        jg.setOnAction(event -> sendJg());
        //setting.setOnAction(event -> new Setting().start());
    }


    /**
     * 发送警告
     */
    private void sendJg() {
        if(this.socket!=null){
            try {
                System.out.println(jg);
                OutputStream os = this.socket.getOutputStream();
                os.write("jg".getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示当先socket的监控界面
     *
     * @param socket
     */
    private void openSocket(Socket socket) {
        serverSocketTCP = new ServerSocketTCP(socket, image -> Platform.runLater(() -> {
            WritableImage writableImage = null;
            writableImage = SwingFXUtils.toFXImage(image, writableImage);
            imageview.setImage(writableImage);
        }));
        serverSocketTCP.start();
    }

}
