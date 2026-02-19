package com.example.dogbreedsproject.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogBreedImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogBreedImages(dogBreedImagesEntity: DogBreedImagesEntity)

    @Query("SELECT * FROM dogBreedImages WHERE message = :breedName")
    suspend fun getImagesFromBreed(breedName: String): List<DogBreedImagesEntity>

    @Query("SELECT * FROM dogBreedImages")
    suspend fun getAllDogBreedImages(): List<DogBreedImagesEntity>
}
