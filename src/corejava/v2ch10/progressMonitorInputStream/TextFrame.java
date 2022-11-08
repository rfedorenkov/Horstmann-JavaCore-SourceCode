package corejava.v2ch10.progressMonitorInputStream;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Фрейм с меню для загрузки текстового файла и текстовой
 * областью для отображения его содержимого. Эта область
 * создается при загрузке файла и устанавливается в виде
 * панели содержимого фрейма по завершении загрузки во избежание
 * мерцания в ходе данного процесса.
 */
public class TextFrame extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 40;

    private JMenuItem openItem;
    private JMenuItem exitItem;
    private JTextArea textArea;
    private JFileChooser chooser;

    public TextFrame() {
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        openItem = new JMenuItem("Open");
        openItem.addActionListener(event -> {
            try {
                openFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        fileMenu.add(openItem);
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);
        pack();
    }

    /**
     * Приглашает пользователя выбрать файл, загружает его
     * в текстовую область и устанавливает ее в виде панели
     * содержимого фрейма
     */
    public void openFile() throws IOException {
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) return;
        final File f = chooser.getSelectedFile();

        // установить последовательность потоков ввода и
        // фильтрации читаемых данных

        InputStream fileIn = Files.newInputStream(f.toPath());
        final ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(
                this, "Reading " + f.getName(), fileIn);

        textArea.setText("");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws IOException {
                try (Scanner in = new Scanner(progressIn, StandardCharsets.UTF_8)) {
                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        textArea.append(line);
                        textArea.append("\n");
                    }
                }
                return null;
            }
        };
        worker.execute();
    }
}