package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.repository.DogBreedRepository
import javax.inject.Inject

class GetDogBreedImageListUseCase @Inject constructor(private val repository: DogBreedRepository) {

    suspend operator fun invoke(breedName: String): Result<DogBreedImageList> {
        return repository.getDogBreedRandomImage(breedName)

    }
}