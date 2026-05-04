package com.liner_exe.domain.models;

public class ListItem {
    private final Product product;
    private int quantity;

    private double price;
    private boolean isBought;
    private final int listId;

    public ListItem(Product product, int quantity, double price, boolean isBought, int listId) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.isBought = isBought;
        this.listId = listId;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBought() { return isBought; }
    public int getListId() { return listId; }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}