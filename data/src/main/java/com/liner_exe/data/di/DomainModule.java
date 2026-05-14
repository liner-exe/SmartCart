package com.liner_exe.data.di;

import com.liner_exe.domain.usecases.GetGrouppedShoppingListsUseCase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DomainModule {
    @Provides
    @Singleton
    public GetGrouppedShoppingListsUseCase provideGetGrouppedShoppingListsUseCase() {
        return new GetGrouppedShoppingListsUseCase();
    }
}
