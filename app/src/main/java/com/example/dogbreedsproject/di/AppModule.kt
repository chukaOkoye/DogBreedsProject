package com.example.dogbreedsproject.di

import android.content.Context
import androidx.room.Room
import com.example.dogbreedsproject.data.api.ApiService
import com.example.dogbreedsproject.data.api.ApiService.Companion.BASE_URL
import com.example.dogbreedsproject.data.db.DogBreedImagesDao
import com.example.dogbreedsproject.data.db.DogBreedsDatabase
import com.example.dogbreedsproject.data.db.DogBreedsListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(): ApiService{

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDogBreedDatabase(@ApplicationContext context: Context): DogBreedsDatabase{
        return Room.databaseBuilder(
            context,
            DogBreedsDatabase::class.java,
            "dogBreedDatabase"
        ).build()
    }

    @Provides
    fun providesDogBreedsListDao(database: DogBreedsDatabase):
            DogBreedsListDao = database.dogBreedsListDao()

    @Provides
    fun providesDogBreedImagesDao(database: DogBreedsDatabase):
            DogBreedImagesDao = database.dogBreedImagesDao()

}