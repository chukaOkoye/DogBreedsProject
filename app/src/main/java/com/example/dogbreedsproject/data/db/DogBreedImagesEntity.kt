package com.example.dogbreedsproject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dogbreedsproject.data.model.DogBreedImageListDTO

@Entity(tableName = "dogBreedImages")
data class DogBreedImagesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: List<String>
)

fun DogBreedImageListDTO.toDogBreedImagesEntity(): DogBreedImagesEntity{
    return DogBreedImagesEntity(
        message = message
    )
}