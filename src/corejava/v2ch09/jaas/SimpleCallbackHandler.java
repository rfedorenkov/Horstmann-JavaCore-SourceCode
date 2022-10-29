package corejava.v2ch09.jaas;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

/**
 * Этот простой обработчик обратных вызовов предоставляет
 * заданные имя пользователя и пароль
 */
public class SimpleCallbackHandler implements CallbackHandler {
    private String username;
    private char[] password;

    /**
     * Конструирует обработчик обратных вызовов
     * @param username Имя пользователя
     * @param password Символьный массив, содержащий пароль
     */
    public SimpleCallbackHandler(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword(password);
            }
        }
    }
}