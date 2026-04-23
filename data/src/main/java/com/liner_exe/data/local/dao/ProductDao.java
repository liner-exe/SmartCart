package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.liner_exe.data.local.entities.ProductEntity;

@Dao
public interface ProductDao {
    @Insert
    void insertAll(ProductEntity... products);

    @Insert
    void insert(ProductEntity product);
}
