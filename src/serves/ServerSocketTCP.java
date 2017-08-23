package serves;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ServerSocketTCP extends Thread{

    private Socket socket;
    private ServerImageListener imageListener;

    private boolean RUN_STATE=true;

    public ServerSocketTCP(Socket socket, ServerImageListener imageListener) {
        this.socket = socket;
        this.imageListener = imageListener;
    }

    @Override
    public void run() {
        while (RUN_STATE) {
            try {
                InputStream is = socket.getInputStream();
                byte bytes[] = new byte[1024];
                int len = is.read(bytes);
                int all = new Integer(new String(bytes, 0, len));
                //System.out.println("from client:" + all);
                byte bs[] = new byte[all];
                int l = 0;
                while (l < all) {
                    byte mbs[] = new byte[1024];
                    int to = is.read(mbs);
                    System.arraycopy(mbs, 0, bs, l, to);
                    l += to;
                }
                //System.out.println("server add:" + l);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bs);
                BufferedImage image = ImageIO.read(inputStream);
                imageListener.imageInfo(image);
            } catch (Exception e) {
                if(socket!=null) {
                    if (!socket.isConnected()) {
                        close();
                        System.out.println("客户机已关闭");
                    }
                }else{
                    close();
                    System.out.println("客户机已关闭");
                }
                //e.printStackTrace();
            }
        }
    }

    public void close(){
        this.RUN_STATE=false;
    }

    public interface ServerImageListener{
        void imageInfo(BufferedImage image);
    }
}
