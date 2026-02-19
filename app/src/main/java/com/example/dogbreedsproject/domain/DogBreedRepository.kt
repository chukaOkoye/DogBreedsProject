package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.model.DogBreedsList

interface DogBreedRepository{
    suspend fun getDogBreedList(): Result<DogBreedsList>

    suspend fun getDogBreedRandomImage(breedName: String): Result<DogBreedImageList>
}