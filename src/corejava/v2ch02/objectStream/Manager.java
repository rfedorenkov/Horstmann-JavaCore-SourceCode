package corejava.v2ch02.objectStream;

public class Manager extends Employee {
    private Employee secretary;

    /**
     * Создает менеджера без секретаря
     * @param name Имя
     * @param salary Зарплата
     * @param year Год
     * @param month Месяц
     * @param day День
     */
    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        this.secretary = null;
    }

    /**
     * Прикрепляет секретаря к менеджеру
     * @param secretary Секретарь
     */
    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return super.toString() + "[secretary=" + secretary + "]";
    }
}