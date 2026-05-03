package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.ListItemDao;
import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.dao.ShoppingListDao;
import com.liner_exe.data.local.dto.ListItemDto;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.mapper.ListItemMapper;
import com.liner_exe.data.mapper.ProductMapper;
import com.liner_exe.data.mapper.ShoppingListMapper;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public class ShoppingRepositoryImpl implements IShoppingRepository {
    private final ProductDao productDao;
    private final ShoppingListDao shoppingListDao;
    private final ListItemDao listItemDao;

    @Inject
    public ShoppingRepositoryImpl(ProductDao productDao,
                                  ShoppingListDao shoppingListDao,
                                  ListItemDao listItemDao) {
        this.productDao = productDao;
        this.shoppingListDao = shoppingListDao;
        this.listItemDao = listItemDao;
    }

    @Override
    public Completable addProduct(Product product) {
        return productDao.insert(ProductMapper.toEntity(product));
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
    public Completable updateProduct(Product product) {
        return productDao.update(ProductMapper.toEntity(product));
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
    public Completable updateList(ShoppingList shoppingList) {
        return shoppingListDao.update(ShoppingListMapper.toEntity(shoppingList));
    }

    @Override
    public Completable deleteListById(int id) {
        return shoppingListDao.deleteById(id);
    }

    @Override
    public Flowable<List<ListItem>> getItemsForList(int listId) {
        return listItemDao.getItemsForList(listId).map(ListItemMapper::toModelList);
    }
}
