package corejava.v2ch07.retire;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * Это поле со списком позволяет пользователю выбрать локаль.
 * Языковые стандарты отображаются в языковом стандарте поля со списком
 * и сортируются в соответствии с параметром сортировки отображаемого языкового стандарта.
 * @version 1.01 2016-05-06
 * @author Cay Horstmann
 */
public class LocaleCombo extends JComboBox<Locale> {
    private int selected;
    private Locale[] locales;
    private ListCellRenderer<Locale> renderer;

    /**
     * Создает комбинацию локалей, которая отображает неизменяемую коллекцию локалей
     * @param locales локали для отображения в этом поле со списком
     */
    public LocaleCombo(Locale[] locales) {
        this.locales = locales.clone();
        sort();
        setSelectedItem(getLocale());
    }

    @Override
    public void setLocale(Locale newValue) {
        super.setLocale(newValue);
        sort();
    }

    private void sort() {
        Locale loc = getLocale();
        Collator collator = Collator.getInstance(loc);
        Comparator<Locale> comp =
                (a, b) -> collator.compare(a.getDisplayName(loc), b.getDisplayName(loc));
        Arrays.sort(locales, comp);
        setModel(new ComboBoxModel<>() {
            @Override
            public Locale getElementAt(int i) {
                return locales[i];
            }

            @Override
            public int getSize() {
                return locales.length;
            }


            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }

            @Override
            public Object getSelectedItem() {
                return selected >= 0 ? locales[selected] : null;
            }

            @Override
            public void setSelectedItem(Object anItem) {
                if (anItem == null) selected = -1;
                else selected = Arrays.binarySearch(locales, (Locale) anItem, comp);
            }
        });
        setSelectedItem(selected);
    }

    @Override
    public ListCellRenderer<Locale> getRenderer() {
        if (renderer == null) {
            @SuppressWarnings("unchecked") final ListCellRenderer<Object> originalRenderer
                    = (ListCellRenderer<Object>) super.getRenderer();
            if (originalRenderer == null) return null;
            renderer = (list, value, index, isSelected, cellHasFocus) ->
                    originalRenderer.getListCellRendererComponent(list, value.getDisplayName(getLocale()),
                            index, isSelected, cellHasFocus);
        }
        return renderer;
    }

    @Override
    public void setRenderer(ListCellRenderer<? super Locale> newValue) {
        renderer = null;
        super.setRenderer(newValue);
    }
}