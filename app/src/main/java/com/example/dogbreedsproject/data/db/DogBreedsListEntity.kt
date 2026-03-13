package com.example.dogbreedsproject.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.example.dogbreedsproject.data.model.DogBreedsListDTO
import com.example.dogbreedsproject.domain.model.DogBreedsList

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

fun DogBreedsListEntity.toDomain(): DogBreedsList{
    return DogBreedsList(
        message = message,
        status = status
    )
}


