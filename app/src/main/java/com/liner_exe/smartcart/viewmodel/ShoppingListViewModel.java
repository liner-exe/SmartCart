package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingListRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ShoppingListViewModel extends BaseViewModel {
    private final IShoppingListRepository repository;

    private final MutableLiveData<List<ShoppingList>> _shoppingLists = new MutableLiveData<>();
    public LiveData<List<ShoppingList>> shoppingLists = _shoppingLists;


    @Inject
    public ShoppingListViewModel(IShoppingListRepository repository) {
        this.repository = repository;
        subscribeToLists();
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
                .subscribe(_shoppingLists::setValue,
                        throwable -> {
                            Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
