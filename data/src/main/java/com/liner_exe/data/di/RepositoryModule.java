package com.liner_exe.data.di;

import com.liner_exe.data.repository.ProductRepositoryImpl;
import com.liner_exe.data.repository.ShoppingListRepositoryImpl;
import com.liner_exe.data.repository.ShoppingRepositoryImpl;
import com.liner_exe.domain.repository.IProductRepository;
import com.liner_exe.domain.repository.IShoppingListRepository;
import com.liner_exe.domain.repository.IShoppingRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract IShoppingRepository bindRepository(ShoppingRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract IProductRepository bindProductRepository(ProductRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract IShoppingListRepository bindShoppingListRepository(ShoppingListRepositoryImpl impl);
}
