package com.raxors.dog_test.data.backend

import com.raxors.dog_test.data.dto.DogDTO
import com.raxors.dog_test.data.dto.DogImageDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendCaller {

    @GET("/api/breeds/list/all")
    fun getDogList(): Single<DogDTO>

    @GET("/api/breed/{breedName}/images")
    fun getAllBreedImages(@Path("breedName", encoded = true) breedName: String): Single<DogImageDTO>
}