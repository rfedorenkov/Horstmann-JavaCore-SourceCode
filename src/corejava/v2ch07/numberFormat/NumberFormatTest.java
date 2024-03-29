package corejava.v2ch07.numberFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * В этой программе демонстрируется форматирование чисел
 * при разных региональных настройках
 * @version 1.14 2016-05-06
 * @author Cay Horstmann
 */
public class NumberFormatTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new NumberFormatFrame();
            frame.setTitle("NumberFormatTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит кнопки-переключатели для выбора способа
 * форматирования чисел, комбинированный список для выбора
 * региональных настроек, текстовое поле для отображения
 * отформатированного числа, а также кнопку для активации
 * синтаксического анализа содержимого текстового поля
 */
class NumberFormatFrame extends JFrame {
    private Locale[] locales;
    private double currentNumber;
    private JComboBox<String> localeCombo = new JComboBox<>();
    private JButton parseButton = new JButton("Parse");
    private JTextField numberText = new JTextField(30);
    private JRadioButton numberRadioButton = new JRadioButton("Number");
    private JRadioButton currencyRadioButton = new JRadioButton("Currency");
    private JRadioButton percentRadioButton = new JRadioButton("Percent");
    private ButtonGroup rbGroup = new ButtonGroup();
    private NumberFormat currentNumberFormat;

    public NumberFormatFrame() {
        setLayout(new GridBagLayout());

        ActionListener listener = event -> updateDisplay();

        JPanel p = new JPanel();
        addRadioButton(p, numberRadioButton, rbGroup, listener);
        addRadioButton(p, currencyRadioButton, rbGroup, listener);
        addRadioButton(p, percentRadioButton, rbGroup, listener);

        add(new JLabel("Locale:"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(p, new GBC(1, 1));
        add(parseButton, new GBC(0, 2).setInsets(2));
        add(localeCombo, new GBC(1, 0).setAnchor(GBC.WEST));
        add(numberText, new GBC(1, 2).setFill(GBC.HORIZONTAL));
        locales = NumberFormat.getAvailableLocales().clone();
        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayName));
        for (Locale loc : locales)
            localeCombo.addItem(loc.getDisplayName());
        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
        currentNumber = 123456.78;
        updateDisplay();

        localeCombo.addActionListener(listener);

        parseButton.addActionListener(event -> {
            String s = numberText.getText().trim();
            try {
                Number n = currentNumberFormat.parse(s);
                if (n != null) {
                    currentNumber = n.doubleValue();
                    updateDisplay();
                } else {
                    numberText.setText("Parse error: " + s);
                }
            } catch (ParseException e) {
                numberText.setText("Parse error: " + s);
            }
        });
        pack();
    }

    /**
     * Вводит кнопки-переключатели в контейнер
     * @param p Контейнер для размещения кнопок-переключателей
     * @param b Кнопка-переключатель
     * @param g Группа кнопок-переключателей
     * @param listener Приемник событий от кнопок-переключателей
     */
    public void addRadioButton(JPanel p, JRadioButton b, ButtonGroup g, ActionListener listener) {
        b.setSelected(g.getButtonCount() == 0);
        b.addActionListener(listener);
        g.add(b);
        p.add(b);
    }

    /**
     * Обновляет отображаемое число и форматирует его
     * в соответствии с пользовательскими установками
     */
    public void updateDisplay() {
        Locale currentLocale = locales[localeCombo.getSelectedIndex()];
        currentNumberFormat = null;
        if (numberRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getNumberInstance(currentLocale);
        else if (currencyRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getCurrencyInstance(currentLocale);
        else if (percentRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getPercentInstance(currentLocale);
        String formatted = currentNumberFormat.format(currentNumber);
        numberText.setText(formatted);
    }
}