package com.example.dogbreedsproject.presentation.dogbreedsearch.mappers

import com.example.dogbreedsproject.domain.model.DogBreedSearchResult

data class DogBreedSearchResultUIModel(
    val message: Map<String, List<String>>
)

fun DogBreedSearchResult.toSearchResultUIState(): DogBreedSearchResultUIModel{
    return DogBreedSearchResultUIModel(
        message = message
    )
}