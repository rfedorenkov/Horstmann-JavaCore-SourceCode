package corejava.v2ch07.retire;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * В этой программе демонстрируется калькуляция пенсионных
 * сбережений. Ее пользовательский интерфейс представлен на
 * английском, немецком и китайском языках
 * @version 1.24 2016-05-06
 * @author Cay Horstmann
 */
public class Retire {
    public static void main(String[] args) {
        JFrame frame = new RetireFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class RetireFrame extends JFrame {
    private JTextField savingsField = new JTextField(10);
    private JTextField contribField = new JTextField(10);
    private JTextField incomeField = new JTextField(10);
    private JTextField currentAgeField = new JTextField(4);
    private JTextField retireAgeField = new JTextField(4);
    private JTextField deathAgeField = new JTextField(4);
    private JTextField inflationPercentField = new JTextField(6);
    private JTextField investPercentField = new JTextField(6);
    private JTextArea retireText = new JTextArea(10, 25);
    private RetireComponent retireCanvas = new RetireComponent();
    private JButton computeButton = new JButton();
    private JLabel languageLabel = new JLabel();
    private JLabel savingsLabel = new JLabel();
    private JLabel contribLabel = new JLabel();
    private JLabel incomeLabel = new JLabel();
    private JLabel currentAgeLabel = new JLabel();
    private JLabel retireAgeLabel = new JLabel();
    private JLabel deathAgeLabel = new JLabel();
    private JLabel inflationPercentLabel = new JLabel();
    private JLabel investPercentLabel = new JLabel();
    private RetireInfo info = new RetireInfo();
    private Locale[] locales = { Locale.US, Locale.CHINA, Locale.GERMANY };
    private Locale currentLocale;
    private JComboBox<Locale> localeCombo = new LocaleCombo(locales);
    private ResourceBundle res;
    private ResourceBundle resStrings;
    private NumberFormat currencyFmt;
    private NumberFormat numberFmt;
    private NumberFormat percentFmt;

    public RetireFrame() {
        setLayout(new GridBagLayout());
        add(languageLabel, new GBC(0, 0).setAnchor(GBC.EAST));
        add(savingsLabel, new GBC(0, 1).setAnchor(GBC.EAST));
        add(contribLabel, new GBC(2, 1).setAnchor(GBC.EAST));
        add(incomeLabel, new GBC(4, 1).setAnchor(GBC.EAST));
        add(currentAgeLabel, new GBC(0, 2).setAnchor(GBC.EAST));
        add(retireAgeLabel, new GBC(2, 2).setAnchor(GBC.EAST));
        add(deathAgeLabel, new GBC(4, 2).setAnchor(GBC.EAST));
        add(inflationPercentLabel, new GBC(0, 3).setAnchor(GBC.EAST));
        add(investPercentLabel, new GBC(2, 3).setAnchor(GBC.EAST));
        add(localeCombo, new GBC(1, 0, 3, 1));
        add(savingsField, new GBC(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(contribField, new GBC(3, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(incomeField, new GBC(5, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(currentAgeField, new GBC(1, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(retireAgeField, new GBC(3, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(deathAgeField, new GBC(5, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(inflationPercentField, new GBC(1, 3).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(investPercentField, new GBC(3, 3).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(retireCanvas, new GBC(0, 4, 4, 1).setWeight(100, 1000).setFill(GBC.BOTH));
        add(new JScrollPane(retireText), new GBC(4, 4, 2, 1).setWeight(0, 100).setFill(GBC.BOTH));

        computeButton.setName("computeButton");
        computeButton.addActionListener(event -> {
            getInfo();
            updateData();
            updateGraph();
        });
        add(computeButton, new GBC(5, 3));

        retireText.setEditable(false);
        retireText.setFont(new Font("Monospaced", Font.PLAIN, 10));

        info.setSavings(0);
        info.setContrib(9000);
        info.setIncome(60000);
        info.setCurrentAge(35);
        info.setRetireAge(65);
        info.setDeathAge(85);
        info.setInvestPercent(0.1);
        info.setInflationPercent(0.05);

        int localeIndex = 0; // региональные настройки США выбираются по умолчанию
        for (int i = 0; i < locales.length; i++)
            // если текущие региональные настройки относятся
            // к числу выбираемых, то выбрать их
            if (getLocale().equals(locales[i])) localeIndex = i;
        setCurrentLocale(locales[localeIndex]);

        localeCombo.addActionListener(event -> {
            setCurrentLocale((Locale) localeCombo.getSelectedItem());
            validate();
        });
        pack();
    }

    /**
     * Устанавливает текущие региональные настройки
     * @param locale Требующиеся региональные настройки
     */
    public void setCurrentLocale(Locale locale) {
        currentLocale = locale;
        localeCombo.setLocale(currentLocale);
        localeCombo.setSelectedItem(currentLocale);

        res = ResourceBundle.getBundle("retire.RetireResources", currentLocale);
        resStrings = ResourceBundle.getBundle("retire.RetireStrings", currentLocale);
        currencyFmt = NumberFormat.getCurrencyInstance(currentLocale);
        numberFmt = NumberFormat.getNumberInstance(currentLocale);
        percentFmt = NumberFormat.getPercentInstance(currentLocale);

        updateDisplay();
        updateInfo();
        updateData();
        updateGraph();
    }

    /**
     * Обновляет все метки при отображении
     */
    public void updateDisplay() {
        languageLabel.setText(resStrings.getString("language"));
        savingsLabel.setText(resStrings.getString("savings"));
        contribLabel.setText(resStrings.getString("contrib"));
        incomeLabel.setText(resStrings.getString("income"));
        currentAgeLabel.setText(resStrings.getString("currentAge"));
        retireAgeLabel.setText(resStrings.getString("retireAge"));
        deathAgeLabel.setText(resStrings.getString("deathAge"));
        inflationPercentLabel.setText(resStrings.getString("inflationPercent"));
        investPercentLabel.setText(resStrings.getString("investPercent"));
        computeButton.setText(resStrings.getString("computeButton"));

    }

    /**
     * Обновляет данные в текстовых полях
     */
    public void updateInfo() {
        savingsField.setText(currencyFmt.format(info.getSavings()));
        contribField.setText(currencyFmt.format(info.getContrib()));
        incomeField.setText(currencyFmt.format(info.getIncome()));
        currentAgeField.setText(numberFmt.format(info.getCurrentAge()));
        retireAgeField.setText(numberFmt.format(info.getRetireAge()));
        deathAgeField.setText(numberFmt.format(info.getDeathAge()));
        investPercentField.setText(percentFmt.format(info.getInvestPercent()));
        inflationPercentField.setText(percentFmt.format(info.getInflationPercent()));
    }

    /**
     * Обновляет данные, отображаемые в текстовой части
     */
    public void updateData() {
        retireText.setText("");
        MessageFormat retireMsg = new MessageFormat("");
        retireMsg.setLocale(currentLocale);
        retireMsg.applyPattern(resStrings.getString("retire"));

        for (int i = info.getCurrentAge(); i <= info.getDeathAge(); i++) {
            Object[] args = { i, info.getBalance(i) };
            retireText.append(retireMsg.format(args) + "\n");
        }
    }

    /**
     * Обновляет график
     */
    public void updateGraph() {
        retireCanvas.setColorPre((Color) res.getObject("colorPre"));
        retireCanvas.setColorGain((Color) res.getObject("colorGain"));
        retireCanvas.setColorLoss((Color) res.getObject("colorLoss"));
        retireCanvas.setInfo(info);
        repaint();
    }

    /**
     * Считывает данные, вводимые пользователем в текстовых полях
     */
    public void getInfo() {
        try {
            info.setSavings(currencyFmt.parse(savingsField.getText()).doubleValue());
            info.setContrib(currencyFmt.parse(contribField.getText()).doubleValue());
            info.setIncome(currencyFmt.parse(incomeField.getText()).doubleValue());
            info.setCurrentAge(numberFmt.parse(currentAgeField.getText()).intValue());
            info.setRetireAge(numberFmt.parse(retireAgeField.getText()).intValue());
            info.setDeathAge(numberFmt.parse(deathAgeField.getText()).intValue());
            info.setInvestPercent(percentFmt.parse(investPercentField.getText()).doubleValue());
            info.setInflationPercent(percentFmt.parse(inflationPercentField.getText()).doubleValue());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }
}

/**
 * Данные, требующиеся для расчета пенсионных отчислений
 * The information required to compute retirement income data.
 */
class RetireInfo {
    private double savings;
    private double contrib;
    private double income;
    private int currentAge;
    private int retireAge;
    private int deathAge;
    private double inflationPercent;
    private double investPercent;
    private int age;
    private double balance;

    /**
     * Получает остаток на счете, имеющийся на указанный год
     * @param year Год для расчета остатка на счете
     * @return Возвращает сумму, имеющуюся (или требуемую)
     *         на указанный год
     */
    public double getBalance(int year) {
        if (year < currentAge) return 0;
        else if (year == currentAge) {
            age = year;
            balance = savings;
            return balance;
        } else if (year == age) return balance;
        if (year != age + 1) getBalance(year - 1);
        age = year;
        if (age < retireAge) balance += contrib;
        else balance -= income;
        balance = balance * (1 + (investPercent - inflationPercent));
        return balance;
    }

    /**
     * Получает сумму предыдущих сбережений
     * @return Возвращает сумму сбережений
     */
    public double getSavings() {
        return savings;
    }

    /**
     * Устанавливает сумму предыдущих сбережений
     * @param newValue Сумма сбережений
     */
    public void setSavings(double newValue) {
        savings = newValue;
    }

    /**
     * Получает сумму ежегодных отчислений на пенсионный счет
     * @return Возвращает сумму ежегодных отчислений
     */
    public double getContrib() {
        return contrib;
    }

    /**
     * Устанавливает сумму ежегодных отчислений на пенсионный счет
     * @param newValue Сумма отчислений
     */
    public void setContrib(double newValue) {
        contrib = newValue;
    }

    /**
     * Получает сумму ежегодного дохода
     * @return Возвращает сумму ежегодного дохода
     */
    public double getIncome() {
        return income;
    }

    /**
     * Устанавливает сумму ежегодного дохода
     * @param newValue Сумма ежегодного дохода
     */
    public void setIncome(double newValue) {
        income = newValue;
    }

    /**
     * Получает текущий возраст
     * @return Возвращает текущий возраст
     */
    public int getCurrentAge() {
        return currentAge;
    }

    /**
     * Устанавливает текущий возраст
     * @param newValue Текущий возраст
     */
    public void setCurrentAge(int newValue) {
        currentAge = newValue;
    }

    /**
     * Получает возраст, требующийся для выхода на пенсию
     * @return Возвращает пенсионный возраст
     */
    public int getRetireAge() {
        return retireAge;
    }

    /**
     * Устанавливает возраст, требующийся для выхода на пенсию
     * @param newValue Пенсионный возраст
     */
    public void setRetireAge(int newValue) {
        retireAge = newValue;
    }

    /**
     * Получает предполагаемую продолжительность жизни
     * @return Возвращает предполагаемую продолжительность жизни
     */
    public int getDeathAge() {
        return deathAge;
    }

    /**
     * Устанавливает предполагаемую продолжительность жизни
     * @param newValue Предполагаемая продолжительность жизни
     */
    public void setDeathAge(int newValue) {
        deathAge = newValue;
    }

    /**
     * Получает предполагаемый уровень инфляции в процентах
     * @return Позвращает уровень инфляции в процентах
     */
    public double getInflationPercent() {
        return inflationPercent;
    }

    /**
     * Устанавливает предполагаемый уровень инфляции в процентах
     * @param newValue Предполагаемый уровень инфляции в процентах
     */
    public void setInflationPercent(double newValue) {
        inflationPercent = newValue;
    }

    /**
     * Получает предполагаемый доход от капиталовложений
     * @return Возвращает доход от капиталовложений
     */
    public double getInvestPercent() {
        return investPercent;
    }

    /**
     * Устанавливает предполагаемый доход от капиталовложений
     * @param newValue Доход от капиталовложений в процентах
     */
    public void setInvestPercent(double newValue) {
        investPercent = newValue;
    }
}

/**
 * Этот компонент рисует график результатов пенсионных вложений
 */
class RetireComponent extends JComponent {
    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 200;
    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);
    private RetireInfo info = null;
    private Color colorPre;
    private Color colorGain;
    private Color colorLoss;

    public RetireComponent() {
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }

    /**
     * Устанавливает данные для построения графика
     * пенсионных вложений
     *
     * @param newInfo Новые данные о пенсионных вложениях
     */
    public void setInfo(RetireInfo newInfo) {
        info = newInfo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (info == null) return;

        double minValue = 0;
        double maxValue = 0;
        int i;
        for (i = info.getCurrentAge(); i <= info.getDeathAge(); i++) {
            double v = info.getBalance(i);
            if (minValue > v) minValue = v;
            if (maxValue < v) maxValue = v;
        }
        if (maxValue == minValue) return;

        int barWidth = getWidth() / (info.getDeathAge() - info.getCurrentAge() + 1);
        double scale = getHeight() / (maxValue - minValue);

        for (i = info.getCurrentAge(); i <= info.getDeathAge(); i++) {
            int x1 = (i - info.getCurrentAge()) * barWidth + 1;
            int y1;
            double v = info.getBalance(i);
            int height;
            int yOrigin = (int) (maxValue * scale);

            if (v >= 0) {
                y1 = (int) ((maxValue - v) * scale);
                height = yOrigin - y1;
            } else {
                y1 = yOrigin;
                height = (int) (-v * scale);
            }

            if (i < info.getRetireAge()) g2.setPaint(colorPre);
            else if (v >= 0) g2.setPaint(colorGain);
            else g2.setPaint(colorLoss);
            Rectangle2D bar = new Rectangle2D.Double(x1, y1, barWidth - 2, height);
            g2.fill(bar);
            g2.setPaint(Color.black);
            g2.draw(bar);
        }
    }

    /**
     * Устанавливает цвет графика для периода до выхода на пенсию
     * @param color Требующийся цвет
     */
    public void setColorPre(Color color) {
        colorPre = color;
        repaint();
    }

    /**
     * Устанавливает цвет графика для периода после
     * выхода на пенсию, когда остаток на пенсионном
     * счете еще положительный
     * @param color Требующийся цвет
     */
    public void setColorGain(Color color) {
        colorGain = color;
        repaint();
    }

    /**
     * Устанавливает цвет графика для периода после
     * выхода на пенсию, когда остаток на пенсионном
     * счете уже отрицательный
     * @param color Требующийся цвет
     */
    public void setColorLoss(Color color) {
        colorLoss = color;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
}