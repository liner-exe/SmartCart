package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.CategoryDao;
import com.liner_exe.data.local.dao.ListItemDao;
import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.dao.ShoppingListDao;
import com.liner_exe.data.local.dao.StoreDao;
import com.liner_exe.data.local.dto.ListItemDto;
import com.liner_exe.data.local.entities.CategoryEntity;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.mapper.CategoryMapper;
import com.liner_exe.data.mapper.ListItemMapper;
import com.liner_exe.data.mapper.ProductMapper;
import com.liner_exe.data.mapper.ShoppingListMapper;
import com.liner_exe.data.mapper.StoreMapper;
import com.liner_exe.domain.models.Category;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.models.Store;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class ShoppingRepositoryImpl implements IShoppingRepository {
    private final ProductDao productDao;
    private final ShoppingListDao shoppingListDao;
    private final ListItemDao listItemDao;
    private final CategoryDao categoryDao;
    private final StoreDao storeDao;

    @Inject
    public ShoppingRepositoryImpl(ProductDao productDao,
                                  ShoppingListDao shoppingListDao,
                                  ListItemDao listItemDao,
                                  CategoryDao categoryDao,
                                  StoreDao storeDao) {
        this.productDao = productDao;
        this.shoppingListDao = shoppingListDao;
        this.listItemDao = listItemDao;
        this.categoryDao = categoryDao;
        this.storeDao = storeDao;
    }

    @Override
    public Flowable<List<ListItem>> getItemsForList(int listId) {
        return listItemDao.getItemsForList(listId).map(ListItemMapper::toModelList);
    }

    @Override
    public Completable updateItemStatus(int listId, int productId, boolean isBought) {
        return listItemDao.updateItemStatus(listId, productId, isBought);
    }

    @Override
    public Completable addCategory(Category category) {
        return categoryDao.insert(CategoryMapper.toEntity(category));
    }

    @Override
    public Flowable<List<Category>> getAllCategories() {
        return categoryDao.getAll().map(CategoryMapper::toModelList);
    }

    @Override
    public Single<Category> getCategoryById(int id) {
        return Single.fromCallable(() -> {
            CategoryEntity category = categoryDao.findById(id);
            return CategoryMapper.toModel(category);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateCategory(Category category) {
        return categoryDao.update(CategoryMapper.toEntity(category));
    }

    @Override
    public Completable deleteCategoryById(int id) {
        return categoryDao.deleteById(id);
    }

    @Override
    public Completable addStore(Store store) {
        return storeDao.insert(StoreMapper.toEntity(store));
    }

    @Override
    public Completable updateStore(Store store) {
        return storeDao.update(StoreMapper.toEntity(store));
    }
}
