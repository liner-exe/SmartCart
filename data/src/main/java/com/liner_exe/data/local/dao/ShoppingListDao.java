package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.liner_exe.data.local.entities.ShoppingListEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ShoppingListDao {
    @Query("SELECT * from shopping_lists")
    Flowable<List<ShoppingListEntity>> getAllLists();
}
