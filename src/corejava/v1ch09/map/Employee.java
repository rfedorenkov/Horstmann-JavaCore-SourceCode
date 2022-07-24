package corejava.v1ch09.map;

/**
 * Минималистский класс сотрудников для целей тестирования.
 */
public class Employee {
    private String name;
    private double salary;

    /**
     * Создает сотрудника с окладом $0.
     * @param name Имя сотрудника
     */
    public Employee(String name) {
        this.name = name;
        salary = 0;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", salary=" + salary + "]";
    }
}