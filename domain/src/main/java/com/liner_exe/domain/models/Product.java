package com.liner_exe.domain.models;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable, DiffIdentifiable {
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

    @Override
    public boolean isContentTheSame(Object other) {
        return this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.getId() && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
