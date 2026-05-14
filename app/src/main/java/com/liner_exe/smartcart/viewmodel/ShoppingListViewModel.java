package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ShoppingListViewModel extends BaseViewModel {
    private final IShoppingListRepository repository;

    private final MutableLiveData<List<ShoppingList>> _allShoppingLists = new MutableLiveData<>();

    private final MediatorLiveData<List<ShoppingList>> _filteredShoppingLists = new MediatorLiveData<>();
    public LiveData<List<ShoppingList>> filteredShoppingLists = _filteredShoppingLists;

    private final MutableLiveData<String> _searchQuery = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> _isDbEmpty = new MutableLiveData<>(true);
    public LiveData<Boolean> isDbEmpty = _isDbEmpty;


    @Inject
    public ShoppingListViewModel(IShoppingListRepository repository) {
        this.repository = repository;

        _filteredShoppingLists.addSource(_allShoppingLists, stores -> performFilter());
        _filteredShoppingLists.addSource(_searchQuery, query -> performFilter());

        subscribeToLists();
    }

    public void setSearchQuery(String query) {
        _searchQuery.setValue(query);
    }

    public void performFilter() {
        List<ShoppingList> list = _allShoppingLists.getValue();
        String query = _searchQuery.getValue();

        if (list == null) {
            _filteredShoppingLists.setValue(new ArrayList<>());
            return;
        }

        if (query == null || query.isEmpty()) {
            _filteredShoppingLists.setValue(list);
        } else {
            List<ShoppingList> filtered = new ArrayList<>();
            String pattern = query.toLowerCase().trim();
            for (ShoppingList shoppingList : list) {
                if (shoppingList.getName().toLowerCase().contains(pattern)) {
                    filtered.add(shoppingList);
                }
            }
            _filteredShoppingLists.setValue(filtered);
        }
    }

    public void addList(ShoppingList shoppingList) {
        executeCompletable(repository.add(shoppingList), "addList");
    }

    public void updateList(ShoppingList shoppingList) {
        executeCompletable(repository.update(shoppingList), "updateList");
    }

    public void deleteListById(int id) {
        executeCompletable(repository.deleteById(id), "deleteListById");
    }

    private void subscribeToLists() {
        disposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shoppingLists -> {
                            _allShoppingLists.setValue(shoppingLists);
                            _isDbEmpty.setValue(shoppingLists == null || shoppingLists.isEmpty());
                        },
                        throwable -> {
                            Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
