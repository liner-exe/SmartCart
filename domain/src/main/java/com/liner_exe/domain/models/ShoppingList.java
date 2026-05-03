package com.liner_exe.domain.models;

import java.util.List;

public class ShoppingList {
    private final int id;
    private String name;
    private final int totalItems;
    private final int boughtItems;

    public ShoppingList(int id, String name, int totalItems, int boughtItems) {
        this.id = id;
        this.name = name;
        this.totalItems = totalItems;
        this.boughtItems = boughtItems;
    }

    public ShoppingList(int id, String name) {
        this.id = id;
        this.name = name;
        this.totalItems = 0;
        this.boughtItems = 0;
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

    public void setName(String name) {
        this.name = name;
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
}
