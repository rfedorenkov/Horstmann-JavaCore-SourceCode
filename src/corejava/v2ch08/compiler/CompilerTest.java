package corejava.v2ch08.compiler;

import javax.swing.*;
import javax.tools.*;
import java.io.IOException;
import java.util.*;

/**
 * @version 1.01 2016-05-10
 * @author Cay Horstmann
 */
public class CompilerTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                if (className.startsWith("x.")) {
                    ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                    classFileObjects.add(fileObject);
                    return fileObject;
                }
                else return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        };

        String frameClassName = args.length == 0 ? "buttons2.ButtonFrame" : args[0];
        JavaFileObject source = buildSource(frameClassName);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, Arrays.asList(source));
        Boolean result = task.call();

        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics())
            System.out.println(d.getKind() + ": " + d.getMessage(null));
        fileManager.close();
        if (!result) {
            System.out.println("Compilation failed.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            try {
                Map<String, byte[]> byteCodeMap = new HashMap<>();
                for (ByteArrayJavaClass cl : classFileObjects)
                    byteCodeMap.put(cl.getName().substring(1), cl.getBytes());
                ClassLoader loader = new MapClassLoader(byteCodeMap);
                JFrame frame = (JFrame) loader.loadClass("x.Frame").newInstance();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setTitle("CompilerTest");
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Генерирует исходный код подкласса, реализующего
     * метод addEventHandlers()
     * @return Возвращает объект файла, содержащий исходный
     *         код в построителе символьных строк
     */
    static JavaFileObject buildSource(String superclassName) throws IOException, ClassNotFoundException {
        StringBuilderJavaSource source = new StringBuilderJavaSource("x.Frame");
        source.append("package x;\n");
        source.append("public class Frame extends " + superclassName + " {");
        source.append("protected void addEventHandlers() {");
        final Properties props = new Properties();
        props.load(Class.forName(superclassName).getResourceAsStream("action.properties"));
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            String beanName = (String) e.getKey();
            String eventCode = (String) e.getValue();
            source.append(beanName + ".addActionListener(event -> {");
            source.append(eventCode);
            source.append("} );");
        }
        source.append("} }");
        return source;
    }
}