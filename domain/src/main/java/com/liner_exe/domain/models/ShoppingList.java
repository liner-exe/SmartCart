package com.liner_exe.domain.models;

import java.util.List;
import java.util.Objects;

public class ShoppingList implements DiffIdentifiable {
    private final int id;
    private final String name;
    private final int totalItems;
    private final int boughtItems;

    public ShoppingList(int id, String name, int totalItems, int boughtItems) {
        this.id = id;
        this.name = name;
        this.totalItems = totalItems;
        this.boughtItems = boughtItems;
    }

    public ShoppingList(String name) {
        this.id = 0;
        this.name = name;
        this.totalItems = 0;
        this.boughtItems = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBoughtItems() {
        return boughtItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public String getProgressString() {
        return boughtItems + "/" + totalItems;
    }

    @Override
    public boolean isContentTheSame(Object other) {
        return this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingList list = (ShoppingList) o;
        return id == list.id &&
                boughtItems == list.boughtItems &&
                totalItems == list.totalItems &&
                Objects.equals(name, list.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, boughtItems, totalItems);
    }
}
