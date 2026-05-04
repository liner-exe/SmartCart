package com.liner_exe.domain.models;

import java.io.Serializable;

public class Product implements Serializable {
    private final int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String name) {
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
