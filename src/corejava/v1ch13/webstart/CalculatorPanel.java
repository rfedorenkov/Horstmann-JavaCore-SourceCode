package corejava.v1ch13.webstart;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Панель с кнопками калькулятора и отображением результатов.
*/
public class CalculatorPanel extends JPanel {
    private JTextArea display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;

    public CalculatorPanel() {
        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start = true;

        // добавить отображение

        display = new JTextArea(10, 20);

        add(new JScrollPane(display), BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        // add the buttons in a 4 x 4 grid

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0", insert);
        addButton(".", command);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Получает текст истории.
     * @return История калькулятора
     */
    public String getText() {
        return display.getText();
    }


    /**
     * Добавляет строку к тексту истории.
     * @param s Строка для добавления
     */
    public void append(String s) {
        display.append(s);
    }

    /**
     * Добавляет кнопку на центральную панель.
     * @param label Метка кнопки
     * @param listener Слушатель кнопки
     */
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * Это действие вставляет строку действия
     * кнопки в конец отображаемого текста.
     */
    private class InsertAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            start = false;
            display.append(input);
        }
    }

    /**
     * Это действие выполняет команду,
     * которую обозначает строка действия кнопки.
     */
    private class CommandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (start) {
                if (command.equals("-")) {
                    display.append(command);
                    start = false;
                } else
                    lastCommand = command;
            } else {
                try {
                    int lines = display.getLineCount();
                    int lineStart = display.getLineStartOffset(lines - 1);
                    int lineEnd = display.getLineEndOffset(lines - 1);
                    String value = display.getText(lineStart, lineEnd - lineStart);
                    display.append(" ");
                    display.append(command);
                    calculate(Double.parseDouble(value));
                    if (command.equals("="))
                        display.append("\n" + result);
                    lastCommand = command;
                    display.append("\n");
                    start = true;
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Выполняет ожидаемый расчет.
     * @param x Значение, которое будет накапливаться с предыдущим результатом.
     */
    public void calculate(double x) {
        if (lastCommand.equals("+")) result += x;
        else if (lastCommand.equals("-")) result -= x;
        else if (lastCommand.equals("*")) result *= x;
        else if (lastCommand.equals("/")) result /= x;
        else if (lastCommand.equals("=")) result = x;
    }
}