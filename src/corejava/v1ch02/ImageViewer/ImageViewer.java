package corejava.v1ch02.ImageViewer;

import javax.swing.*;
import java.io.File;

/**
 * Программа для просмотра изображений
 * @version 1.30 2014-02-27
 * @author Cay Horstmann
 */
public class ImageViewer {
    public static void main(String[] args) {
        JFrame frame = new ImageViewerFrame();
        frame.setTitle("ImageViewer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
 * Фрейм с текстовой меткой для вывода изображения
 */
class ImageViewerFrame extends JFrame {
    private JLabel label;
    private JFileChooser chooser;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    public ImageViewerFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // использовать метку для вывода изображений на экране
        label = new JLabel();
        add(label);

        // установить селектор файлов
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // установить строку меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event -> {
            // отобразить диалоговое окно селектора файлов
            int result = chooser.showOpenDialog(null);

            // если файл выбран, задать его в качестве
            // пиктограммы для метки
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(name));
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
    }
}
