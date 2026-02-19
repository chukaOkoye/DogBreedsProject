package com.example.dogbreedsproject.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogBreedsListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogBreeds(dogBreedsEntity: DogBreedsListEntity)

    @Query("SELECT * FROM dogBreeds")
    suspend fun getAllDogBreeds(): List<DogBreedsListEntity>
}
