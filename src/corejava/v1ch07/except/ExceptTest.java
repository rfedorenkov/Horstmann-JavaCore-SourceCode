package corejava.v1ch07.except;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @version 1.34 2015-08-20
 * @author Cay Horstmann
 */
public class ExceptTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ExceptTestFrame();
            frame.setTitle("ExceptTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Рамка с панелью для тестирования различных исключений
 */
class ExceptTestFrame extends JFrame {
    public ExceptTestFrame() {
        ExceptTestPanel panel = new ExceptTestPanel();
        add(panel);
        pack();
    }
}

/**
 * Панель с переключателями для запуска фрагментов кода
 * и изучения их поведения при исключении.
 */
class ExceptTestPanel extends Box {
    private ButtonGroup group;
    private JTextField textField;
    private double[] a = new double[10];

    public ExceptTestPanel() {
        super(BoxLayout.Y_AXIS);
        group = new ButtonGroup();

        // добавить переключатели для фрагментов кода

        addRadioButton("Integer divide by zero", e -> a[1] = 1 / (a.length - a.length));

        addRadioButton("Floating point divide by zero", e -> a[1] = a[2] / (a[3] - a[3]));

        addRadioButton("Array bounds", e -> a[1] = a[10]);

        addRadioButton("Bad cast", e -> a = (double[]) e.getSource());

        addRadioButton("Null pointer", e -> System.out.println(textField.getAction().toString()));

        addRadioButton("sqrt(-1)", e -> a[1] = Math.sqrt(-1));

        addRadioButton("Overflow", e -> {
            a[1] = 1000 * 1000 * 1000 * 1000;
            int n = (int) a[1];
            System.out.println(n);
        });

        addRadioButton("No such file", e -> {
            try {
                System.out.println(new Scanner(Paths.get("woozle.txt"), StandardCharsets.UTF_8).next());
            } catch (IOException ex) {
                textField.setText(ex.toString());
            }
        });

        addRadioButton("Throw unknown", e -> {
            throw new UnknownError();
        });

        // добавить текстовое поле для отображения исключений
        textField = new JTextField(30);
        add(textField);
    }

    /**
     * Добавляет на панель радиокнопку с заданным слушателем.
     * Перехватывает любые исключения в методе actionPerformed слушателя.
     * @param s Метка переключателя
     * @param listener Слушатель действий для переключателя
     */
    private void addRadioButton(String s, ActionListener listener) {
        JRadioButton button = new JRadioButton(s, false) {
            // кнопка вызывает этот метод для запуска события действия.
            // мы переопределяем его, чтобы перехватывать исключения
            @Override
            protected void fireActionPerformed(ActionEvent event) {
                try {
                    textField.setText("No exception");
                    super.fireActionPerformed(event);
                } catch (Exception e) {
                    textField.setText(e.toString());
                }
            }
        };

        button.addActionListener(listener);
        add(button);
        group.add(button);
    }
}