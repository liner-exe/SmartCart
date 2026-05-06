package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.Category;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface IShoppingRepository {
    Completable addProduct(Product product);

    Flowable<List<Product>> getAllProducts();

    Product getProductById(int id);

    Completable updateProduct(Product product);

    Completable deleteProductById(int id);

    Flowable<List<ShoppingList>> getAllLists();
    Completable addList(ShoppingList shoppingList);

    Completable updateList(ShoppingList shoppingList);

    Completable deleteListById(int id);

    Flowable<List<ListItem>> getItemsForList(int listId);

    Completable updateItemStatus(int listId, int productId, boolean isBought);

    Completable addCategory(Category category);

    Flowable<List<Category>> getAllCategories();

    Completable updateCategory(Category category);

    Completable deleteCategoryById(int id);
}
