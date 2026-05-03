package com.liner_exe.data.local.dto;

import androidx.room.ColumnInfo;

public class ListItemDto {
    @ColumnInfo(name = "productId")
    public int productId;

    @ColumnInfo(name = "listId")
    public int listId;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "isChecked")
    public boolean isChecked;
}
