package com.example.dogbreedsproject.data.api

//import com.example.dogbreedsproject.data.model.DogBreedImageList
import com.example.dogbreedsproject.data.model.DogBreedImageListDTO
import com.example.dogbreedsproject.data.model.DogBreedsListDTO
//import com.example.dogbreedsproject.data.model.DogBreedsList
import com.example.dogbreedsproject.domain.model.DogBreedsList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("breeds/list/all")
    suspend fun fetchAllBreedsList(): DogBreedsListDTO


    @GET("breed/{name}/images/random/10")
    suspend fun fetchRandomBreedImages(@Path("name") name: String): DogBreedImageListDTO

    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }
}

