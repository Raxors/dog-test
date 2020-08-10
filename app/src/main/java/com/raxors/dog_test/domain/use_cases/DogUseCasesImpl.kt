package com.raxors.dog_test.domain.use_cases

import com.raxors.dog_test.data.entity.Dog
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogUseCasesImpl @Inject constructor(private val dogRepository: DogRepository): DogUseCases {
    override fun getAllDogs(): Single<List<Dog>> {
        return dogRepository.getAllDogs()
    }

    override fun getAllBreedImages(breedName: String): Single<List<String>> {
        return dogRepository.getAllBreedImages(breedName)
    }
}