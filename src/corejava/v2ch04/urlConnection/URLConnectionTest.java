package corejava.v2ch04.urlConnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * В этой программе устанавливается соединение по заданному URL и
 * отображаются данные заголовка из получаемого ответа, а также
 * первые 10 строк запрашиваемых данных. Для этого в командной
 * строке следует указать конкретный URL и дополнительно имя
 * пользователя и пароль (для элементарной аутентификации по
 * сетевому протоколу HTTP)
 * @version 1.11 2007-06-26
 * @author Cay Horstmann
 */
public class URLConnectionTest {
    public static void main(String[] args) {
        try {
            String urlName;
            if (args.length > 0) urlName = args[0];
            else urlName = "https://horstmann.com";

            URL url = new URL(urlName);
            URLConnection connection = url.openConnection();

            // установить имя пользователя и пароль, если они
            // указаны в командной строке
            if (args.length > 2) {
                String username = args[1];
                String password = args[2];
                String input = username + ":" + password;
                Base64.Encoder encoder = Base64.getEncoder();
                String encoding = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
                connection.setRequestProperty("Authorization", "Basic " + encoding);
            }

            connection.connect();

            // вывести поля заголовка

            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue())
                    System.out.println(key + ": " + value);
            }

            // вывести значения полей заголовка,
            // используя удобные методы

            System.out.println("----------");
            System.out.println("getContentType(): " + connection.getContentType());
            System.out.println("getContentLength(): " + connection.getContentLength());
            System.out.println("getContentEncoding(): " + connection.getContentEncoding());
            System.out.println("getDate(): " + connection.getDate());
            System.out.println("getExpiration(): " + connection.getExpiration());
            System.out.println("getLastModified(): " + connection.getLastModified());
            System.out.println("----------");

            String encoding = connection.getContentEncoding();
            if (encoding == null) encoding = "UTF-8";
            try (Scanner in = new Scanner(connection.getInputStream(), encoding)) {
                // вывести первые десять строк запрашиваемого содержимого

                for (int n = 0; in.hasNextLine() && n <= 10; n++)
                    System.out.println(in.nextLine());
                if (in.hasNextLine()) System.out.println(". . .");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}