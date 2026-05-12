package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface IListItemRepository {
    Completable add(ListItem listItem);

    Flowable<List<ListItem>> getItemsForList(int id);

    Completable updateItemStatus(int listId, int productId, boolean isBought);
}
