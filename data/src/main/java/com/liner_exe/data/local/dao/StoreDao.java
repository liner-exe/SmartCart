package com.liner_exe.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.liner_exe.data.local.entities.StoreEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface StoreDao {
    @Insert
    Completable insert(StoreEntity shop);

    @Query("SELECT * FROM stores")
    Flowable<StoreEntity> getAll();

    @Update
    Completable update(StoreEntity shop);
}
