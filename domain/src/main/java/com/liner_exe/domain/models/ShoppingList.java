package com.liner_exe.domain.models;

import java.util.List;

public class ShoppingList {
    private final int id;
    private String name;
    private List<Product> products;


    public ShoppingList(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

//    public long getChecked() {
//        return this.products.stream().filter(Product::isChecked).count();
//    }

//    public int getProgress() {
//        if (this.products.isEmpty()) {
//            return 0;
//        }
//
//        return (int) (this.getChecked() * 100 / this.products.size());
//    }
}
