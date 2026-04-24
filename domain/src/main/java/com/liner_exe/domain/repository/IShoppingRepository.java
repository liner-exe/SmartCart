package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IShoppingRepository {
    Flowable<List<Product>> getAllProducts();
    Product getProductById(int id);

    List<ListItem> getAllListItems();
    ListItem getItemsForList(int listId);
}
