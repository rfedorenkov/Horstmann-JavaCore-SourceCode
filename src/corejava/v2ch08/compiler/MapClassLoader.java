package corejava.v2ch08.compiler;

import java.util.Map;

/**
 * Загрузчик классов, загружающий классы из отображения,
 * где в качестве ключей служат имена классов, а
 * соответствующих им значений - массивы байт-кодов
 * @version 1.00 2007-11-02
 * @author Cay Horstmann
 */
public class MapClassLoader extends ClassLoader {
    private Map<String, byte[]> classes;

    public MapClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = classes.get(name);
        if (classes == null) throw new ClassNotFoundException(name);
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) throw new ClassNotFoundException(name);
        return cl;
    }
}