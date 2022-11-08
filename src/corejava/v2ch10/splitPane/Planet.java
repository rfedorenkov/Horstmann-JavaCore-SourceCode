package corejava.v2ch10.splitPane;

import javax.swing.*;

/**
 * Описывает планету
 */
public class Planet {
    private String name;
    private double radius;
    private int moons;
    private ImageIcon image;

    /**
     * Строит планету
     * @param n Название планеты
     * @param r Радиус планеты
     * @param m Количество лун
     */
    public Planet(String n, double r, int m) {
        name = n;
        radius = r;
        moons = m;
        image = new ImageIcon(getClass().getResource(name + ".gif"));
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Получает описание планеты
     * @return Описание
     */
    public String getDescription() {
        return "Radius: " + radius + "\nMoons: " + moons + "\n";
    }

    /**
     * Получает изображение планеты
     * @return Изображение
     */
    public ImageIcon getImage() {
        return image;
    }
}