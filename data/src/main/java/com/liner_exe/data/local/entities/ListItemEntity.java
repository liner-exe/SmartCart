package com.liner_exe.data.local.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
    tableName = "list_items",
    primaryKeys = {"listId", "productId"},
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
        )
    }
)
public class ListItemEntity {
    public int listId;
    public int productId;

    public int quantity;
    public boolean isChecked;

    public ListItemEntity(int listId, int productId) {
        this.listId = listId;
        this.productId = productId;
        this.isChecked = false;
    }
}
