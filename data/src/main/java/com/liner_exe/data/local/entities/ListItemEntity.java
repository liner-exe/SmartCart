package com.liner_exe.data.local.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
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

    public ListItemEntity(int listId, int productId) {
        this.listId = listId;
        this.productId = productId;
        this.isChecked = false;
        this.storeId = null;
        this.quantity = 1;
    }
}
