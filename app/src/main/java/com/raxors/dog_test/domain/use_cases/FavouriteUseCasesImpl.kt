package com.raxors.dog_test.domain.use_cases

import com.raxors.dog_test.data.entity.FavouriteMap
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteUseCasesImpl @Inject constructor(private val favouriteRepository: FavouriteRepository): FavouriteUseCases {

    override var favourites: FavouriteMap
        get() = favouriteRepository.favourites
        set(value) {favouriteRepository.favourites = value}
    override val favouriteObservable: Observable<FavouriteMap>
        get() = favouriteRepository.favouriteObservable
}