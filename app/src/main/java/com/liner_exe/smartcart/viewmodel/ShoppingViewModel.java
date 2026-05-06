package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liner_exe.domain.models.Category;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class ShoppingViewModel extends ViewModel {
    private final IShoppingRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    private final MutableLiveData<List<ShoppingList>> _shoppingLists = new MutableLiveData<>();
    public LiveData<List<ShoppingList>> shoppingLists = _shoppingLists;

    private final MutableLiveData<List<ListItem>> _listItems = new MutableLiveData<>();
    public LiveData<List<ListItem>> listItems = _listItems;

    private final MutableLiveData<List<Category>> _categories = new MutableLiveData<>();
    public LiveData<List<Category>> categories = _categories;

    private final BehaviorSubject<Integer> currentListId = BehaviorSubject.create();

    @Inject
    public ShoppingViewModel(IShoppingRepository repository) {
        this.repository = repository;
        subscribeToProducts();
        subscribeToLists();
        subscribeToListItems();
        subscribeToCategories();
    }

    private void subscribeToProducts() {
        disposable.add(repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_products::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    private void subscribeToLists() {
        disposable.add(repository.getAllLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _shoppingLists::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }));
    }

    private void subscribeToListItems() {
        disposable.add(currentListId
                .toFlowable(BackpressureStrategy.LATEST)
                .distinctUntilChanged()
                .switchMap(id -> repository.getItemsForList(id)
                        .subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _listItems::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }));
    }

    private void subscribeToCategories() {
        disposable.add(repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _categories::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void addList(ShoppingList shoppingList) {
        disposable.add(repository.addList(shoppingList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void updateList(ShoppingList shoppingList) {
        disposable.add(repository.updateList(shoppingList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void deleteListById(int id) {
        disposable.add(repository.deleteListById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void addProduct(Product product) {
        disposable.add(repository.addProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void updateProduct(Product product) {
        disposable.add(repository.updateProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void deleteProductById(int id) {
        disposable.add(repository.deleteProductById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void addCategory(Category category) {
        disposable.add(repository.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void updateCategory(Category category) {
        disposable.add(repository.updateCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void deleteCategoryById(int id) {
        disposable.add(repository.deleteCategoryById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {},
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void toggleItemStatus(ListItem item) {
        boolean newStatus = !item.isBought();
        disposable.add(repository.updateItemStatus(
                        item.getListId(),
                        item.getProduct().getId(),
                        newStatus
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void setCurrentListId(int listId) {
        currentListId.onNext(listId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}