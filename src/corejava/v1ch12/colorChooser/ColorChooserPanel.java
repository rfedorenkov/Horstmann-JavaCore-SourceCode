package corejava.v1ch12.colorChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Панель с кнопками для открытия трех видов
 * диалоговых окон для выбора цвета
 */
public class ColorChooserPanel extends JPanel {

    public ColorChooserPanel() {
        JButton modalButton = new JButton("Modal");
        modalButton.addActionListener(new ModalListener());
        add(modalButton);

        JButton modelessButton = new JButton("Modeless");
        modelessButton.addActionListener(new ModelessListener());
        add(modelessButton);

        JButton immediateButton = new JButton("Immediate");
        immediateButton.addActionListener(new ImmediateListener());
        add(immediateButton);
    }

    /**
     * Этот приемник событий открывает модальное диалоговое
     * окно для выбора цвета
     */
    private class ModalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Color defaultColor = getBackground();
            Color selected = JColorChooser.showDialog(
                    ColorChooserPanel.this, "Set background",
                    defaultColor);
            if (selected != null) setBackground(selected);
        }
    }

    /**
     * Этот приемник событий открывает немодальное диалоговое окно
     * для выбора цвета. Цвет фона панели изменится, как только
     * пользователь щелкнет на кнопке OK
     */
    private class ModelessListener implements ActionListener {
        private JDialog dialog;
        private JColorChooser chooser;

        public ModelessListener() {
            chooser = new JColorChooser();
            dialog = JColorChooser.createDialog(ColorChooserPanel.this,
                    "Background Color",
                    false /* не модальное окно */, chooser,
                    e -> setBackground(chooser.getColor()),
                    null /* приемник событий от кнопки Cancel не требуется */);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }

    /**
     * Этот приемник событий открывает немодальное диалоговое окно
     * для выбора цвета. Цвет фона панели изменится, как только
     * пользователь выберет новый цвет, не закрывая окно
     */
    private class ImmediateListener implements ActionListener {
        private JDialog dialog;
        private JColorChooser chooser;

        public ImmediateListener() {
            chooser = new JColorChooser();
            chooser.getSelectionModel().addChangeListener(e -> setBackground(chooser.getColor()));

            dialog = new JDialog((Frame) null, false /* не модальное окно */);
            dialog.add(chooser);
            dialog.pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(getBackground());
            dialog.setVisible(true);
        }
    }
}