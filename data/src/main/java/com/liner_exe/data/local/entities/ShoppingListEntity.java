package com.liner_exe.data.local.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_lists")
public class ShoppingListEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public ShoppingListEntity() {

    }

    @Ignore
    public ShoppingListEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
