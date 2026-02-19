package com.example.dogbreedsproject.presentation.dogbreedlist.mappers

import com.example.dogbreedsproject.domain.model.DogBreedsList

data class DogBreedListUIModel(
    val message: Map<String, List<String>>
)

fun DogBreedsList.toDogBreedListUIModel(): DogBreedListUIModel{
    return DogBreedListUIModel(
        message = message,
    )
}
