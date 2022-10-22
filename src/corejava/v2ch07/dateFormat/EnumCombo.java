package corejava.v2ch07.dateFormat;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Комбинированный список для выбора среди значений
 * статических полей, имена которых задаются в
 * конструкторе вспомогательного класса
 * @version 1.15 2016-05-06
 * @author Cay Horstmann
 */
public class EnumCombo<T> extends JComboBox<String> {
    private Map<String, T> table = new TreeMap<>();

    /**
     * Конструирует объект вспомогательного класса EnumCombo,
     * производящий значения типа T
     * @param cl Класс
     * @param labels Массив символьных строк, описывающих имена
     *               статических полей из класса cl, относящихся к типу T
     */
    public EnumCombo(Class<?> cl, String... labels) {
        for (String label : labels) {
            String name = label.toUpperCase().replace(' ', '_');
            try {
                Field f = cl.getField(name);
                @SuppressWarnings("unchecked") T value = (T) f.get(cl);
                table.put(label, value);
            } catch (Exception e) {
                label = "(" + label + ")";
                table.put(label, null);
            }
            addItem(label);
        }
        setSelectedItem(labels[0]);
    }

    /**
     * Возвращает значения поля, выбранного пользователем
     * @return Значение статического поля
     */
    public T getValue() {
        return table.get(getSelectedItem());
    }
}