package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.data.repository.DogBreedRepositoryImpl
import com.example.dogbreedsproject.domain.model.DogBreedsList
import javax.inject.Inject

class DogBreedListUseCase @Inject constructor(private val repositoryImpl: DogBreedRepositoryImpl){
    suspend operator fun invoke(): Result<DogBreedsList>{
        return repositoryImpl.getDogBreedList()
    }
}