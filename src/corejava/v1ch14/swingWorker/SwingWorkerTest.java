package corejava.v1ch14.swingWorker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * В этой программе демонстрируется рабочий поток, в котором
 * выполняется потенциально продолжительная задача
 * @version 1.11 2015-06-21
 * @author Cay Horstmann
 */
public class SwingWorkerTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SwingWorkerFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит текстовую область для отображения
 * содержимого текстового файла, меню для открытия файла и
 * отмены его открытия, а также строку состояния для отображения
 * процесса загрузки файла
 */
class SwingWorkerFrame extends JFrame {
    private JFileChooser chooser;
    private JTextArea textArea;
    private JLabel statusLine;
    private JMenuItem openItem;
    private JMenuItem cancelItem;
    private SwingWorker<StringBuilder, ProgressData> textReader;
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    public SwingWorkerFrame() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        statusLine = new JLabel(" ");
        add(statusLine, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(e -> {
            // показать диалоговое окно для выбора файлов
            int result = chooser.showOpenDialog(null);

            // если файл выбран, задать его в качестве
            // пиктограммы для метки
            if (result == JFileChooser.APPROVE_OPTION) {
                textArea.setText("");
                openItem.setEnabled(false);
                textReader = new TextReader(chooser.getSelectedFile());
                textReader.execute();
                cancelItem.setEnabled(true);
            }
        });

        cancelItem = new JMenuItem("Cancel");
        menu.add(cancelItem);
        cancelItem.setEnabled(false);
        cancelItem.addActionListener(e -> textReader.cancel(true));

        pack();
    }


    private class ProgressData {
        public int number;
        public String line;
    }

    private class TextReader extends SwingWorker<StringBuilder, ProgressData> {
        private File file;
        private StringBuilder text = new StringBuilder();

        public TextReader(File file) {
            this.file = file;
        }

        // Следующий метод выполняется в рабочем потоке,
        // не затрагивая компоненты Swing
        @Override
        public StringBuilder doInBackground() throws IOException, InterruptedException {
            int lineNumber = 0;
            try (Scanner in = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8)) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    lineNumber++;
                    text.append(line).append("\n");
                    ProgressData data = new ProgressData();
                    data.number = lineNumber;
                    data.line = line;
                    publish(data);
                    // только для проверки отмены;
                    // а в конкретных программах не требуется
                    Thread.sleep(1);
                }
            }
            return text;
        }

        // Следующие методы выполняются в потоке
        // диспетчеризации событий
        @Override
        protected void process(List<ProgressData> data) {
            if (isCancelled()) return;
            StringBuilder b = new StringBuilder();
            statusLine.setText("" + data.get(data.size() - 1).number);
            for (ProgressData d : data) b.append(d.line).append("\n");
            textArea.append(b.toString());
        }

        @Override
        protected void done() {
            try {
                StringBuilder result = get();
                textArea.setText(result.toString());
                statusLine.setText("Done");
            } catch (InterruptedException ignored) {
            } catch (CancellationException e) {
                textArea.setText("");
                statusLine.setText("Cancelled");
            } catch (ExecutionException e) {
                statusLine.setText("" + e.getCause());
            }

            cancelItem.setEnabled(false);
            openItem.setEnabled(true);
        }
    }
}