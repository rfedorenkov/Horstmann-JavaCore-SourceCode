package corejava.v2ch09.classLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * В этой программе демонстрируется специальный загрузчик
 * классов, расшифровывающий файлы классов
 * @version 1.24 2016-05-10
 * @author Cay Horstmann
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ClassLoaderFrame();
            frame.setTitle("ClassLoaderTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит два текстовых поля для ввода имени
 * загружаемого класса и ключ расшифровки
 */
class ClassLoaderFrame extends JFrame {
    private JTextField keyField = new JTextField("3", 4);
    private JTextField nameField = new JTextField("Calculator", 30);
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ClassLoaderFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());
        add(new JLabel("Class"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(nameField, new GBC(1, 0).setWeight(100, 0).setAnchor(GBC.WEST));
        add(new JLabel("Key"), new GBC(0, 1).setAnchor(GBC.EAST));
        add(keyField, new GBC(1, 1).setWeight(100, 0).setAnchor(GBC.WEST));
        JButton loadButton = new JButton("Load");
        add(loadButton, new GBC(0, 2, 2, 1));
        loadButton.addActionListener(event -> runClass(nameField.getText(), keyField.getText()));
        pack();
    }

    /**
     * Выполняет основной метод указанного класса
     * @param name Имя класса
     * @param key Ключ расшифровки файлов класса
     */
    public void runClass(String name, String key) {
        try {
            ClassLoader loader = new CryptoClassLoader(Integer.parseInt(key));
            Class<?> c = loader.loadClass(name);
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, (Object) new String[] {} );
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
}

/**
 * Этот загрузчик классов загружает их из зашифрованных файлов
 */
class CryptoClassLoader extends ClassLoader {
    private int key;

    /**
     * Конструирует загрузчик зашифрованных классов
     * @param k Ключ расшифровки
     */
    public CryptoClassLoader(int k) {
        key = k;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classBytes = null;
            classBytes = loadClassBytes(name);
            Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
            if (cl == null) throw new ClassNotFoundException(name);
            return cl;
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }

    /**
     * Загружает и расшифровывает байты из файла класса
     * @param name Имя класса
     * @return Возвращает массив с байтами из файла класса
     */
    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace('.', '/') + ".caesar";
        byte[] bytes = Files.readAllBytes(Paths.get(cname));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] - key);
        }
        return bytes;
    }
}