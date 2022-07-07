package corejava.v1ch05.inheritance;

public class Manager extends Employee {

    private double bonus;

    /**
     * @param name Имя работника
     * @param salary Зарплата
     * @param year Год приема на работу
     * @param month Месяц приема на работу
     * @param day День приема на работу
     */
    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    @Override
    public double getSalary() {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}