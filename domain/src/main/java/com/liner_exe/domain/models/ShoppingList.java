package com.liner_exe.domain.models;

import java.util.List;

public class ShoppingList {
    private final int id;
    private String name;

    public ShoppingList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShoppingList(String name) {
        this.id = 0;
        this.name = name;
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
}
