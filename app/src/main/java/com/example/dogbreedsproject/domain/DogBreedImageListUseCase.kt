package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.data.repository.DogBreedRepositoryImpl
import com.example.dogbreedsproject.domain.model.DogBreedImageList
import javax.inject.Inject

class DogBreedImageListUseCase @Inject constructor(private val repositoryImpl: DogBreedRepositoryImpl) {

    suspend operator fun invoke(breedName: String): Result<DogBreedImageList> {
        return repositoryImpl.getDogBreedRandomImage(breedName)

    }
}