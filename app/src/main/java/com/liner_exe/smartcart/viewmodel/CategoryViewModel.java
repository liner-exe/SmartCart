package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.Category;
import com.liner_exe.domain.repository.ICategoryRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CategoryViewModel extends BaseViewModel {
    private final ICategoryRepository repository;

    private final MutableLiveData<List<Category>> _categories = new MutableLiveData<>();
    public LiveData<List<Category>> categories = _categories;

    private final MutableLiveData<Category> _selectedCategory = new MutableLiveData<>();
    public LiveData<Category> selectedCategory = _selectedCategory;

    @Inject
    public CategoryViewModel(ICategoryRepository repository) {
        this.repository = repository;
        subscribeToCategories();
    }

    public void addCategory(Category category) {
        executeCompletable(repository.add(category), "addCategory");
    }

    public void getCategory(int id) {
        disposable.add(repository.findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _selectedCategory::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }

    public void updateCategory(Category category) {
        executeCompletable(repository.update(category), "updateCategory");
    }

    public void deleteCategoryById(int id) {
        executeCompletable(repository.deleteById(id), "deleteCategoryById");
    }

    private void subscribeToCategories() {
        disposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _categories::setValue,
                        throwable -> {
                            Log.e("DB_ERROR", "error vm: " + throwable.getMessage());
                        }
                ));
    }
}
