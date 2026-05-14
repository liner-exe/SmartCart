package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.Store;
import com.liner_exe.domain.repository.IStoreRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class StoresViewModel extends BaseViewModel {
    private final IStoreRepository repository;

    private final MutableLiveData<List<Store>> _allStores = new MutableLiveData<>();

    private final MediatorLiveData<List<Store>> _filteredStores = new MediatorLiveData<>();
    public LiveData<List<Store>> filteredStores = _filteredStores;

    private final MutableLiveData<String> _searchQuery = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> _isDbEmpty = new MutableLiveData<>(true);
    public LiveData<Boolean> isDbEmpty = _isDbEmpty;

    @Inject
    public StoresViewModel(IStoreRepository repository) {
        this.repository = repository;

        _filteredStores.addSource(_allStores, stores -> performFilter());
        _filteredStores.addSource(_searchQuery, query -> performFilter());

        subscribeToStores();
    }

    public void setSearchQuery(String query) {
        _searchQuery.setValue(query);
    }

    public void performFilter() {
        List<Store> list = _allStores.getValue();
        String query = _searchQuery.getValue();

        if (list == null) {
            _filteredStores.setValue(new ArrayList<>());
            return;
        }

        if (query == null || query.isEmpty()) {
            _filteredStores.setValue(list);
        } else {
            List<Store> filtered = new ArrayList<>();
            String pattern = query.toLowerCase().trim();
            for (Store store : list) {
                if (store.getName().toLowerCase().contains(pattern)) {
                    filtered.add(store);
                }
            }
            _filteredStores.setValue(filtered);
        }
    }

    public void addStore(Store store) {
        executeCompletable(repository.add(store), "addStore");
    }

    public void updateStore(Store store) {
        executeCompletable(repository.update(store), "updateStore");
    }

    public void deleteStoreById(int id) {
        executeCompletable(repository.deleteById(id), "deleteStoreById");
    }

    private void subscribeToStores() {
        disposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stores -> {
                            _allStores.setValue(stores);
                            _isDbEmpty.setValue(stores == null || stores.isEmpty());
                        },
                        throwable -> {
                            Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
