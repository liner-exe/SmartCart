package com.liner_exe.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.entities.ProductEntity;

@Database(entities = {
        ProductEntity.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
