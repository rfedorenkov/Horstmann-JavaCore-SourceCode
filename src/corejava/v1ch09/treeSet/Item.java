package corejava.v1ch09.treeSet;

import java.util.Objects;

/**
 * Описание изделия и его номер по каталогу
 */
public class Item implements Comparable<Item> {
    private String description;
    private int partNumber;

    /**
     * Конструирует объект изделия
     * @param aDescription Описание изделия
     * @param aPartNumber Номер изделия по каталогу
     */
    public Item(String aDescription, int aPartNumber) {
        description = aDescription;
        partNumber = aPartNumber;
    }

    /**
     * Получает описание данного изделия
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[description=" + description + ", partNumber=" + partNumber + "]";
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        Item item = (Item) otherObject;
        return Objects.equals(description, item.description) && partNumber == item.partNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }

    @Override
    public int compareTo(Item other) {
        int diff = Integer.compare(partNumber, other.partNumber);
        return diff != 0 ? diff :
                description.compareTo(other.description);
    }
}