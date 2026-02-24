package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.domain.model.DogBreedsList
import com.example.dogbreedsproject.domain.repository.DogBreedRepository
import javax.inject.Inject

class GetDogBreedListUseCase @Inject constructor(private val repository: DogBreedRepository){
    suspend operator fun invoke(): Result<DogBreedsList>{
        return repository.getDogBreedList()
    }
}