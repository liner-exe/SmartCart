package com.liner_exe.data.mapper;

import com.liner_exe.data.local.dto.ListItemDto;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ListItemMapper {
    public static ListItem toModel(ListItemDto dto) {
        return new ListItem(
                new Product(dto.productId, dto.productName),
                dto.quantity,
                dto.isChecked,
                dto.listId
        );
    }

    public static List<ListItem> toModelList(List<ListItemDto> dtos) {
        List<ListItem> listItems = new ArrayList<>();
        for (ListItemDto dto : dtos) {
            listItems.add(toModel(dto));
        }

        return listItems;
    }
}
