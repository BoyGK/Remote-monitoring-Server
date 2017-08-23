package ztest;

public class RTMServer{

    public static void main(String[] args) throws InterruptedException {


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(123);
        }));

        Thread.sleep(5000);
    }

}
