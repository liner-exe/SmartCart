package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;

import java.util.List;

public interface IShoppingRepository {
    List<Product> getAllProducts();
    Product getProductById(int id);

    List<ListItem> getAllListItems();
    ListItem getItemsForList(int listId);
}
