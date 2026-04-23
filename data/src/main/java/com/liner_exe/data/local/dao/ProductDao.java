package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.liner_exe.data.local.entities.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertAll(ProductEntity... products);

    @Insert
    void insert(ProductEntity product);

    @Query("SELECT * from products WHERE name = :name")
    List<ProductEntity> findByName(String name);
}
