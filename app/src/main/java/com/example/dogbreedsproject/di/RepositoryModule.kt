package com.example.dogbreedsproject.di

import com.example.dogbreedsproject.data.repository.DogBreedRepositoryImpl
import com.example.dogbreedsproject.domain.repository.DogBreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindsDogBreedRepository(repositoryImpl: DogBreedRepositoryImpl): DogBreedRepository

}