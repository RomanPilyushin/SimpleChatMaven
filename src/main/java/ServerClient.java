/**
 * Created by User on 11/16/2016.
 */
public class ServerClient {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(6789);
        server.run();

        while (true){
            Thread.sleep(1_000);
        }
    }
}
