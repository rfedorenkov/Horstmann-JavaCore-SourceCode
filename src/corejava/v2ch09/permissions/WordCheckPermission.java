package corejava.v2ch09.permissions;

import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Полномочия на проверку непристойных слов
 */
public class WordCheckPermission extends Permission {
    private String action;

    /**
     * Конструирует полномочия на проверку непристойных слов
     * Constructs a permission with the specified name.
     * @param target Список разделяемых запятыми слов
     * @param anAction Действие "вставить" или "исключить"
     */
    public WordCheckPermission(String target, String anAction) {
        super(target);
        action = anAction;
    }

    @Override
    public String getActions() {
        return action;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!getClass().equals(other.getClass())) return false;
        WordCheckPermission b = (WordCheckPermission) other;
        if (!Objects.equals(action, b.action)) return false;
        if ("insert".equals(action)) return Objects.equals(getName(), b.getName());
        else if ("avoid".equals(action)) return Objects.equals(badWordSet(), b.badWordSet());
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), action);
    }

    @Override
    public boolean implies(Permission other) {
        if (!(other instanceof WordCheckPermission)) return false;
        WordCheckPermission b = (WordCheckPermission) other;
        if (action.equals("insert")) {
            return b.action.equals("insert") && getName().indexOf(b.getName()) >= 0;
        } else if (action.equals("avoid")) {
            if (b.action.equals("avoid")) return b.badWordSet().containsAll(badWordSet());
            else if (b.action.equals("insert")) {
                for (String badWord : badWordSet())
                    if (b.getName().indexOf(badWord) >= 0) return false;
                return true;
            } else return false;
        }
        else return false;
    }

    /**
     * Получает непристойные слова, описываемые данным
     * правилом прав доступа
     * @return Возвращает непристойные слова
     */
    public Set<String> badWordSet() {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(getName().split(",")));
        return set;
    }
}