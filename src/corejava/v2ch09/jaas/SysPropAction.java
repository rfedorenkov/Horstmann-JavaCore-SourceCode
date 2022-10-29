package corejava.v2ch09.jaas;

import java.security.*;

/**
 * Это действие осуществляет поиск системного свойства
 * @author Cay Horstmann
 * @version 1.01 2007-10-06
 */
public class SysPropAction implements PrivilegedAction<String> {
    private String propertyName;

    /**
     * Конструирует действие для поиска заданного свойства
     * @param propertyName Имя свойства (например, "user.home")
     */
    public SysPropAction(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String run() {
        return System.getProperty(propertyName);
    }
}