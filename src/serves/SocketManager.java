package serves;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class SocketManager {

    private SocketManager() {
    }

    public static Integer BEGIN_URL = 0;
    public static Integer END_URL = 0;

    public static Vector<Socket> sockets = new Vector<>();

    public static void initManager() {
        sockets = new Vector<>();
    }

    public static void close() {
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
