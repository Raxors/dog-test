package com.raxors.dog_test.domain.use_cases

import com.raxors.dog_test.data.entity.FavouriteMap
import io.reactivex.Observable

interface FavouriteUseCases {

    var favourites: FavouriteMap
    val favouriteObservable: Observable<FavouriteMap>
}