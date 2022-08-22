package corejava.v1ch13.systemInfo;

import java.io.IOException;
import java.util.Properties;

/**
 * This program prints out all system properties.
 * @version 1.10 2002-07-06
 * @author Cay Horstmann
 */
public class SystemInfo {
    public static void main(String[] args) {
        Properties sysprops = System.getProperties();
        try {
            sysprops.store(System.out, "System Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}