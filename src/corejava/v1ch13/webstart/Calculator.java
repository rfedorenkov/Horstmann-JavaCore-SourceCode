package corejava.v1ch13.webstart;

import javax.swing.*;

/**
 * Калькулятор с историей вычислений,
 * который можно развернуть как приложение Java Web Start.
 * @version 1.04 2015-06-12
 * @author Cay Horstmann
 */
public class Calculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}