package com.example.dogbreedsproject.presentation.dogbreedrandomimages.mappers

import com.example.dogbreedsproject.domain.model.DogBreedImageList

data class DogBreedImageListUIModel(
    val message: List<String>
)

fun DogBreedImageList.toDogBreedImageListUIModel(): DogBreedImageListUIModel{
    return DogBreedImageListUIModel(
        message = message
    )
}