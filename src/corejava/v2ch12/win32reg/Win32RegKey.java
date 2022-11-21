package corejava.v2ch12.win32reg;

import java.util.Enumeration;

/**
 * Объект класса Win32RegKey служит для получения и
 * установки значений в ключах реестра Windows
 * A Win32RegKey object can be used to get and set values of a registry key in the Windows registry.
 * @version 1.00 1997-07-01
 * @author Cay Horstmann
 */
public class Win32RegKey {
    public static final int HKEY_CLASSES_ROOT = 0x80000000;
    public static final int HKEY_CURRENT_USER = 0x80000001;
    public static final int HKEY_LOCAL_MACHINE = 0x80000002;
    public static final int HKEY_USERS = 0x80000003;
    public static final int HKEY_CURRENT_CONFIG = 0x80000005;
    public static final int HKEY_DYN_DATA = 0x80000006;

    private int root;
    private String path;

    /**
     * Получает значение ключа в реестра
     * @param name Имя ключа
     * @return Возвращает значение, связанное с заданным ключом
     */
    public native Object getValue(String name);

    /**
     * Устанавливает значение ключа в реестре
     * @param name Имя ключа
     * @param value Новое значение
     */
    public native void setValue(String name, Object value);

    /**
     * Создает объект ключа в реестре
     * @param theRoot Один из следующих ключей:
     *                HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, HKEY_LOCAL_MACHINE,
     *                HKEY_USERS, HKEY_CURRENT_CONFIG, HKEY_DYN_DATA
     * @param thePath Путь к ключу в реестре
     */
    public Win32RegKey(int theRoot, String thePath) {
        root = theRoot;
        path = thePath;
    }

    /**
     * Перечисляет все имена ключей в реестра по пути,
     * описываемому в данном объекте
     * @return Возвращает список всех перечисляемых имен
     */
    public Enumeration<String> names() {
        return new Win32RegKeyNameEnumeration(root, path);
    }

    static {
        System.loadLibrary("Win32RegKey");
    }
}

class Win32RegKeyNameEnumeration implements Enumeration<String> {
    @Override
    public native String nextElement();
    @Override
    public native boolean hasMoreElements();
    private int root;
    private String path;
    private int index = -1;
    private int hkey = 0;
    private int maxsize;
    private int count;

    Win32RegKeyNameEnumeration(int theRoot, String thePath) {
        root = theRoot;
        path = thePath;
    }
}

class Win32RegKeyException extends RuntimeException {
    public Win32RegKeyException() {
    }

    public Win32RegKeyException(String why) {
        super(why);
    }
}