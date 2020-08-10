package com.raxors.dog_test.data.repository.mapper

import com.google.gson.Gson
import com.raxors.dog_test.data.entity.FavouriteMap
import javax.inject.Inject

class FavouriteMapper @Inject constructor() {

    fun String.toFavouriteMap(): FavouriteMap {
        return Gson().fromJson(this, FavouriteMap::class.java)
    }

    fun FavouriteMap.toPref(): String {
        return Gson().toJson(this)
    }
}