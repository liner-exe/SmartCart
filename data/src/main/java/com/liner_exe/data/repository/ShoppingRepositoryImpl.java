package com.liner_exe.data.repository;

import com.liner_exe.data.local.dao.ProductDao;
import com.liner_exe.data.local.entities.ProductEntity;
import com.liner_exe.data.mapper.ProductMapper;
import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.models.Product;
import com.liner_exe.domain.repository.IShoppingRepository;

import java.util.Collections;
import java.util.List;

public class ShoppingRepositoryImpl implements IShoppingRepository {
    private final ProductDao productDao;

    public ShoppingRepositoryImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> products = productDao.getAll();
        return ProductMapper.toModelList(products);
    }

    @Override
    public Product getProductById(int id) {
        ProductEntity product = productDao.findById(id);
        return ProductMapper.toModel(product);
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
