package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.liner_exe.data.local.entities.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CategoryDao {
    @Insert
    Completable insert(CategoryEntity category);

    @Query("SELECT * from categories")
    Flowable<List<CategoryEntity>> getAll();
}
