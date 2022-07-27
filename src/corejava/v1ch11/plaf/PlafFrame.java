package corejava.v1ch11.plaf;

import javax.swing.*;

/**
 * Фрейм с панелью экранных кнопок для смены визуального стиля
 */
public class PlafFrame extends JFrame {
    private JPanel buttonPanel;

    public PlafFrame() {
        buttonPanel = new JPanel();

        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos)
            makeButton(info.getName(), info.getClassName());

        add(buttonPanel);
        pack();
    }

    /**
    * Изменяет подключаемый стиль после щелчка на кнопке
    * @param name Имя кнопки
    * @param className Имя класса визуального стиля
    */
    private void makeButton(String name, String className) {
        // ввести кнопку на панели

        JButton button = new JButton(name);
        buttonPanel.add(button);

        // установить действие для кнопки
        button.addActionListener(event -> {
            // действие для кнопки: сменить визуальный стиль на новый
            try {
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
                pack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}