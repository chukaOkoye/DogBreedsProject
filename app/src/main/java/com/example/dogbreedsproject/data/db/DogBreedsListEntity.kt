package com.example.dogbreedsproject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dogbreedsproject.data.model.DogBreedsListDTO

@Entity(tableName = "dogBreeds")
data class DogBreedsListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: Map<String, List<String>>,
    val status: String
)

fun DogBreedsListDTO.toDogBreedsEntity(): DogBreedsListEntity{
    return DogBreedsListEntity(
        message = message,
        status = status
    )
}

