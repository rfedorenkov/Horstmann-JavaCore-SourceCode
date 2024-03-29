package corejava.v1ch13.properties;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * В этой программе проверяются свойства.
 * В ней запоминаются положение, размеры и заголовок фрейма
 * @version 1.01 2015-06-16
 * @author Cay Horstmann
 */
public class PropertiesTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PropertiesFrame frame = new PropertiesFrame();
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм, восстанавливающий свое положение и размеры и файла
 * свойств и обновляющий свойства после выхода из программы
 */
class PropertiesFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private File propertiesFile;
    private Properties settings;

    public PropertiesFrame() {
        // получить положение, размеры и заголовок фрейма из свойств

        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, ".corejava");
        if (!propertiesDir.exists()) propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "program.properties");

        Properties defaultSettings = new Properties();
        defaultSettings.setProperty("left", "0");
        defaultSettings.setProperty("top", "0");
        defaultSettings.setProperty("width", "" + DEFAULT_WIDTH);
        defaultSettings.setProperty("height", "" + DEFAULT_HEIGHT);
        defaultSettings.setProperty("title", "");

        settings = new Properties(defaultSettings);

        if (propertiesFile.exists()) {
            try (InputStream in = new FileInputStream(propertiesFile)) {
                settings.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int left = Integer.parseInt(settings.getProperty("left"));
        int top = Integer.parseInt(settings.getProperty("top"));
        int width = Integer.parseInt(settings.getProperty("width"));
        int height = Integer.parseInt(settings.getProperty("height"));
        setBounds(left, top, width, height);

        // если заголовок не задан, запросить его у пользователя

        String title = settings.getProperty("title");
        if (title.equals(""))
            title = JOptionPane.showInputDialog("Please supply a frame title:");
        if (title == null) title = "";
        setTitle(title);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                settings.setProperty("left", "" + getX());
                settings.setProperty("top", "" + getY());
                settings.setProperty("width", "" + getWidth());
                settings.setProperty("height", "" + getHeight());
                settings.setProperty("title", "" + getTitle());

                try (OutputStream out = new FileOutputStream(propertiesFile)) {
                    settings.store(out, "Program Properties");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}