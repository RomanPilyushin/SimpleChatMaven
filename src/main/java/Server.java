import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements Runnable{
    private ArrayList<Socket> clients = new ArrayList<>();
    private static Gson gson = new Gson();
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket accept = serverSocket.accept();
                System.out.println("Connected new user: " + accept.getInetAddress());
                synchronized (this) { clients.add(accept); }
                new ServerThread(accept).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendAll(Msg msg) throws IOException {
        for (Socket client : this.clients) {
            OutputStream outputStream = client.getOutputStream();
            String toJson = gson.toJson(msg) + "\n";
            outputStream.write(toJson.getBytes());
        }
    }

    private synchronized void remove(Socket s) {
        try {
            s.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients.remove(s);

        try {
            sendAll(new Msg("User leaved", "SERVER", LocalDateTime.now()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread extends Thread {
        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                scanner.useDelimiter("[\n]");
                while (scanner.hasNext()) {
                    String msg = scanner.next();
                    Msg fromJson = gson.fromJson(msg, Msg.class);
                    if (fromJson.getText().equals("-exit")) {
                        remove(socket);
                        return;
                    }
                    sendAll(fromJson);
                    System.out.println(fromJson);
                }
            } catch (IOException e) {
                remove(socket);
            }
        }
    }
}
