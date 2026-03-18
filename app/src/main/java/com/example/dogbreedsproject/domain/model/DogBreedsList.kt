package com.example.dogbreedsproject.domain.model

import com.example.dogbreedsproject.data.model.DogBreedsListDTO


data class DogBreedsList(
    val message: Map<String, List<String>>,
    val status: String
)



