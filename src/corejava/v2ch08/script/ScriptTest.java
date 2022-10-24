package corejava.v2ch08.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @version 1.02 2016-05-10
 * @author Cay Horstmann
 */
public class ScriptTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ScriptEngineManager manager = new ScriptEngineManager();
                String language;
                if (args.length == 0) {
                    System.out.println("Available factories: ");
                    for (ScriptEngineFactory factory : manager.getEngineFactories())
                        System.out.println(factory.getEngineName());

                    language = "nashorn";
                } else language = args[0];

                final ScriptEngine engine = manager.getEngineByName(language);
                if (engine == null) {
                    System.err.println("No engine for " + language);
                    System.exit(1);
                }

                final String frameClassName = args.length < 2 ? "buttons1.ButtonFrame" : args[1];

                JFrame frame = (JFrame) Class.forName(frameClassName).newInstance();
                InputStream in = frame.getClass().getResourceAsStream("init." + language);
                if (in != null) engine.eval(new InputStreamReader(in));
                Map<String, Component> components = new HashMap<>();
                getComponentBindings(frame, components);
                components.forEach((name, c) -> engine.put(name, c));

                final Properties events = new Properties();
                in = frame.getClass().getResourceAsStream(language + ".properties");
                events.load(in);

                for (final Object e : events.keySet()) {
                    String[] s = ((String) e).split("\\.");
                    addListener(s[0], s[1], (String) events.get(e), engine, components);
                }
                frame.setTitle("ScriptTest");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (ReflectiveOperationException | IOException | ScriptException | IntrospectionException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Собирает все именованные компоненты в контейнер
     * @param c Компонент
     * @param namedComponents Отображение, в которое вводятся
     *                        компоненты и их имена
     */
    private static void getComponentBindings(Component c, Map<String, Component> namedComponents) {
        String name = c.getName();
        if (name != null) {
            namedComponents.put(name, c);
        }
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents())
                getComponentBindings(child, namedComponents);
        }
    }

    /**
     * Вводит в объект приемник событий, метод которого выполняет сценарий
     * @param beanName Имя компонента JavaBeans, в который вводится приемник событий
     * @param eventName Название типа приемника событий, например,
     *                  "action" (действие) или "change" (изменение)
     * @param scriptCode Выполняемый код сценария
     * @param engine Механизм сценариев, выполняющий код сценария
     * @param components Привязки для выполнения сценария
     * @throws IntrospectionException
     */
    private static void addListener(String beanName, String eventName, final String scriptCode,
                                    final ScriptEngine engine, Map<String, Component> components)
            throws ReflectiveOperationException, IntrospectionException {
        Object bean = components.get(beanName);
        EventSetDescriptor descriptor = getEventSetDescriptor(bean, eventName);
        if (descriptor == null) return;
        descriptor.getAddListenerMethod().invoke(bean,
                Proxy.newProxyInstance(null, new Class[] { descriptor.getListenerType() },
                        (proxy, method, args) -> {
                    engine.eval(scriptCode);
                    return null;
                }));
    }

    private static EventSetDescriptor getEventSetDescriptor(Object bean, String eventName)
            throws IntrospectionException {
        for (EventSetDescriptor descriptor : Introspector.getBeanInfo(bean.getClass())
                .getEventSetDescriptors())
            if (descriptor.getName().equals(eventName)) return descriptor;
        return null;
    }
}


