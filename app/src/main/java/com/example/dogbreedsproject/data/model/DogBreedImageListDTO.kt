package com.example.dogbreedsproject.data.model

import com.google.gson.annotations.SerializedName

data class DogBreedImageListDTO(
    @SerializedName("message")
    val message: List<String>
)