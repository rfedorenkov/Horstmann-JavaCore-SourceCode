package corejava.v2ch09.auth;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedAction;

/**
 * В этой программе демонстрируется аутентификация
 * пользователей через специальную регистрацию и
 * последующее выполнение действия типа SysPropAction
 * привилегиями зарегистрированного пользователя
 * @version 1.01 2007-10-06
 * @author Cay Horstmann
 */
public class AuthTest {
    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        try {
            LoginContext context = new LoginContext("Login1");
            context.login();
            System.out.println("Authentication successful.");
            Subject subject = context.getSubject();
            System.out.println("subject=" + subject);
            PrivilegedAction<String> action = new SysPropAction("user.home");
            String result = Subject.doAsPrivileged(subject, action, null);
            System.out.println(result);
            context.logout();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}