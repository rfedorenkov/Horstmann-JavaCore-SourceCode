package corejava.v1ch12.fileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Вспомогательный компонент предварительного просмотра
 * изображений в диалоговом окне для выбора файлов
 */
public class ImagePreviewer extends JLabel {

    /**
     * Конструирует объект типа ImagePreviewer
     * @param chooser Диалоговое окно для выбора файлов, изменение
     *                свойств в котором влечет за собой изменение
     *                в предварительно просматриваемом виде изображения
     *                из выбранного файла
     */
    public ImagePreviewer(JFileChooser chooser) {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());

        chooser.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
                // пользователь выбрал новый файл
                File f = (File) evt.getNewValue();
                if (f == null) {
                    setIcon(null);
                    return;
                }

                // вывести изображение в пиктограмму
                ImageIcon icon = new ImageIcon(f.getPath());

                // если пиктограмма слишком велика
                // подогнать изображение по размеру
                if (icon.getIconWidth() > getWidth())
                    icon = new ImageIcon(icon.getImage().getScaledInstance(
                            getWidth(), -1, Image.SCALE_DEFAULT));
                setIcon(icon);
            }
        });
    }
}