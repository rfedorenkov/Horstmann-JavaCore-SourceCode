package corejava.v2ch10.internalFrame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

/**
 * Этот настольный фрейм содержит панели редакторов,
 * на которых отображается HTML-документы
 */
public class DesktopFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String[] planets = { "Mercury", "Venus", "Earth", "Mars", "Jupiter",
            "Saturn", "Uranus", "Neptune", "Pluto" };

    private JDesktopPane desktop;
    private int nextFrameX;
    private int nextFrameY;
    private int frameDistance;
    private int counter;

    public DesktopFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        // установить меню

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("New");
        openItem.addActionListener(event -> {
            createInternalFrame(new JLabel(
                    new ImageIcon(getClass().getResource(planets[counter] + ".gif"))),
                    planets[counter]);
            counter = (counter + 1) % planets.length;
        });
        fileMenu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);
        JMenu windowMenu = new JMenu("Window");
        menuBar.add(windowMenu);
        JMenuItem nextItem = new JMenuItem("Next");
        nextItem.addActionListener(event -> selectNextWindow());
        windowMenu.add(nextItem);
        JMenuItem cascadeItem = new JMenuItem("Cascade");
        cascadeItem.addActionListener(event -> cascadeWindows());
        windowMenu.add(cascadeItem);
        JMenuItem tileItem = new JMenuItem("Tile");
        tileItem.addActionListener(event -> tileWindows());
        windowMenu.add(tileItem);
        final JCheckBoxMenuItem dragOutlineItem = new JCheckBoxMenuItem("Drag Outline");
        dragOutlineItem.addActionListener(event ->
                desktop.setDragMode(dragOutlineItem.isSelected() ? JDesktopPane.OUTLINE_DRAG_MODE
                        : JDesktopPane.LIVE_DRAG_MODE));
        windowMenu.add(dragOutlineItem);
    }

    /**
     * Создает внутренний фрейм на настольной панели
     * @param c Компонент, отображаемый во внутреннем фрейме
     * @param t Заголовок внутреннего фрейма
     */
    public void createInternalFrame(Component c, String t) {
        final JInternalFrame iframe = new JInternalFrame(t, true, // изменение размеров
                true, // закрытие
                true, // разворачивание
                true); // сворачивание

        iframe.add(c, BorderLayout.CENTER);
        desktop.add(iframe);

        iframe.setFrameIcon(new ImageIcon(getClass().getResource("document.gif")));

        // ввести приемник, чтобы подтвердить закрытие фрейма
        iframe.addVetoableChangeListener(event -> {
            String name = event.getPropertyName();
            Object value = event.getNewValue();

            // проверить только попытки закрыть фрейм
            if (name.equals("closed") && value.equals(true)) {
                // запросить у пользователя разрешение закрыть фрейм
                int result = JOptionPane.showInternalConfirmDialog(iframe, "OK to close?",
                        "Select an Option", JOptionPane.YES_NO_OPTION);

                // если пользователь не дает разрешение,
                // наложить запрет на закрытие фрейма
                if (result != JOptionPane.YES_OPTION)
                    throw new PropertyVetoException("User canceled close", event);
            }
        });

        // расположить фрейм
        int width = desktop.getWidth() / 2;
        int height = desktop.getHeight() / 2;
        iframe.reshape(nextFrameX, nextFrameY, width, height);

        iframe.show();

        // выбрать фрейм, на что может быть наложен запрет
        try {
            iframe.setSelected(true);
        } catch (PropertyVetoException ignored) {
        }

        frameDistance = iframe.getHeight() - iframe.getContentPane().getHeight();

        // вычислить расположение следующего фрейма

        nextFrameX += frameDistance;
        nextFrameY += frameDistance;
        if (nextFrameX + width > desktop.getWidth()) nextFrameX = 0;
        if (nextFrameY + height > desktop.getHeight()) nextFrameY = 0;
    }

    /**
     * Располагает каскадом внутренние фреймы
     */
    public void cascadeWindows() {
        int x = 0;
        int y = 0;
        int width = desktop.getWidth() / 2;
        int height = desktop.getHeight() / 2;

        for (JInternalFrame frame : desktop.getAllFrames()) {
            if (!frame.isIcon()) {
                try {
                    // попытаться сделать развернутые фреймы допускающими
                    // изменение размеров, что может быть запрещено
                    frame.setMaximum(false);
                    frame.reshape(x, y, width, height);

                    x += frameDistance;
                    y += frameDistance;
                    // перенести в следующий ряд по достижении правого
                    // края настольной панели
                    if (x + width > desktop.getWidth()) x = 0;
                    if (y + height > desktop.getHeight()) y = 0;
                } catch (PropertyVetoException ignored) {
                }
            }
        }
    }

    /**
     * Располагает мозаикой несвернутые внутренние фреймы
     * на настольной панели
     */
    public void tileWindows() {
        // подсчитать количество несвернутых фреймов
        int frameCount = 0;
        for (JInternalFrame frame : desktop.getAllFrames())
            if (!frame.isIcon()) frameCount++;
        if (frameCount == 0) return;

        int rows = (int) Math.sqrt(frameCount);
        int cols = frameCount / rows;
        int extra = frameCount % rows;
        // количество столбцов с дополнительными рядами

        int width = desktop.getWidth() / cols;
        int height = desktop.getHeight() / rows;
        int r = 0;
        int c = 0;
        for (JInternalFrame frame : desktop.getAllFrames()) {
            if (!frame.isIcon()) {
                try {
                    frame.setMaximum(false);
                    frame.reshape(c * width, r * height, width, height);
                    r++;
                    if (r == rows) {
                        r = 0;
                        c++;
                        if (c == cols - extra) {
                            // начать ввод дополнительного ряда
                            rows++;
                            height = desktop.getHeight() / rows;
                        }
                    }
                } catch (PropertyVetoException ignored) {
                }
            }
        }
    }

    /**
     * Перемещает вперед следующий несвернутый внутренний фрейм
     */
    public void selectNextWindow() {
        JInternalFrame[] frames = desktop.getAllFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isSelected()) {
                // найти следующий несвернутый фрейм,
                // который можно выбрать
                int next = (i + 1) % frames.length;
                while (next != i) {
                    if (!frames[next].isIcon()) {
                        try {
                            // все остальные фреймы свернуты
                            // или их запрещено выбирать
                            frames[next].setSelected(true);
                            frames[next].toFront();
                            frames[i].toBack();
                            return;
                        } catch (PropertyVetoException ignored) {
                        }
                    }
                    next = (next + 1) % frames.length;
                }
            }
        }
    }
}