package com.liner_exe.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.local.entities.ShopEntity;
import com.liner_exe.data.local.entities.ShoppingListEntity;

@Database(entities = {
        ProductEntity.class,
        ShopEntity.class,
        ShoppingListEntity.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
