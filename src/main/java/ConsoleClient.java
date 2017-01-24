/**
 * Created by User on 11/16/2016.
 */
public class ConsoleClient {
    public static void main(String[] args) {
        Client client = new Client(6789, "localhost");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
