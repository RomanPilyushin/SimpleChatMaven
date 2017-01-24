import com.google.gson.Gson;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Msg msg = new Msg("hello", "Roman", LocalDateTime.now());
        String json = gson.toJson(msg);
        System.out.println(json);
        Msg fromJson = gson.fromJson(json, Msg.class);
        System.out.println(fromJson);
        System.out.println(fromJson.equals(msg));
    }
}
