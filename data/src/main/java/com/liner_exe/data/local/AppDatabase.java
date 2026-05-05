package com.liner_exe.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.liner_exe.data.local.dao.ListItemDao;
import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.dao.ShoppingListDao;
import com.liner_exe.data.local.entities.CategoryEntity;
import com.liner_exe.data.local.entities.ListItemEntity;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.local.entities.ShoppingListEntity;

@Database(entities = {
        ProductEntity.class,
        ShoppingListEntity.class,
        ListItemEntity.class,
        CategoryEntity.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "smartcart_db";

    public abstract ProductDao productDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ListItemDao listItemDao();
}
