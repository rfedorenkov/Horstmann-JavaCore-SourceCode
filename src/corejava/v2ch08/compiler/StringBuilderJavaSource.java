package corejava.v2ch08.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * Источник, хранящий код Java в построителе символьных строк
 * @version 1.00 2007-11-02
 * @author Cay Horstmann
 */
public class StringBuilderJavaSource extends SimpleJavaFileObject {
    private StringBuilder code;

    /**
     * Конструирует новый объект типа StringBuilderJavaSource
     * @param name Имя исходного файла, представленного
     *             данным объектом
     */
    public StringBuilderJavaSource(String name) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        code = new StringBuilder();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }

    public void append(String str) {
        code.append(str);
        code.append("\n");
    }
}