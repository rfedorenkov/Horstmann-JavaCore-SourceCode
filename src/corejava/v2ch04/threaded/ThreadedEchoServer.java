package corejava.v2ch04.threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * В этой программе реализуется многопоточный сервер,
 * прослушивающий порт 8189 и передающий обратно все данные,
 * полученные от клиентов
 * @author Cay Horstmann
 * @version 1.22 2016-04-27
 */
public class ThreadedEchoServer {
    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8189)) {
            int i = 1;

            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Этот класс обрабатывает данные, получаемые сервером от
 * клиента через одно сокетное соединение
 */
class ThreadedEchoHandler implements Runnable {
    private Socket incoming;

    /**
     * Конструирует обработчик
     * @param incomingSocket Входящий сокет
     */
    public ThreadedEchoHandler(Socket incomingSocket) {
        incoming = incomingSocket;
    }

    @Override
    public void run() {
        try (InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream()) {
            Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                    true); // автоматическая очистка

            out.println("Hello! Enter BYE to exit.");

            // передать обратно данные, полученные от клиента
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}