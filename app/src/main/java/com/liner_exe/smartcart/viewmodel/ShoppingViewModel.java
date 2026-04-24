package com.liner_exe.smartcart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingViewModel extends ViewModel {
    private final IShoppingRepository repository;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    public ShoppingViewModel(IShoppingRepository repository) {
        this.repository = repository;
    }

    public void loadProducts() {

    }
}
