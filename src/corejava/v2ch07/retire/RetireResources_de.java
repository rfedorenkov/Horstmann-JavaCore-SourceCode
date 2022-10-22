package corejava.v2ch07.retire;

import java.awt.*;
import java.util.ListResourceBundle;

/**
 * Нестроковые ресурсы для пользовательского интерфейса на
 * немецком языке программы калькуляции пенсионных сбережений
 * @version 1.21 2001-08-27
 * @author Cay Horstmann
 */
public class RetireResources_de extends ListResourceBundle {
    private static final Object[][] contents = {
            // НАЧАЛО ИНТЕРНАЦИОНАЛИЗАЦИИ
            { "colorPre", Color.yellow }, { "colorGain", Color.black }, { "colorLoss", Color.red }
            // КОНЕЦ ИНТЕРНАЦИОНАЛИЗАЦИИ
    };

    @Override
    protected Object[][] getContents() {
        return new Object[0][];
    }
}