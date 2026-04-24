package com.liner_exe.domain.models;

public class ListItem {
    private final int id;

    private int listId;
    private String name;
    private int quantity;
    private boolean isBought;

    public ListItem(int id, int listId, String name, int quantity, boolean isBought) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.quantity = quantity;
        this.isBought = isBought;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}