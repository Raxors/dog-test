package com.raxors.dog_test.data.repository

import com.raxors.dog_test.data.backend.BackendApi
import com.raxors.dog_test.data.entity.Dog
import com.raxors.dog_test.data.repository.mapper.DogMapper
import com.raxors.dog_test.domain.use_cases.DogRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepositoryImpl @Inject constructor(private val backendApi: BackendApi, private val mapper: DogMapper) :
    DogRepository {
    override fun getAllDogs(): Single<List<Dog>> {
        return backendApi.getAllDogs().map { with(mapper) { it.toDogList() } }
    }

    override fun getAllBreedImages(breedName: String): Single<List<String>> {
        return backendApi.getAllBreedImages(breedName).map { with(mapper) {it.toImageList()} }
    }
}