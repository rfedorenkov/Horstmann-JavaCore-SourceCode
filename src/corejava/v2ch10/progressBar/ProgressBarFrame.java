package corejava.v2ch10.progressBar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Фрейм, содержащий кнопку для запуска имитируемого процесса,
 * индикатор выполнения и текстовую область для вывода результатов
 */
public class ProgressBarFrame extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 40;

    private JButton startButton;
    private JProgressBar progressBar;
    private JCheckBox checkBox;
    private JTextArea textArea;
    private SimulatedActivity activity;

    public ProgressBarFrame() {
        // в этой текстовой области выводятся результаты
        // выполнения имитируемого процесса
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);

        // установить панель с кнопкой и индикатором выполнения

        final int MAX = 1000;
        JPanel panel = new JPanel();
        startButton = new JButton("Start");
        progressBar = new JProgressBar(0, MAX);
        progressBar.setStringPainted(true);
        panel.add(startButton);
        panel.add(progressBar);

        checkBox = new JCheckBox("indeterminate");
        checkBox.addActionListener(event -> {
            progressBar.setIndeterminate(checkBox.isSelected());
            progressBar.setStringPainted(!progressBar.isIndeterminate());
        });
        panel.add(checkBox);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // установить приемник действий кнопки

        startButton.addActionListener(event -> {
            startButton.setEnabled(false);
            activity = new SimulatedActivity(MAX);
            activity.execute();
        });
        pack();
    }

    class SimulatedActivity extends SwingWorker<Void, Integer> {
        private int current;
        private int target;

        /**
         * Имитирует процесс инкрементирования счетчика от нуля
         * до заданного конечного значения
         * @param t Конечное значение счетчика.
         */
        public SimulatedActivity(int t) {
            current = 0;
            target = t;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                while (current < target) {
                    Thread.sleep(100);
                    current++;
                    publish(current);
                }
            } catch (InterruptedException ignored) {
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            for (Integer chunk : chunks) {
                textArea.append(chunk + "\n");
                progressBar.setValue(chunk);
            }
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
        }
    }
}