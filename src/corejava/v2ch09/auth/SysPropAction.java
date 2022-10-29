package corejava.v2ch09.auth;

import java.security.PrivilegedAction;

/**
 * Это действие осуществляет поиск системного свойства
 * @version 1.01 2007-10-06
 * @author Cay Horstmann
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