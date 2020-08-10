package com.raxors.dog_test.domain.di

import com.raxors.dog_test.domain.use_cases.DogUseCases
import com.raxors.dog_test.domain.use_cases.DogUseCasesImpl
import com.raxors.dog_test.domain.use_cases.FavouriteUseCases
import com.raxors.dog_test.domain.use_cases.FavouriteUseCasesImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun provideDogUseCases(dogUseCasesImpl: DogUseCasesImpl): DogUseCases

    @Binds
    @Singleton
    abstract fun provideFavouriteUseCases(favouriteUseCasesImpl: FavouriteUseCasesImpl): FavouriteUseCases
}