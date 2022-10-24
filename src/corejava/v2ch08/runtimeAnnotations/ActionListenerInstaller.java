package corejava.v2ch08.runtimeAnnotations;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

/**
 * @version 1.00 2004-08-17
 * @author Cay Horstmann
 */
public class ActionListenerInstaller {

    /**
     * Обрабатывает все аннотации типа ActionListenerFor
     * в заданном объекте
     * @param obj Объект, методы которого могут иметь аннотации
     *            типа ActionListenerFor
     */
    public static void processAnnotations(Object obj) {
        try {
            Class<?> cl = obj.getClass();
            for (Method m : cl.getDeclaredMethods()) {
                ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);
                if (a != null) {
                    Field f = cl.getDeclaredField(a.source());
                    f.setAccessible(true);
                    addListener(f.get(obj), obj, m);
                }
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Вводит приемник действий, вызывающий заданный метод
     * @param source Источник событий, в который вводится
     *               приемник действий
     * @param param Неявный параметр метода, вызываемого
     *              приемником действий
     * @param m Метод, вызываемый приемником действий
     */
    public static void addListener(Object source, final Object param, Method m) throws ReflectiveOperationException {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method mm, Object[] args) throws Throwable {
                return m.invoke(param);
            }
        };

        Object listener = Proxy.newProxyInstance(null,
                new Class[] { ActionListener.class }, handler);
        Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
        adder.invoke(source, listener);
    }
}