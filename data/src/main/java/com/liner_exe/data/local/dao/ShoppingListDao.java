package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.liner_exe.data.local.entities.ListItemEntity;
import com.liner_exe.data.local.entities.ShoppingListEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ShoppingListDao {
    @Insert
    Completable insert(ShoppingListEntity shoppingList);

    @Query("SELECT * from shopping_lists")
    Flowable<List<ShoppingListEntity>> getAllLists();

    @Update
    Completable update(ShoppingListEntity shoppingList);

    @Query("DELETE from shopping_lists WHERE id = :id")
    Completable deleteById(int id);

    // ListItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertListItem(ListItemEntity listItem);

    @Query("DELETE FROM list_items WHERE listId = :listId AND productId = :productId")
    Completable deleteProductFromList(int listId, int productId);
}
