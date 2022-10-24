package corejava.v2ch08.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * A Java class that holds the bytecodes in a byte array.
 * @version 1.00 2007-11-02
 * @author Cay Horstmann
 */
public class ByteArrayJavaClass extends SimpleJavaFileObject {
    private ByteArrayOutputStream stream;

    /**
     * Конструирует новый объект типа ByteArrayJavaClass
     * @param name Имя файла класса, представленного данным объектом
     */
    protected ByteArrayJavaClass(String name) {
        super(URI.create("bytes:///" + name), Kind.CLASS);
        stream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return stream;
    }

    public byte[] getBytes() {
        return stream.toByteArray();
    }
}