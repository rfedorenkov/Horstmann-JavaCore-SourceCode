package corejava.v2ch09.hash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * В этой программе вычисляется сверстка сообщения из файла
 * @version 1.20 2012-06-16
 * @author Cay Horstmann
 */
public class Digest {

    /**
     * @param args args[0] - имя файла, args[1] - дополнительно
     *             алгоритм (SHA-1, SHA-256, или MD5)
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String algname = args.length >= 2 ? args[1] : "SHA-1";
        MessageDigest alg = MessageDigest.getInstance(algname);
        byte[] input = Files.readAllBytes(Paths.get(args[0]));
        byte[] hash = alg.digest(input);
        String d = "";
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            if (v < 16) d += "0";
            d += Integer.toString(v, 16).toUpperCase() + " ";
        }
        System.out.println(d);
    }
}