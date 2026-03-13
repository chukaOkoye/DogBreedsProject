package com.example.dogbreedsproject.domain.repository

import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.model.DogBreedSearchResult
import com.example.dogbreedsproject.domain.model.DogBreedsList
import kotlinx.coroutines.flow.Flow

interface DogBreedRepository{
    suspend fun getDogBreedList(): Result<DogBreedsList>

    suspend fun getDogBreedRandomImage(breedName: String): Result<DogBreedImageList>

    fun getDogBreedSearchQuery(input: String): Flow<DogBreedSearchResult>
}