package corejava.v2ch09.jaas;

import java.security.Principal;
import java.util.Objects;

/**
 * Принципал с именованным значением (например,
 * "role=HR" или "username=harry").
 */
public class SimplePrincipal implements Principal {
    private String descr;
    private String value;

    /**
     * Конструирует принципал типа SimplePrincipal для
     * хранения отдельного описания и значения
     * @param descr Описание
     * @param value Связанное с ним значение
     */
    public SimplePrincipal(String descr, String value) {
        this.descr = descr;
        this.value = value;
    }

    /**
     * Возвращает имя роли данного принципала
     * @return Имя роли
     */
    @Override
    public String getName() {
        return descr + "=" + value;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        SimplePrincipal other = (SimplePrincipal) otherObject;
        return Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}