package corejava.v2ch07.retire;

import java.awt.*;
import java.util.*;

/**
 * Нестроковые ресурсы для пользовательского интерфейса на
 * английском языке программы калькуляции пенсионных сбережений
 * @version 1.21 2001-08-27
 * @author Cay Horstmann
 */
public class RetireResources extends ListResourceBundle {
    private static final Object[][] contents = {
            // НАЧАЛО ИНТЕРНАЦИОНАЛИЗАЦИИ
            { "colorPre", Color.blue }, { "colorGain", Color.white }, { "colorLoss", Color.red }
            // КОНЕЦ ИНТЕРНАЦИОНАЛИЗАЦИИ
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}