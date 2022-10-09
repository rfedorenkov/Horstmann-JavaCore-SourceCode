package corejava.v2ch04.socket;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * В этой программе устанавливается сокетное соединение
 * с атомными часами в г. Боулдере, шт. Колорадо и выводится
 * время, передаваемое из сервера
 * @version 1.21 2016-04-27
 * @author Cay Horstmann
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
             Scanner in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}