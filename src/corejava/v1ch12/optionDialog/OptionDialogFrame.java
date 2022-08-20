package corejava.v1ch12.optionDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Date;

/**
 * Фрейм с установками для выбора различных типов диалоговых окон
 */
public class OptionDialogFrame extends JFrame {
    private ButtonPanel typePanel;
    private ButtonPanel messagePanel;
    private ButtonPanel messageTypePanel;
    private ButtonPanel optionTypePanel;
    private ButtonPanel optionsPanel;
    private ButtonPanel inputPanel;
    private String messageString = "Message";
    private Icon messageIcon = new ImageIcon("src/corejava/v1ch12/blue-ball.gif");
    private Object messageObject = new Date();
    private Component messageComponent = new SampleComponent();

    public OptionDialogFrame() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 3));

        typePanel = new ButtonPanel("Type", "Message", "Confirm", "Option", "Input");
        messageTypePanel = new ButtonPanel("Message Type", "ERROR_MESSAGE", "INFORMATION_MESSAGE",
                "WARNING_MESSAGE", "QUESTION_MESSAGE", "PLAIN_MESSAGE");
        messagePanel = new ButtonPanel("Message", "String", "Icon", "Component", "Other", "Object[]");
        optionTypePanel = new ButtonPanel("Confirm", "DEFAULT_OPTION", "YES_NO_OPTION",
                "YES_NO_CANCEL_OPTION", "OK_CANCEL_OPTION");
        optionsPanel = new ButtonPanel("Option", "String[]", "Icon[]", "Object[]");
        inputPanel = new ButtonPanel("Input", "Text field", "Combo box");

        gridPanel.add(typePanel);
        gridPanel.add(messageTypePanel);
        gridPanel.add(messagePanel);
        gridPanel.add(optionTypePanel);
        gridPanel.add(optionsPanel);
        gridPanel.add(inputPanel);

        // ввести панель с кнопкой Show

        JPanel showPanel = new JPanel();
        JButton showButton = new JButton("Show");
        showButton.addActionListener(new ShowAction());
        showPanel.add(showButton);

        add(gridPanel, BorderLayout.CENTER);
        add(showPanel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Получает выбранное в настоящий момент сообщение
     * @return Строка, пиктограмма, компонент или массив объектов
     *         в зависимости от выбора на панели сообщений
     */
    public Object getMessage() {
        String s = messagePanel.getSelection();
        if (s.equals("String")) return messageString;
        else if (s.equals("Icon")) return messageIcon;
        else if (s.equals("Component")) return messageComponent;
        else if (s.equals("Object[]")) return new Object[] { messageString, messageIcon,
                messageComponent, messageObject };
        else if (s.equals("Other")) return messageObject;
        else return null;
    }

    /**
     * Получает выбранные в настоящий момент варианты
     * @return Строка, пиктограмма, компонент или массив объектов
     *         в зависимости от выбора на панели сообщений
     */
    public Object[] getOptions() {
        String s = optionsPanel.getSelection();
        if (s.equals("String[]")) return new String[] { "Yellow", "Blue", "Red" };
        else if (s.equals("Icon[]")) return new Icon[] { new ImageIcon("src/corejava/v1ch12/yellow-ball.gif"),
                new ImageIcon("src/corejava/v1ch12/blue-ball.gif"),
                new ImageIcon("src/corejava/v1ch12/red-ball.gif")};
        else if (s.equals("Object[]")) return new Object[] { messageString, messageIcon,
                messageComponent, messageObject };
        else return null;
    }

    /**
     * Получает выбранное в настоящий момент сообщение или тип вариантов
     * @param panel Панель Message Type (Тип сообщения) или Confirm (Подтверждение)
     * @return Константа XXX_MESSAGE или XXX_OPTION, выбранная из класса JOptionPane
     */
    public int getType(ButtonPanel panel) {
        String s = panel.getSelection();
        try {
            return JOptionPane.class.getField(s).getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return -1;
        }
    }

    /**
     * Приемник действий для кнопки Show. Отображает диалоговое
     * окно подтверждения (Confirm), ввода (Input), сообщения (Message)
     * или выбора варианта (Option) в зависимости от типа диалогового
     * окна, выбранного на панели Type
     */
    private class ShowAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (typePanel.getSelection().equals("Confirm")) JOptionPane.showConfirmDialog(
                    OptionDialogFrame.this, getMessage(), "Title", getType(optionTypePanel),
                    getType(messageTypePanel));
            else if (typePanel.getSelection().equals("Input")) {
                if (inputPanel.getSelection().equals("Text field")) JOptionPane.showInputDialog(
                        OptionDialogFrame.this, getMessage(), "Title", getType(messageTypePanel));
                else JOptionPane.showInputDialog(OptionDialogFrame.this, getMessage(), "Title",
                        getType(messageTypePanel), null, new String[] { "Yellow", "Blue", "Red" },
                        "Blue");
            } else if (typePanel.getSelection().equals("Message")) JOptionPane.showMessageDialog(
                    OptionDialogFrame.this, getMessage(), "Title", getType(messageTypePanel));
            else if (typePanel.getSelection().equals("Option")) JOptionPane.showOptionDialog(
                    OptionDialogFrame.this, getMessage(), "Title", getType(optionTypePanel),
                    getType(messageTypePanel), null, getOptions(), getOptions()[0]);
        }
    }
}

/**
 * Компонент с окрашенной поверхностью
 */
class SampleComponent extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1);
        g2.setPaint(Color.YELLOW);
        g2.fill(rect);
        g2.setPaint(Color.BLUE);
        g2.draw(rect);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }
}