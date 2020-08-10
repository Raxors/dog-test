package com.raxors.dog_test.data.backend

import com.raxors.dog_test.data.dto.DogDTO
import com.raxors.dog_test.data.dto.DogImageDTO
import io.reactivex.Single

interface BackendApi {
    fun getAllDogs(): Single<DogDTO>

    fun getAllBreedImages(breedName: String): Single<DogImageDTO>
}