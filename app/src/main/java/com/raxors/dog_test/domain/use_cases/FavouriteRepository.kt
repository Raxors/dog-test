package com.raxors.dog_test.domain.use_cases

import com.raxors.dog_test.data.entity.FavouriteMap
import io.reactivex.Observable

interface FavouriteRepository {

    var favourites: FavouriteMap
    val favouriteObservable: Observable<FavouriteMap>
}