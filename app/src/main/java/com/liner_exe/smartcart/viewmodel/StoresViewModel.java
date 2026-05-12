package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.Store;
import com.liner_exe.domain.repository.IStoreRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class StoresViewModel extends BaseViewModel {
    private final IStoreRepository repository;

    private final MutableLiveData<List<Store>> _stores = new MutableLiveData<>();
    public LiveData<List<Store>> stores = _stores;

    @Inject
    public StoresViewModel(IStoreRepository repository) {
        this.repository = repository;
        subscribeToStores();
    }

    public void addStore(Store store) {
        executeCompletable(repository.addStore(store), "addStore");
    }

    private void subscribeToStores() {
        disposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _stores::setValue,
                        throwable -> {
                            Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
