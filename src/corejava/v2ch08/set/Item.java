package corejava.v2ch08.set;

import corejava.v2ch08.bytecodeAnnotations.LogEntry;

import java.util.Objects;

/**
 * Товар с описанием и номенклатурным номером
 */
public class Item {
    private String description;
    private int partNumber;

    /**
     * Конструирует объект товара
     * @param aDescription Описание товара
     * @param aPartNumber Номенклатурный номер
     */
    public Item(String aDescription, int aPartNumber) {
        description = aDescription;
        partNumber = aPartNumber;
    }

    /**
     * Получить описание данного товара
     * @return Возвращает описание товара
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[description=" + description + ", partNumber=" + partNumber + "]";
    }

    @Override
    @LogEntry(logger = "com.horstmann")
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Item other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    @Override
    @LogEntry(logger = "com.horstmann")
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
}