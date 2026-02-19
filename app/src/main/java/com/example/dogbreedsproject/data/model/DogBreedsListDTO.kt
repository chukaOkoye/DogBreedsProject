package com.example.dogbreedsproject.data.model


import com.google.gson.annotations.SerializedName

data class DogBreedsListDTO(
    @SerializedName("message")
    val message: Map<String, List<String>>,

    @SerializedName("status")
    val status: String
)

