package com.example.dogbreedsproject.data.repository

import com.example.dogbreedsproject.data.api.ApiService
import com.example.dogbreedsproject.data.db.DogBreedImagesDao
import com.example.dogbreedsproject.data.db.DogBreedsListDao
import com.example.dogbreedsproject.data.db.toDogBreedImagesEntity
import com.example.dogbreedsproject.data.db.toDogBreedsEntity
import com.example.dogbreedsproject.data.model.DogBreedImageListDTO
import com.example.dogbreedsproject.data.model.DogBreedsListDTO
import com.example.dogbreedsproject.domain.DogBreedRepository
import com.example.dogbreedsproject.domain.model.DogBreedImageList
import com.example.dogbreedsproject.domain.model.DogBreedsList
import java.io.IOException
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(private val apiService: ApiService,
                                                 private val dogBreedsListDao: DogBreedsListDao,
                                                 private val dogBreedImagesDao: DogBreedImagesDao)
    : DogBreedRepository {

    override suspend fun getDogBreedList(): Result<DogBreedsList>{
        return try {
            val response = apiService.fetchAllBreedsList()
            val cache = response.toDogBreedsEntity()

            dogBreedsListDao.insertDogBreeds(cache)

            if(response.message.isEmpty()){
                return Result.failure(IOException("Data unavailable"))
            }
            Result.success(response.toBreedsListDomain())

        } catch (e: IOException){
            val cachedEntities = dogBreedsListDao.getAllDogBreeds()

            if(cachedEntities.isNotEmpty()){
                val entity = cachedEntities.first()
                val domainModel = DogBreedsList(
                    message = entity.message,
                    status = "success"
                )
                return Result.success(domainModel)
            }
            Result.failure(e)
        }
    }

    override suspend fun getDogBreedRandomImage(breedName: String): Result<DogBreedImageList> {
        return try {
            val response = apiService.fetchRandomBreedImages(breedName)
            val cache = response.toDogBreedImagesEntity()

            dogBreedImagesDao.insertDogBreedImages(cache)

            if(response.message.isEmpty()){
                return Result.failure(Exception("Data breed not available"))
            }

            Result.success(response.toImageListDomain())
        } catch(e: Exception){
            val dogBreedImageEntities = dogBreedImagesDao.getImagesFromBreed(breedName)

            if(dogBreedImageEntities.isNotEmpty()){
                val entity = dogBreedImageEntities.first()
                val imageDomainModel = DogBreedImageList(
                    message = entity.message
                )
                return Result.success(imageDomainModel)
            }

            Result.failure(e)
        }
    }
}

fun DogBreedImageListDTO.toImageListDomain(): DogBreedImageList{
    return DogBreedImageList(
        message = message
    )
}

fun DogBreedsListDTO.toBreedsListDomain(): DogBreedsList{
    return DogBreedsList(
        message = message,
        status = status
    )
}