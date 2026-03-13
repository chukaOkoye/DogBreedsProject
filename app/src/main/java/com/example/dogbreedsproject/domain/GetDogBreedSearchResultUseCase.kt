package com.example.dogbreedsproject.domain

import com.example.dogbreedsproject.domain.model.DogBreedSearchResult
import com.example.dogbreedsproject.domain.repository.DogBreedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogBreedSearchResultUseCase @Inject constructor(private val dogBreedRepository: DogBreedRepository) {

    operator fun invoke(query: String): Flow<DogBreedSearchResult> =
        dogBreedRepository.getDogBreedSearchQuery(query)

}