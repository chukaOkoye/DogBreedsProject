package com.example.dogbreedsproject

//import com.example.dogbreedsproject.data.model.DogBreedImageList
//import com.example.dogbreedsproject.data.model.DogBreedsList
import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.model.DogBreedsList
import com.example.dogbreedsproject.presentation.dogbreedlist.mappers.DogBreedListUIModel
import com.example.dogbreedsproject.presentation.dogbreedrandom.mappers.DogBreedImageListUIModel

object MockedDogBreeds {

    val mockedDogBreeds = DogBreedsList(
        mapOf(
            "dalmatian" to listOf(""),
            "akita" to listOf(""),
            "staffy" to listOf("")
        ),
        status = "success"
    )

    val mockedDogBreedUIModel = DogBreedListUIModel(
        mapOf(
            "dalmatian" to listOf(""),
            "akita" to listOf(""),
            "staffy" to listOf("")
        )
    )

    val mockedDogImageList = DogBreedImageList(
        listOf(
            "https://images.dog.ceo/breeds/dalmatian/cooper1.jpg",
            "https://images.dog.ceo/breeds/dalmatian/cooper2.jpg"
        )
    )

    val mockedDogImageListUIModel = DogBreedImageListUIModel(
        listOf(
            "https://images.dog.ceo/breeds/dalmatian/cooper1.jpg",
            "https://images.dog.ceo/breeds/dalmatian/cooper2.jpg"
        )
    )
}