package corejava.v1ch12.fileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Фрейм с меню для загрузки файла изображения
 * и областью его воспроизведения
 */
public class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
    private JLabel label;
    private JFileChooser chooser;

    public ImageViewerFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // установить строку меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(e -> {
            chooser.setCurrentDirectory(new File("."));

            // показать диалоговое окно для выбора файлов
            int result = chooser.showOpenDialog(ImageViewerFrame.this);

            // если файл изображения подходит, выбрать его
            // в качестве пиктограммы для метки
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(name));
                pack();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));

        // использовать метку для показа изображений
        label = new JLabel();
        add(label);

        // установить диалоговое окно для выбора файлов
        chooser = new JFileChooser();

        // принимать все файлы с расширением .jpg, .jpeg, .gif
        FileFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "gif");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false); // подавляем фильтр All files

        chooser.setAccessory(new ImagePreviewer(chooser));

        chooser.setFileView(new FileIconView(filter, new ImageIcon("src/corejava/v1ch12/palette.gif")));
    }
}