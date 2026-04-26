package com.liner_exe.data.mapper;

import com.liner_exe.data.local.entities.ShoppingListEntity;
import com.liner_exe.domain.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListMapper {
    public static ShoppingList toModel(ShoppingListEntity entity) {
        return new ShoppingList(
                entity.id,
                entity.name
        );
    }

    public static ShoppingListEntity toEntity(ShoppingList model) {
        return new ShoppingListEntity(
                model.getId(),
                model.getName()
        );
    }

    public static List<ShoppingList> toModelList(List<ShoppingListEntity> entities) {
        List<ShoppingList> models = new ArrayList<>();
        for (ShoppingListEntity entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }
}
