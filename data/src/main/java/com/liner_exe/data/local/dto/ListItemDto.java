package com.liner_exe.data.local.dto;

import androidx.room.ColumnInfo;

public class ListItemDto {
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "productId")
    public int productId;

    @ColumnInfo(name = "listId")
    public int listId;

    @ColumnInfo(name = "categoryId")
    public Integer categoryId;

    @ColumnInfo(name = "storeId")
    public Integer storeId;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "isChecked")
    public boolean isChecked;
}
