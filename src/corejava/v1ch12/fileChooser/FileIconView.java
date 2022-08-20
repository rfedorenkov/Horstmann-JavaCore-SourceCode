package corejava.v1ch12.fileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * Представление файлов для отображения пиктограмм
 * рядом с именами всех отфильтрованных файлов
 */
public class FileIconView extends FileView {
    private FileFilter filter;
    private Icon icon;

    /**
     * Конструирует объект типа FileIconView
     * @param filter Фильтр файлов. Все отфильтрованные им
     *               файлы отобразятся с пиктограммой
     * @param icon Пиктограмма, отображаемая вместе со всеми
     *             принятыми и отфильтрованными файлами
     */
    public FileIconView(FileFilter filter, Icon icon) {
        this.filter = filter;
        this.icon = icon;
    }

    @Override
    public Icon getIcon(File f) {
        if (!f.isDirectory() && filter.accept(f)) return icon;
        else return null;
    }
}