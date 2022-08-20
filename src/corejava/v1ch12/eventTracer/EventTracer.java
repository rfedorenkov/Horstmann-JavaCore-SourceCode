package corejava.v1ch12.eventTracer;

import java.awt.*;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version 1.31 2004-05-10
 * @author Cay Horstmann
 */
public class EventTracer {
    private InvocationHandler handler;

    public EventTracer() {
        // обработчик всех событий в виде прокси-объектов
        handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method + ":" + args[0]);
                return null;
            }
        };
    }

    /**
     * Добавляет объекты для отслеживания всех событий, которые может
     * принимать данный компонент и производные от него компоненты
     * @param c Компонент
     */
    public void add(Component c) {
        try {
            // получить все события, которые
            // может принимать данный компонент
            BeanInfo info = Introspector.getBeanInfo(c.getClass());

            EventSetDescriptor[] eventSets = info.getEventSetDescriptors();
            for (EventSetDescriptor eventSet : eventSets)
                addListener(c, eventSet);

        } catch (IntrospectionException ignored) {
            // если генерируется исключение,
            // приемники событий не выводятся
        }

        if (c instanceof Container) {
            // получить все производные объекты и организовать
            // рекурсивный вызов метода add()
            for (Component comp : ((Container) c).getComponents())
                add(comp);
        }
    }

    /**
     * Добавляет приемник заданного множества событий
     * @param c        Компонент
     * @param eventSet Описатель интерфейса приемника событий
     */
    public void addListener(Component c, EventSetDescriptor eventSet) {
        // создать прокси-объект для данного типа приемника событий
        // и направить все вызовы этому обработчику
        Object proxy = Proxy.newProxyInstance(null, new Class[]{eventSet.getListenerType()}, handler);

        // добавить прокси-объект как приемник событий в компонент
        Method addListenerMethod = eventSet.getAddListenerMethod();
        try {
            addListenerMethod.invoke(c, proxy);
        } catch (ReflectiveOperationException ignored) {
            // если генерируется исключение, приемник событий не вводится
        }
    }
}