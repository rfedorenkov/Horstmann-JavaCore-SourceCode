package corejava.v1ch13.preferences;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.prefs.Preferences;

/**
 * В этой программе проверяются глобальные параметры настройки.
 * В ней запоминаются положение, размеры и заголовок фрейма
 * @version 1.03 2015-06-12
 * @author Cay Horstmann
 */
public class PreferencesTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PreferencesFrame frame = new PreferencesFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм, восстанавливающий свое положение и размеры и файла
 * свойств и обновляющий свойства после выхода из программы
 */
class PreferencesFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private Preferences root = Preferences.userRoot();
    private Preferences node = root.node("/com/horstmann/corejava");

    public PreferencesFrame() {
        // получить положение, размеры и заголовок фрейма из свойств

        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        setBounds(left, top, width, height);

        // если заголовок не задан, запросить его у пользователя

        String title = node.get("title", "");
        if (title.equals("")) title = JOptionPane.showInputDialog("Please supply a frame title:");
        if (title == null) title = "";
        setTitle(title);

        // установить компонент для выбора и отображения XML-файлов

        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));

        // установить меню

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem exportItem = new JMenuItem("Export preferences");
        menu.add(exportItem);
        exportItem.addActionListener(e -> {
            if (chooser.showSaveDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    savePreferences();
                    OutputStream out = new FileOutputStream(chooser.getSelectedFile());
                    node.exportSubtree(out);
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem importItem = new JMenuItem("Import preferences");
        menu.add(importItem);
        importItem.addActionListener(e -> {
            if (chooser.showOpenDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    InputStream in = new FileInputStream(chooser.getSelectedFile());
                    Preferences.importPreferences(in);
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(e -> {
            savePreferences();
            System.exit(0);
        });
    }

    public void savePreferences() {
        node.putInt("left", getX());
        node.putInt("top", getY());
        node.putInt("width", getWidth());
        node.putInt("height", getHeight());
        node.put("title", getTitle());
    }
}