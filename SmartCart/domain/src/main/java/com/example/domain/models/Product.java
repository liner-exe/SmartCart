package com.example.domain.models;

public class Product {
    private int id;
    private String name;
    private boolean isChecked;
    private int amount;

    public Product(int id, String name, boolean isChecked, int amount) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
