package com.example.dogbreedsproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DogBreedsListEntity::class, DogBreedImagesEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class DogBreedsDatabase: RoomDatabase() {
    abstract fun dogBreedsListDao(): DogBreedsListDao
    abstract fun dogBreedImagesDao(): DogBreedImagesDao

}
