package com.liner_exe.smartcart.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.repository.IListItemRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class ListItemsViewModel extends BaseViewModel {
    private final IListItemRepository repository;

    private final MutableLiveData<List<ListItem>> _listItems = new MutableLiveData<>();
    public LiveData<List<ListItem>> listItems = _listItems;

    private final BehaviorSubject<Integer> _currentListId = BehaviorSubject.create();

    @Inject
    public ListItemsViewModel(IListItemRepository repository) {
        this.repository = repository;
        observeCurrentList();
    }

    public void addProductAsListItem(Product product) {
        Integer currentId = _currentListId.getValue();

        if (currentId != null) {
            ListItem newItem = new ListItem(currentId, product);
            executeCompletable(repository.add(newItem), "addListItem");
        }
    }

    public void setCurrentListId(int listId) {
        _currentListId.onNext(listId);
    }

    public void toggleItemStatus(ListItem listItem) {
        boolean newStatus = !listItem.isBought();
        executeCompletable(repository.updateItemStatus(
                listItem.getId(),
                listItem.getListId(),
                listItem.getProduct().getId(),
                newStatus), "toggleItemStatus");
    }

    private void observeCurrentList() {
        disposable.add(_currentListId
            .distinctUntilChanged()
            .switchMap(id -> repository.getItemsForList(id).toObservable())
            .subscribe(
                    _listItems::postValue,
                    throwable -> { Log.e("SC_DB_ERROR", "error vm: " + throwable.getMessage()); }
            )
        );
    }
}