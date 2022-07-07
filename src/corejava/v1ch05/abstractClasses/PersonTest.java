package corejava.v1ch05.abstractClasses;

/**
 * В этой программе демонстрируется применение абстрактных классов
 * @version 1.01 2004-02-21
 * @author Cay Horstmann
 */
public class PersonTest {
    public static void main(String[] args) {
        Person[] people = new Person[2];

        // заполнить массив people объектами типа Student и Employee
        people[0] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        people[1] = new Student("Maria Morris", "computer science");

        // вывести имена и описания всех лиц,
        // представленных объектами типа Person
        for (Person p : people)
            System.out.println(p.getName() + ", " + p.getDescription());
    }
}