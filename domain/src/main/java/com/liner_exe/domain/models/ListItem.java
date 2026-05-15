package com.liner_exe.domain.models;

import java.io.Serializable;
import java.util.Objects;

public class ListItem implements Serializable, DiffIdentifiable {
    private final int id;
    private final Product product;
    private final int quantity;
    private final double price;
    private final boolean isBought;
    private final int listId;
    private final Integer storeId;

    public ListItem(int id, Product product, int quantity, double price, boolean isBought, int listId, Integer storeId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.isBought = isBought;
        this.listId = listId;
        this.storeId = storeId;
    }

    public ListItem(int listId, Product product) {
        this.id = 0;
        this.product = product;
        this.quantity = 1;
        this.price = 0.0;
        this.isBought = false;
        this.listId = listId;
        this.storeId = null;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public double getPrice() {
        return price;
    }

    public boolean isBought() { return isBought; }

    public int getListId() { return listId; }

    @Override
    public boolean isContentTheSame(Object other) {
        return this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListItem item = (ListItem) o;
        return quantity == item.quantity &&
                isBought == item.isBought &&
                listId == item.listId &&
                Double.compare(item.price, price) == 0 &&
                Objects.equals(product, item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, price, isBought, listId);
    }
}