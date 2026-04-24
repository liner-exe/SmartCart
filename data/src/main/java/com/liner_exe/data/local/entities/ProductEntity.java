package com.liner_exe.data.local.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public ProductEntity() {

    }

    @Ignore
    public ProductEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
