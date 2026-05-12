package com.liner_exe.domain.repository;

import com.liner_exe.domain.models.Store;

import io.reactivex.rxjava3.core.Completable;

public interface IStoreRepository {
    Completable addStore(Store store);

    Completable updateStore(Store store);
}
