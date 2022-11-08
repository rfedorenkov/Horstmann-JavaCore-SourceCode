package corejava.v2ch10.textChange;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Фрейм с тремя текстовыми полями для установки цвета фона
 */
public class ColorFrame extends JFrame {
    private JPanel panel;
    private JTextField redField;
    private JTextField greenField;
    private JTextField blueField;

    public ColorFrame() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        panel = new JPanel();

        panel.add(new JLabel("Red:"));
        redField = new JTextField("255", 3);
        panel.add(redField);
        redField.getDocument().addDocumentListener(listener);

        panel.add(new JLabel("Green:"));
        greenField = new JTextField("255", 3);
        panel.add(greenField);
        greenField.getDocument().addDocumentListener(listener);

        panel.add(new JLabel("Blue:"));
        blueField = new JTextField("255", 3);
        panel.add(blueField);
        blueField.getDocument().addDocumentListener(listener);

        add(panel);
        pack();
    }

    /**
     * Установить цвет фона в соответствии со значениями,
     * хранящимися в текстовых полях
     */
    public void setColor() {
        try {
            int red = Integer.parseInt(redField.getText().trim());
            int green = Integer.parseInt(greenField.getText().trim());
            int blue = Integer.parseInt(blueField.getText().trim());
            panel.setBackground(new Color(red, green, blue));
        } catch (NumberFormatException ignored) {
            // не устанавливать цвет, если введенные данные
            // нельзя проанализировать синтаксически
        }
    }
}