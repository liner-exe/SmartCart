package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.liner_exe.data.local.dto.ListItemDto;
import com.liner_exe.data.local.entities.ListItemEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ListItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(ListItemEntity listItem);

    @Query("SELECT " +
            "li.id AS id, " +
            "p.id AS productId, " +
            "li.listId AS listId, " +
            "p.categoryId AS categoryId, " +
            "li.storeId AS storeId, " +
            "p.name AS productName, " +
            "li.quantity AS quantity, " +
            "li.price AS price, " +
            "li.isChecked AS isChecked " +
            "FROM list_items li " +
            "LEFT JOIN products p ON li.productId = p.id " +
            "WHERE li.listId = :listId"
    )
    Flowable<List<ListItemDto>> getItemsForList(int listId);

    @Query("UPDATE list_items SET isChecked = :isBought " +
            "WHERE productId = :productId " +
            "AND listId = :listId")
    Completable updateItemStatus(int listId, int productId, boolean isBought);
}
