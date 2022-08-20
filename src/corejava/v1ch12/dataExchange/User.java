package corejava.v1ch12.dataExchange;

/**
 * У пользователя есть имя и пароль.
 * По соображениям безопасности пароль хранится в виде char[], а не строки.
 */
public class User {

    private String name;
    public char[] password;

    public User(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public char[] getPassword() {
        return password;
    }
}