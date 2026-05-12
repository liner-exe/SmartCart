package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.StoreDao;
import com.liner_exe.data.mapper.StoreMapper;
import com.liner_exe.domain.models.Store;
import com.liner_exe.domain.repository.IStoreRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class StoreRepositoryImpl implements IStoreRepository {
    private final StoreDao dao;

    @Inject
    public StoreRepositoryImpl(StoreDao dao) {
        this.dao = dao;
    }

    @Override
    public Completable addStore(Store store) {
        return dao.insert(StoreMapper.toEntity(store));
    }

    @Override
    public Flowable<List<Store>> getAll() {
        return dao.getAll().map(StoreMapper::toModelList);
    }

    @Override
    public Completable updateStore(Store store) {
        return dao.update(StoreMapper.toEntity(store));
    }
}
