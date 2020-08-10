package com.raxors.dog_test.domain.use_cases

import com.raxors.dog_test.data.entity.Dog
import io.reactivex.Single

interface DogUseCases {
    fun getAllDogs(): Single<List<Dog>>

    fun getAllBreedImages(breedName: String): Single<List<String>>
}