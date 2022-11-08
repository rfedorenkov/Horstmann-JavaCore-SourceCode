package corejava.v2ch10.textFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Фильтр, ограничивающий ввод данных цифрами и знаком '-'
 */
public class IntFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        StringBuilder builder = new StringBuilder(string);
        for (int i = builder.length() - 1; i >= 0; i--) {
            int cp = builder.codePointAt(i);
            if (!Character.isDigit(cp) && cp != '-') {
                builder.deleteCharAt(i);
                if (Character.isSupplementaryCodePoint(cp)) {
                    i--;
                    builder.deleteCharAt(i);
                }
            }
        }
        super.insertString(fb, offset, builder.toString(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        if (string != null) {
            StringBuilder builder = new StringBuilder(string);
            for (int i = builder.length() - 1; i >= 0; i--) {
                int cp = builder.codePointAt(i);
                if (!Character.isDigit(cp) && cp != '-') {
                    builder.deleteCharAt(i);
                    if (Character.isSupplementaryCodePoint(cp)) {
                        i--;
                        builder.deleteCharAt(i);
                    }
                }
            }
            string = builder.toString();
        }
        super.replace(fb, offset, length, string, attrs);
    }
}