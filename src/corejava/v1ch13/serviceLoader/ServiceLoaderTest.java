package corejava.v1ch13.serviceLoader;

import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;

/**
 * @version 1.00 2015-06-14
 * @author Cay Horstmann
 */
public class ServiceLoaderTest {
    public static ServiceLoader<Cipher> ciphersLoader = ServiceLoader.load(Cipher.class);

    public static void main(String[] args) {
        Cipher ciph = getCipher(1);
        String message = "Meet me at the toga party.";
        byte[] bytes = ciph.encrypt(message.getBytes(), new byte[]{3});
        String encrypted = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(encrypted);
    }

    public static Cipher getCipher(int minStrength) {
        for (Cipher cipher : ciphersLoader)
            // Неявно вызывает метод cipherLoader.iterator()
            if (cipher.strength() >= minStrength) return cipher;
        return null;
    }
}