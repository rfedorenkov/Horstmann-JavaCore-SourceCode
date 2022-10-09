package corejava.v2ch04.inetAddress;

import java.io.IOException;
import java.net.InetAddress;

/**
 * В этой программе демонстрируется применение класса InetAddress.
 * В качестве аргумента в командной строке следует указать имя хоста
 * или запустить программу без аргументов, чтобы получить
 * в ответ адрес локального хоста
 * @version 1.02 2012-06-05
 * @author Cay Horstmann
 */
public class InetAddressTest {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress a : addresses)
                System.out.println(a);
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
    }
}