package com.liner_exe.domain.models;

public class ListItem {
    private final Product product;
    private final int quantity;
    private boolean isBought;
    private final int listId;

    public ListItem(Product product, int quantity, boolean isBought, int listId) {
        this.product = product;
        this.quantity = quantity;
        this.isBought = isBought;
        this.listId = listId;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public boolean isBought() { return isBought; }
    public int getListId() { return listId; }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}