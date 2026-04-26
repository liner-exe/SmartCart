package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.dao.ShoppingListDao;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.mapper.ProductMapper;
import com.liner_exe.data.mapper.ShoppingListMapper;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public class ShoppingRepositoryImpl implements IShoppingRepository {
    private final ProductDao productDao;
    private final ShoppingListDao shoppingListDao;

    @Inject
    public ShoppingRepositoryImpl(ProductDao productDao, ShoppingListDao shoppingListDao) {
        this.productDao = productDao;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    public Flowable<List<Product>> getAllProducts() {
        return productDao.getAll().map(ProductMapper::toModelList);
    }

    @Override
    public Product getProductById(int id) {
        ProductEntity product = productDao.findById(id);
        return ProductMapper.toModel(product);
    }

    @Override
    public Completable deleteProductById(int id) {
        return productDao.deleteById(id);
    }

    @Override
    public Flowable<List<ShoppingList>> getAllLists() {
        return shoppingListDao.getAllLists().map(ShoppingListMapper::toModelList);
    }

    @Override
    public Completable addList(ShoppingList shoppingList) {
        return shoppingListDao.insert(ShoppingListMapper.toEntity(shoppingList));
    }

    @Override
    public List<ListItem> getAllListItems() {
        return Collections.emptyList();
    }

    @Override
    public ListItem getItemsForList(int listId) {
        return null;
    }
}
