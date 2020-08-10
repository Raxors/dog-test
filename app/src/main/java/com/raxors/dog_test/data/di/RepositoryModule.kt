package com.raxors.dog_test.data.di

import com.raxors.dog_test.data.repository.DogRepositoryImpl
import com.raxors.dog_test.data.repository.FavouriteRepositoryImpl
import com.raxors.dog_test.domain.use_cases.DogRepository
import com.raxors.dog_test.domain.use_cases.FavouriteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBackendApi(dogRepositoryImpl: DogRepositoryImpl): DogRepository

    @Binds
    @Singleton
    abstract fun provideFavouriteRepo(favouriteRepositoryImpl: FavouriteRepositoryImpl): FavouriteRepository

}