package corejava.v2ch08.bytecodeAnnotations;

import org.objectweb.asm.*;

import java.lang.instrument.*;
import java.security.ProtectionDomain;

/**
 * @version 1.10 2016-05-10
 * @author Cay Horstmann
 */
public class EntryLoggingAgent {
    public static void premain(final String arg, Instrumentation instr) {
        instr.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> cl, ProtectionDomain pd, byte[] data) throws IllegalClassFormatException {
                if (!className.replace("/", ".").equals(arg)) return null;
                ClassReader reader = new ClassReader(data);
                ClassWriter writer = new ClassWriter(
                        ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                EntryLogger el = new EntryLogger(writer, className);
                reader.accept(el, ClassReader.EXPAND_FRAMES);
                return writer.toByteArray();
            }
        });
   }
}


//package bytecodeAnnotations;
//
//import java.lang.instrument.*;
//
//import org.objectweb.asm.*;
//
///**
// * @version 1.10 2016-05-10
// * @author Cay Horstmann
// */
//public class EntryLoggingAgent
//{
//   public static void premain(final String arg, Instrumentation instr)
//   {
//      instr.addTransformer((loader, className, cl, pd, data) ->
//         {
//            if (!className.replace("/", ".").equals(arg)) return null;
//            ClassReader reader = new ClassReader(data);
//            ClassWriter writer = new ClassWriter(
//               ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//            EntryLogger el = new EntryLogger(writer, className);
//            reader.accept(el, ClassReader.EXPAND_FRAMES);
//            return writer.toByteArray();
//      });
//   }
//}