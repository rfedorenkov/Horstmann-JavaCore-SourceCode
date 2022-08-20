package corejava.v1ch12.slider;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Фрейм с несколькими ползунками и текстовым полем для показа
 * значений, на которых по очереди устанавливаются ползунки
 */
public class SliderFrame extends JFrame {
    private JPanel sliderPanel;
    private JTextField textField;
    private ChangeListener listener;

    public SliderFrame() {
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridBagLayout());

        // общий приемник событий для всех ползунков
        listener = e -> {
            // обновить текстовое поле, если выбранный ползунок
            // установится на отметке с другим значением
            JSlider source = (JSlider) e.getSource();
            textField.setText("" + source.getValue());
        };

        // ввести простой ползунок

        JSlider slider = new JSlider();
        addSlider(slider, "Plain");

        // ввести ползунок с основными и неосновными отметками

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Ticks");

        // ввести ползунок, привязываемый к отметкам

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Snap to ticks");

        // ввести ползунок без отметок

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTrack(false);
        addSlider(slider, "No track");

        // ввести обращенный ползунок

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setInverted(true);
        addSlider(slider, "Inverted");

        // ввести ползунок с числовыми обозначениями отметок

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Labels");

        // ввести ползунок с буквенными обозначениями отметок

        slider = new JSlider();
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        Dictionary<Integer, Component> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));

        slider.setLabelTable(labelTable);
        addSlider(slider, "Custom labels");

        // ввести ползунок с пиктограммными обозначениями отметок

        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);

        labelTable = new Hashtable<>();

        // ввести изображения игральных карт
        labelTable.put(0, new JLabel(new ImageIcon("src/corejava/v1ch12/nine.gif")));
        labelTable.put(20, new JLabel(new ImageIcon("src/corejava/v1ch12/ten.gif")));
        labelTable.put(40, new JLabel(new ImageIcon("src/corejava/v1ch12/jack.gif")));
        labelTable.put(60, new JLabel(new ImageIcon("src/corejava/v1ch12/queen.gif")));
        labelTable.put(80, new JLabel(new ImageIcon("src/corejava/v1ch12/king.gif")));
        labelTable.put(100, new JLabel(new ImageIcon("src/corejava/v1ch12/ace.gif")));

        slider.setLabelTable(labelTable);
        addSlider(slider, "Icon labels");

        // ввести текстовое поле для показа значения, на котором
        // установлен выбранный в настоящий момент ползунок

        textField = new JTextField();
        add(sliderPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Вводит ползунки на панели и привязывает
     * к ним приемник событий
     * @param s Ползунок
     * @param description Описание ползунка
     */
    public void addSlider(JSlider s, String description) {
        s.addChangeListener(listener);
        JPanel panel = new JPanel();
        panel.add(s);
        panel.add(new JLabel(description));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = sliderPanel.getComponentCount();
        gbc.anchor = GridBagConstraints.WEST;
        sliderPanel.add(panel, gbc);
    }
}