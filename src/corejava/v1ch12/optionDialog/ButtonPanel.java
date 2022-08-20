package corejava.v1ch12.optionDialog;

import javax.swing.*;

/**
 * Панель с кнопками-переключателями, заключенная
 * в рамку с заголовком
 */
public class ButtonPanel extends JPanel {
    private ButtonGroup group;

    /**
     * Конструирует панель кнопок
     * @param title Заголовок, отображаемый в рамке
     * @param options Массив меток для кнопок-переключателей
     */
    public ButtonPanel(String title, String... options) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        group = new ButtonGroup();
        // создать по одной кнопке-переключателю
        // на каждый вариант выбора
        for (String option : options) {
            JRadioButton b = new JRadioButton(option);
            b.setActionCommand(option);
            add(b);
            group.add(b);
            b.setSelected(option == options[0]);
        }
    }

    /**
     * Получает выбранный в настоящий момент вариант
     * @return Метка выбранной в настоящий момент
     *         кнопки-переключателя
     */
    public String getSelection() {
        return group.getSelection().getActionCommand();
    }
}