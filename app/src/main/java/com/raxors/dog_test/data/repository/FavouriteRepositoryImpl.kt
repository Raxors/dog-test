package com.raxors.dog_test.data.repository

import com.raxors.dog_test.data.entity.FavouriteMap
import com.raxors.dog_test.data.preferences.PreferencesApi
import com.raxors.dog_test.data.repository.mapper.FavouriteMapper
import com.raxors.dog_test.domain.use_cases.FavouriteRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepositoryImpl @Inject constructor(private val mapper: FavouriteMapper, private val preferencesApi: PreferencesApi) :
    FavouriteRepository {

    override var favourites: FavouriteMap
        get() = with(mapper) { preferencesApi.favourites.toFavouriteMap() }
        set(value) {
            with(mapper) { preferencesApi.favourites = value.toPref() }
        }

    override val favouriteObservable: Observable<FavouriteMap>
        get() = with(mapper) {
            preferencesApi.favouriteObservable.map { it.toFavouriteMap() }
        }
}