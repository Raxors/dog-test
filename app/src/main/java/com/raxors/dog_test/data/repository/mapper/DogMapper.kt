package com.raxors.dog_test.data.repository.mapper

import com.raxors.dog_test.data.dto.DogDTO
import com.raxors.dog_test.data.dto.DogImageDTO
import com.raxors.dog_test.data.entity.Dog
import java.util.*
import java.util.function.Consumer
import javax.inject.Inject

class DogMapper @Inject constructor() {
    fun DogDTO.toDogList(): List<Dog> {
        val keyList: ArrayList<String> = ArrayList(this.message.keys)
        val dogList: ArrayList<Dog> = ArrayList()
        keyList.forEach(Consumer { dogName: String ->
            this.message[dogName]?.let { Dog(dogName, it) }?.let { dogList.add(it) }
        })
        return dogList
    }

    fun DogImageDTO.toImageList(): List<String> {
        return message
    }
}