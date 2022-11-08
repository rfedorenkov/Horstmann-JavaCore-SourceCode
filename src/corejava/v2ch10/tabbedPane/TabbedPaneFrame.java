package corejava.v2ch10.tabbedPane;

import javax.swing.*;
import java.awt.*;

/**
 * Этот фрейм содержит панель с вкладками и
 * кнопки-переключатели режимов отображения
 * вкладок полностью или частично с прокруткой
 */
public class TabbedPaneFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private JTabbedPane tabbedPane;

    public TabbedPaneFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        tabbedPane = new JTabbedPane();
        // установить пустое значение null во всех компонентах и
        // отложить их загрузку вплоть до показа вкладки в первый раз

        ImageIcon icon = new ImageIcon(getClass().getResource("yellow-ball.gif"));

        tabbedPane.addTab("Mercury", icon, null);
        tabbedPane.addTab("Venus", icon, null);
        tabbedPane.addTab("Earth", icon, null);
        tabbedPane.addTab("Mars", icon, null);
        tabbedPane.addTab("Jupiter", icon, null);
        tabbedPane.addTab("Saturn", icon, null);
        tabbedPane.addTab("Uranus", icon, null);
        tabbedPane.addTab("Neptune", icon, null);
        tabbedPane.addTab("Pluto", null, null);

        final int plutoIndex = tabbedPane.indexOfTab("Pluto");
        JPanel plutoPanel = new JPanel();
        plutoPanel.add(new JLabel("Pluto", icon, SwingConstants.LEADING));
        JToggleButton plutoCheckbox = new JCheckBox();
        plutoCheckbox.addActionListener(event -> tabbedPane.remove(plutoIndex));
        plutoPanel.add(plutoCheckbox);
        tabbedPane.setTabComponentAt(plutoIndex, plutoPanel);

        add(tabbedPane, "Center");

        tabbedPane.addChangeListener(event -> {
            // проверить, находится ли пустой компонент
            // по-прежнему на вкладке

            if (tabbedPane.getSelectedComponent() == null) {
                // установить пиктограмму для компонента
                int n = tabbedPane.getSelectedIndex();
                loadTab(n);
            }
        });

        loadTab(0);

        JPanel buttonPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton wrapButton = new JRadioButton("Wrap tabs");
        wrapButton.addActionListener(event ->
                tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT));
        buttonPanel.add(wrapButton);
        buttonGroup.add(wrapButton);
        wrapButton.setSelected(true);
        JRadioButton scrollButton = new JRadioButton("Scroll tabs");
        scrollButton.addActionListener(event ->
                tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT));
        buttonPanel.add(scrollButton);
        buttonGroup.add(scrollButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Загружает вкладку по указанному индексу
     * @param n Индекс загружаемой вкладки
     */
    private void loadTab(int n) {
        String title = tabbedPane.getTitleAt(n);
        ImageIcon planetIcon = new ImageIcon(getClass().getResource(title + ".gif"));
        tabbedPane.setComponentAt(n, new JLabel(planetIcon));

        // обозначить данную вкладку как уже просматривавшуюся
        // просто ради большей привлекательности

        tabbedPane.setIconAt(n, new ImageIcon(getClass().getResource("red-ball.gif")));
    }
}