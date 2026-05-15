package com.liner_exe.data.local.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "list_items",
    foreignKeys = {
        @ForeignKey(
            entity = ShoppingListEntity.class,
            parentColumns = "id",
            childColumns = "listId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = ProductEntity.class,
            parentColumns = "id",
            childColumns = "productId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = StoreEntity.class,
            parentColumns = "id",
            childColumns = "storeId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    }
)
public class ListItemEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int listId;
    public int productId;

    public Integer storeId;

    public int quantity;
    public double price;
    public boolean isChecked;

    public ListItemEntity(int id, int listId, int productId) {
        this.id = id;
        this.listId = listId;
        this.productId = productId;
        this.isChecked = false;
        this.storeId = null;
        this.quantity = 1;
    }

    @Ignore
    public ListItemEntity(int id, int listId, int productId, Integer storeId,
                          int quantity, double price, boolean isChecked) {
        this.id = id;
        this.listId = listId;
        this.productId = productId;
        this.storeId = storeId;
        this.quantity = quantity;
        this.price = price;
        this.isChecked = isChecked;
    }
}
