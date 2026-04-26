package com.liner_exe.smartcart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ShoppingViewModel extends ViewModel {
    private final IShoppingRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    private final MutableLiveData<List<ShoppingList>> _shoppingLists = new MutableLiveData<>();
    public LiveData<List<ShoppingList>> shoppingLists = _shoppingLists;

    @Inject
    public ShoppingViewModel(IShoppingRepository repository) {
        this.repository = repository;
        subscribeToProducts();
        subscribeToLists();
    }

    private void subscribeToProducts() {
        disposable.add(repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_products::setValue));
    }

    private void subscribeToLists() {
        disposable.add(repository.getAllLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_shoppingLists::setValue));
    }

    public void addList(ShoppingList shoppingList) {
       disposable.add(repository.addList(shoppingList)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe());
    }

    public void deleteProductById(int id) {
        disposable.add(repository.deleteProductById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
