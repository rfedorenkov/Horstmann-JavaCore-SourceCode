package corejava.v1ch12.calculator;

import javax.swing.*;

/**
 * Фрейм с панелью калькулятора
 */
public class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        add(new CalculatorPanel());
        pack();
    }
}