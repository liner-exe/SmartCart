package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.Category;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.Store;
import com.liner_exe.domain.models.ShoppingList;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IShoppingRepository {
    Flowable<List<ListItem>> getItemsForList(int listId);

    Completable updateItemStatus(int listId, int productId, boolean isBought);

    Completable addCategory(Category category);

    Flowable<List<Category>> getAllCategories();

    Single<Category> getCategoryById(int categoryId);

    Completable updateCategory(Category category);

    Completable deleteCategoryById(int id);

    Completable addStore(Store store);

    Completable updateStore(Store store);
}
