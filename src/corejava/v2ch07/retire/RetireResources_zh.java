package corejava.v2ch07.retire;

import java.awt.*;
import java.util.ListResourceBundle;

/**
 * Нестроковые ресурсы для пользовательского интерфейса на
 * китайском языке программы калькуляции пенсионных сбережений
 * @version 1.21 2001-08-27
 * @author Cay Horstmann
 */
public class RetireResources_zh extends ListResourceBundle {
    private static final Object[][] contents = {
            // НАЧАЛО ИНТЕРНАЦИОНАЛИЗАЦИИ
            { "colorPre", Color.red }, { "colorGain", Color.blue }, { "colorLoss", Color.yellow }
            // КОНЕЦ ИНТЕРНАЦИОНАЛИЗАЦИИ
    };

    @Override
    protected Object[][] getContents() {
        return new Object[0][];
    }
}