package com.liner_exe.data.mapper;

import com.liner_exe.data.local.entities.StoreEntity;
import com.liner_exe.domain.models.Store;

public class StoreMapper {
    public static Store toModel(StoreEntity entity) {
        if (entity == null) return null;

        return new Store(
            entity.id,
            entity.name
        );
    }

    public static StoreEntity toEntity(Store model) {
        if (model == null) return null;

        return new StoreEntity(
            model.getId(),
            model.getName()
        );
    }
}
