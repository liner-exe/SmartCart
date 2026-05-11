package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.repository.IProductRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ProductViewModel extends BaseViewModel {
    private final IProductRepository repository;

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    @Inject
    public ProductViewModel(IProductRepository repository) {
        this.repository = repository;
        subscribeToProducts();
    }

    public void addProduct(Product product) {
        executeCompletable(repository.add(product), "addProduct");
    }

    public void updateProduct(Product product) {
        executeCompletable(repository.update(product), "updateProduct");
    }

    public void deleteProductById(int id) {
        executeCompletable(repository.deleteById(id), "deleteProductById");
    }

    private void subscribeToProducts() {
        disposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_products::setValue,
                        throwable -> {
                            Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
