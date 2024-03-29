package corejava.v2ch08.bytecodeAnnotations;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Вводит инструкции для протоколирования записей вначале
 * всех методов из класса, снабженного аннотацией LogEntry
 * @version 1.20 2016-05-10
 * @author Cay Horstmann
 */
public class EntryLogger extends ClassVisitor {
    private String className;

    /**
     * Конструирует объект типа EntryLogger,
     * вставляющий инструкции протоколирования в
     * аннотированные методы данного класса
     * @param className Класс
     */
    public EntryLogger(ClassWriter writer, String className) {
        super(Opcodes.ASM5, writer);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        return new AdviceAdapter(Opcodes.ASM5, mv, access, methodName, desc) {
            private String loggerName;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return new AnnotationVisitor(Opcodes.ASM5) {
                    @Override
                    public void visit(String name, Object value) {
                        if (desc.equals("LbytecodeAnnotations/LogEntry;") && name.equals("logger"))
                                loggerName = value.toString();
                    }
                };
            }

            @Override
            protected void onMethodEnter() {
                if (loggerName != null) {
                    visitLdcInsn(loggerName);
                    visitMethodInsn(INVOKESTATIC, "java/util/logging/Logger", "getLogger",
                            "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
                    visitLdcInsn(className);
                    visitLdcInsn(methodName);
                    visitMethodInsn(INVOKEVIRTUAL, "java/util/logging/Logger", "entering",
                            "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    loggerName = null;
                }
            }
        };
    }

    /**
     * Вводит код регистрации записей в указанный класс
     * @param args Имя файла класса для вставки кода
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("USAGE: java bytecodeAnnotations.EntryLogger classfile");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);
        ClassReader reader = new ClassReader(Files.newInputStream(path));
        ClassWriter writer = new ClassWriter(
                ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        EntryLogger entryLogger = new EntryLogger(writer,
                path.toString().replace(".class", "").replaceAll("[/\\\\]", "."));
        reader.accept(entryLogger, ClassReader.EXPAND_FRAMES);
        Files.write(Paths.get(args[0]), writer.toByteArray());
    }
}