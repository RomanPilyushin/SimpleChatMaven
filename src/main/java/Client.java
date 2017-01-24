import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {
    private int port;
    private String host;

    public Client(int port, String host){
        this.port = port;
        this.host = host;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Gson gson = new Gson();
        Msg[] msgs = new Msg[5];

        for (int i = 0; i < msgs.length; i++) {
            msgs[i] = new Msg("hello " + i, "Sender", LocalDateTime.now());
            Thread.sleep(100);
        }

        Socket socket = new Socket("localhost", 6789);
        OutputStream outputStream = socket.getOutputStream();

        for (int i = 0; i < msgs.length; i++) {
            String toJson = gson.toJson(msgs[i]) + "\n";
            outputStream.write(toJson.getBytes());
        }

        Msg closeConnect = new Msg("-exit", "User777", LocalDateTime.now());
        String toJson = gson.toJson(closeConnect) + "\n";
        outputStream.write(toJson.getBytes());

        Thread.sleep(5000);

        Scanner scanner = new Scanner(socket.getInputStream());
        scanner.useDelimiter("[\n]");
        while (scanner.hasNext()) {
            String msg = scanner.next();
            Msg fromJson = gson.fromJson(msg, Msg.class);
            System.out.printf("[%s] %s: %s\n",
                    fromJson.getDateTime().format(DateTimeFormatter.ISO_TIME),
                    fromJson.getSender(),
                    fromJson.getText());
            //System.out.println(fromJson);
        }
        socket.close();
    }
}
