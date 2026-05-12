package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.ListItemDao;
import com.liner_exe.data.mapper.ListItemMapper;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.repository.IListItemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class ListItemRepositoryImpl implements IListItemRepository {
    private final ListItemDao dao;

    @Inject
    public ListItemRepositoryImpl(ListItemDao dao) {
        this.dao = dao;
    }

    @Override
    public Flowable<List<ListItem>> getItemsForList(int id) {
        return dao.getItemsForList(id).map(ListItemMapper::toModelList);
    }

    @Override
    public Completable updateItemStatus(int listId, int productId, boolean isBought) {
        return null;
    }
}
