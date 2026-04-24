package com.liner_exe.smartcart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ShoppingViewModel extends ViewModel {
    private final IShoppingRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    @Inject
    public ShoppingViewModel(IShoppingRepository repository) {
        this.repository = repository;
        subscribeToProducts();
    }

    private void subscribeToProducts() {
        disposable.add(repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_products::setValue));
    }

    public void deleteProductById(int id) {
        Completable.fromAction(() -> repository.deleteProductById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
