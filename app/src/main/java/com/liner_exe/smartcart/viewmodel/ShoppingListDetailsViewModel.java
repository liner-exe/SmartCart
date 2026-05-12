package com.liner_exe.smartcart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.repository.IListItemRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class ShoppingListDetailsViewModel extends BaseViewModel {
    private final IListItemRepository repository;

    private final MutableLiveData<List<ListItem>> _listItems = new MutableLiveData<>();
    public LiveData<List<ListItem>> listItems = _listItems;

    private final BehaviorSubject<Integer> _currentListId = BehaviorSubject.create();

    @Inject
    public ShoppingListDetailsViewModel(IListItemRepository repository) {
        this.repository = repository;
    }

    public void setCurrentListId(int listId) {

    }

    public void toggleItemStatus(ListItem listItem) {
        boolean newStatus = !listItem.isBought();
        executeCompletable(repository.updateItemStatus(
                listItem.getListId(),
                listItem.getProduct().getId(),
                newStatus),"toggleItemStatus");
    }
}