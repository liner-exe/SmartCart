package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface IStoreRepository {
    Completable addStore(Store store);

    Flowable<List<Store>> getAll();

    Completable updateStore(Store store);
}
