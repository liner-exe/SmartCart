package com.liner_exe.domain.models;

import java.util.List;

public class ShoppingListWithItems {
    private final ShoppingList shoppingList;
    private final List<Product> products;

    public ShoppingListWithItems(ShoppingList shoppingList, List<Product> products) {
        this.shoppingList = shoppingList;
        this.products = products;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public List<Product> getProducts() {
        return products;
    }
}
