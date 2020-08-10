package com.raxors.dog_test.data.repository.mapper

import com.raxors.dog_test.data.dto.DogImageDTO
import javax.inject.Inject

class DogImageMapper @Inject constructor() {
    fun DogImageDTO.toImageList(): List<String> {
        return message
    }
}