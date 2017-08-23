package serves;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerSocketThread extends Thread {

    private final int PORT = 8080;

    private boolean RUN_STATE = true;

    private SocketManagerListener listener;

    public ServerSocketThread(SocketManagerListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (RUN_STATE) {
                boolean hasSocket = false;
                socket = serverSocket.accept();
                if (!SocketManager.BEGIN_URL.equals(SocketManager.END_URL)) {
                    int nip = s2i(socket.getInetAddress().getHostAddress());
                    if (nip >= SocketManager.BEGIN_URL && nip <= SocketManager.END_URL) {
                        start_(hasSocket, socket);
                    } else {
                        socket.close();
                    }
                } else {
                    start_(hasSocket, socket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.RUN_STATE = false;
    }

    public interface SocketManagerListener {
        void socketInfo(Vector<Socket> sockets);
    }

    private int s2i(String ip) {
        String[] s = ip.split("\\.");
        return Integer.parseInt(s[s.length - 1]);
    }

    private void start_(boolean hasSocket, Socket socket) {
        for (int i = 0; i < SocketManager.sockets.size(); i++) {
            if (SocketManager.sockets.get(i) != null && socket.getInetAddress().getHostAddress().equals(
                    SocketManager.sockets.get(i).getInetAddress().getHostAddress())) {
                SocketManager.sockets.set(i, socket);
                hasSocket = true;
                break;
            }
        }
        if (!hasSocket) {
            SocketManager.sockets.add(socket);
            listener.socketInfo(SocketManager.sockets);
        }
    }
}
