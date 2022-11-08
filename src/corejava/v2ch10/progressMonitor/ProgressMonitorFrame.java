package corejava.v2ch10.progressMonitor;

import javax.swing.*;
import java.awt.*;

/**
 * Фрейм, содержащий кнопку для запуска имитируемого процесса и
 * текстовое поле для вывода результатов выполнения этого процесса
 */
public class ProgressMonitorFrame extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 40;

    private Timer cancelMonitor;
    private JButton startButton;
    private ProgressMonitor progressDialog;
    private JTextArea textArea;
    private SimulatedActivity activity;

    public ProgressMonitorFrame() {
        // в этой текстовой области выводятся результаты
        // выполнения имитируемого процесса
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);

        // установить панель с кнопками
        JPanel panel = new JPanel();
        startButton = new JButton("Start");
        panel.add(startButton);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // установить приемник действий кнопки

        startButton.addActionListener(event -> {
            startButton.setEnabled(false);
            final int MAX = 1000;

            // начать имитируемый процесс
            activity = new SimulatedActivity(MAX);
            activity.execute();

            // открыть диалоговое окно монитора текущего состояния
            progressDialog = new ProgressMonitor(ProgressMonitorFrame.this,
                    "Waiting for Simulated Activity", null, 0, MAX);
            cancelMonitor.start();
        });

        // установить приемник действий таймера

        cancelMonitor = new Timer(500, event -> {
            if (progressDialog.isCanceled()) {
                activity.cancel(true);
                startButton.setEnabled(true);
            } else if (activity.isDone()) {
                progressDialog.close();
                startButton.setEnabled(true);
            } else {
                progressDialog.setProgress(activity.getProgress());
            }
        });
        pack();
    }

    class SimulatedActivity extends SwingWorker<Void, Integer> {
        private int current;
        private int target;

        /**
         * Имитирует процесс инкрементироавния счетчика от нуля
         * до заданного конечного значения
         * @param t Конечное значение счетчика
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
                    textArea.append(current + "\n");
                    setProgress(current);
                }
            } catch (InterruptedException ignored) {
            }
            return null;
        }
    }
}